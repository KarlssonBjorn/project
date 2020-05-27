package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.paypal.api.payments.BillingInfo;
import com.paypal.api.payments.CancelNotification;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.Invoice;
import com.paypal.api.payments.InvoiceItem;
import com.paypal.api.payments.ShippingInfo;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.PayPalRESTException;

import model.database.CustomerDatabase;
import model.database.OrderDatabase;
import model.database.ServiceDatabase;
import secretStuff.PaypalSecrets;

public class PaymentInvoice {
	private PaypalSecrets secret;
	private Invoice instance;
	private APIContext context;
	private ShippingInfo ship;
	private BillingInfo billInfo;
	private Currency cur;
	private InvoiceItem item;
	private OrderDatabase data;
	private CustomerDatabase customerData;
	private ServiceDatabase serviceData;
	private int customerID;
	private int serviceID;

	public PaymentInvoice() {
		secret = new PaypalSecrets();
		context = new APIContext(secret.getClientID(), secret.getClientSecret(), "sandbox");
		ship = new ShippingInfo();
		billInfo = new BillingInfo();
		cur = new Currency();
		item = new InvoiceItem();
		data = new OrderDatabase();
		customerData = new CustomerDatabase();
		serviceData = new ServiceDatabase();
	}

	// TODO remove get merchant from everyone
	public Invoice create(int orderId) {
		instance = loadInvoice();
		customerID = data.getOrderById(orderId).getCustomerId();
		serviceID = data.getOrderById(orderId).getServiceId();

		try {
			item.setName(serviceData.getServiceById(serviceID).getTitle());
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage() + "\nError Code: " + e.getErrorCode());
		}
		item.setQuantity(1);
		cur.setCurrency("USD");
		
		cur.setValue("" + data.getOrderById(orderId).getPrice());
		item.setUnitPrice(cur);

		ArrayList<BillingInfo> billing = new ArrayList<BillingInfo>();
		ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
		items.add(item);
		ship.setFirstName(customerData.getCustomerById(customerID).getName());
		ship.setLastName("");

		billInfo.setFirstName(customerData.getCustomerById(customerID).getName());
		billInfo.setLastName("");
		billInfo.setEmail("sb-433qpa1863507@personal.example.com");
		billing.add(billInfo);

		instance.setShippingInfo(ship);
		instance.setBillingInfo(billing);
		instance.setItems(items);

		try {
			instance = instance.create(context);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.setPayPalInvoiceID(orderId, instance.getId());

		System.out.println("create response:\n" + Invoice.getLastResponse());
		return instance;
	}

	public void send() {
		try {
			instance.send(context);
			System.out.println("send response:\n" + Invoice.getLastResponse());
		} catch (PayPalRESTException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void cancel(Invoice i) throws PayPalRESTException {
		CancelNotification cancelNotification = new CancelNotification();
		cancelNotification.setSubject("Order Uncomplete");
		cancelNotification.setNote("Order Uncomplete Canceling invoice");
		System.out.println("canceling Invoice Nr: " + i.getNumber());
		i.cancel(context, cancelNotification);
	}

	public Invoice retrieveInvoice(String invoiceID) {
		try {
			instance = Invoice.get(context, invoiceID);
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return instance;
	}


	private Invoice loadInvoice() {
		BufferedReader br = null;
		try {
			File file = new File("templet\\invoice_create.json").getAbsoluteFile();
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			return JSONFormatter.fromJSON(sb.toString(), Invoice.class);
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

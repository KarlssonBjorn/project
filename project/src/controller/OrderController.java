package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.paypal.api.payments.Invoice;
import com.paypal.base.rest.PayPalRESTException;

import model.Customer;
import model.Email;
import model.Order;
import model.PaymentInvoice;
import model.User;
import model.database.CustomerDatabase;
import model.database.OrderDatabase;
import model.database.ServiceDatabase;

/**
 *  Handles calls from View to Model which concerns any calls to the Order class.
 *
 */
public class OrderController {

    OrderDatabase orderDatabase;
    ServiceDatabase sd = new ServiceDatabase();
	CustomerDatabase CD = new CustomerDatabase();
    PaymentInvoice invoice;

    /**
     * Constructor
     */
    
	public OrderController() {
		orderDatabase = new OrderDatabase();
		sd = new ServiceDatabase();
		CD = new CustomerDatabase();
		invoice = new PaymentInvoice();
	}

    /**
     * Creates a new Order.
     * @param customerId
     * @param serviceId
     * @param date
     * @param shopId
     * @param company
     * @param price
     * @return String
     */
    
    public String newOrder(int customerId, int serviceId, String date, int shopId, String company, double price) {
    	
    	// TODO Why isn't this in InputValidator?
        if (customerId <= 0) return "ops, something went wrong!";
        else if (serviceId <= 0) return "ops, something went wrong!";
        else if (date.equalsIgnoreCase("")) return "ops, something went wrong!";
        else if (shopId <= 0) return "ops, something went wrong!";
        else if (date.equalsIgnoreCase("")) return "ops, something went wrong!";
        else if (company.equalsIgnoreCase("")) return "ops, something went wrong!";

        // TODO solve price is not set
        // TODO we need to get it from service, what's the best approach?

        User user = new User();

        boolean isSaved = orderDatabase.saveOrder(user.createOrder(customerId, serviceId, date, shopId, company, price));

        if (!isSaved) return "ops, something went wrong!";
        return "Order saved successfully!";
    }

    /**
     * Deletes a order.
     * @param id
     * @return String
     */
    
    public String deleteOrder(int id) {
        boolean isDeleted = orderDatabase.deleteOrder(id);

        if (isDeleted) return id + " Deleted!";
        return "ops, something went wrong!";
    }
    
    /**
     * Edits a Order
     * @param id
     * @param customerId
     * @param serviceId
     * @param price
     * @return String
     */

    public String editOrder(int id, int customerId, int serviceId, double price) {
        if (customerId <= 0) return "ops, something went wrong!";
        else if (serviceId <= 0) return "ops, something went wrong!";
        else if (id <= 0) return "ops, something went wrong!";

        User user = new User();

        boolean isDeleted = orderDatabase.editOrder(user.editOrder(id, customerId, serviceId, price));

        if (isDeleted) return "Order Edited!";
        return "ops, something went wrong!";
    }
    
    /**
     * Gets all Orders from a certain shop.
     * @param shopId The shop the retrieve all orders from.
     * @return ArrayList<Order>
     */

    public ArrayList<Order> getAllOrders(int shopId) {
        return orderDatabase.getAllOrders(shopId);
    }
    
    public ArrayList<Order> getAllOrdersCompany(String companyName) {
    	return orderDatabase.getAllOrdersCompany(companyName);
    }

    /**
     * Sets a order to Completed status.
     * @param id The Order to change status on.
     * @return String
     */

	public String setOrderToCompleted(int id) {
		if (id <= 0)
			return "ops, something went wrong!";

		boolean isSetToCompleted = orderDatabase.setOrderToCompleted(id);

		if (isSetToCompleted) {
			return "Order set to completed!";
		}
		return "ops, something went wrong!";
	}

    /**
     * Sets a order to Uncompleted status.
     * @param id The Order to change status on.
     * @return String
     */

	public String setOrderToUnCompleted(int id, String paidStatus) {
		if (id <= 0) return "ops, something went wrong!";
		else if (paidStatus.equalsIgnoreCase("PAID")) return "Order already paid, can not set to uncompleted";

		boolean isSetToCompleted = orderDatabase.setOrderToUnCompleted(id);

		if (isSetToCompleted) {
			return "Order set to completed!";
		}
		return "ops, something went wrong!";
	}

	public void sendInvoice(int id) {
		new Thread(() ->{
			int serviceId = orderDatabase.getOrderById(id).getServiceId();
			int customerId = orderDatabase.getOrderById(id).getCustomerId();
			try {
				orderDatabase.setPayPalInvoiceID(id, invoice.create(sd.getServiceById(serviceId).getTitle(), orderDatabase.getOrderById(id).getPrice(), CD.getCustomerById(customerId).getName()).getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			invoice.send();
			sendOrderCompleteMail(id);
		}).start();
	}

	public void cancelInvoice(int id) {
		new Thread(() ->{
			try {
				invoice.cancel(invoice.retrieveInvoice(orderDatabase.getOrderById(id).getPaypalID()));
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void sendOrderCompleteMail(int orderID) {

		Email email = new Email();
		Customer customer = CD.getCustomerById(orderDatabase.getOrderById(orderID).getCustomerId());
		email.createLink(orderDatabase.getOrderById(orderID).getPaypalID());
		email.sendMail(customer.getEmail(), orderID);
	}

	public boolean isEmailValid(int id) {

		Email email = new Email();
		Order order = orderDatabase.getOrderById(id);
		Customer customer = CD.getCustomerById(order.getCustomerId());
		if (email.validateEmail(customer.getEmail())) {
			return true;
		} else {
			return false;
		}

	}

}
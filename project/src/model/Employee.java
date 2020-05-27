package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Abstract class Employee that all system users extends.
 */

@SuppressWarnings("serial")
public abstract class Employee implements Serializable {

	private String phone;
	private String email;
	private String name;
	private String company;
	private int shopId;
	private int id;
	private String password;
	
	private String status;
	
	/**
	 * Constructor
	 * @param phone
	 * @param email
	 * @param name
	 * @param company
	 * @param shopId
	 * @param password
	 */
	
	public Employee(String phone, String email, String name, String company, int shopId, String password) {
		this.status = "";
		this.phone = phone;
		this.email = email;
		this.name = name;
		this.company = company;
		this.shopId = shopId;
		this.password = password;
	}
	
	/**
	 * Gets the logged in Employee.
	 * @return Employee the logged in Employee
	 */
	
	public static Employee getLoggedInUser() {
		try {
		File f = new File("LoggedInUser.txt");
		f.setWritable(true, true);
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Employee e = (Employee) ois.readObject();
		f.setWritable(false);
		f.setReadable(true);
		ois.close();
		return e;
		} catch (Exception err) {
			err.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Saves the Logged in Employee Object to a text file located in model.
	 * @param e The Employee the be logged in.
	 */
	
	public static void logInUser(Employee e) {
		try {
		File f = new File("LoggedInUser.txt");
		f.setWritable(true, true);
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(e);
		oos.close();
		f.setWritable(false);
		f.setReadable(true);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public Employee() { this.status = ""; } // TODO: Not sure where this is used. Remove comment if its useful.
	
	/**
	 * Creates a new Order.
	 * @param customerId
	 * @param serviceId
	 * @param date
	 * @param shopId
	 * @param company
	 * @param price
	 * @return Order
	 */
	
	public Order createOrder(int customerId, int serviceId, String date, int shopId, String company, double price) {
		Order order = new Order();
        order.setCustomerId(customerId);
        order.setServiceId(serviceId);
        order.setDate(date);
        order.setShopId(shopId);
        order.setcompanyName(company);
        order.setPrice(price);
        order.setCompleted(false);
        
        return order;
	}
	
	public int removeOrder(String id) {
		return -1; // TODO Not implemented
	}
	
	/**
	 * Edits a Order.
	 * @param id
	 * @param customerId
	 * @param serviceId
	 * @param price
	 * @return Order The newly edited Order.
	 */
	
	public Order editOrder(int id, int customerId, int serviceId, double price) {
		Order order = new Order();
        order.setId(id);
        order.setCustomerId(customerId);
        order.setServiceId(serviceId);
        order.setPrice(price);
        
        return order;
	}
	
	/**
	 * Creates a new Customer
	 * @param name
	 * @param email
	 * @param phone
	 * @param address
	 * @param company
	 * @return Customer The newly created customer
	 */
	
	public Customer createCustomer(String name, String email, String phone, String address, String company) {
		Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCompany(company);
        
        customer.setActive(true); // always true?
        
        return customer;
	}
	
	public int removeCustomer(String id) { return -1; } // TODO not implemented
	
	/**
	 * Edits a Customer
	 * @param name
	 * @param email
	 * @param phone
	 * @param address
	 * @param isActive
	 * @param id
	 * @return Customer The newly edited Customer.
	 */
	
	public Customer editCustomer(String name, String email, String phone, String address, boolean isActive, int id) { 
	Customer customer = new Customer();
    customer.setName(name);
    customer.setEmail(email);
    customer.setPhone(phone);
    customer.setAddress(address);
    customer.setId(id); 
    
    return customer;
	}
	
	// GETTERS AND SETTERS
	
	public int completeOrder(String orderId) { return -1; }
	
	public int uncompleteOrder(String orderId) { return -1; }
	
	public String getCompanyName() { return company; }
	
	public String getName() { return name; }
	
	public String getEmail() {return email; }
	
	public String getPhone() { return phone; }
	
	public int getShopId() { return shopId; }
	
	public String getStatus() { return status; }
	
	public int getId() { return id; }
	
	public String getPassword() { return password; }
	
	public void setName(String name) { this.name = name; }
	
	public void setEmail(String email) { this.email = email; }
	
	public void setPhone(String phone) { this.phone = phone; }
	
	public void setCompanyName(String company) { this.company = company;}
	
	public void setStatus(String status) { this.status = status;}
	
	public void setShopId(int shopId) { this.shopId = shopId; }
	
	public void setId(int id) { this.id = id; }
	
	public void setPassword(String password) { this.password = password; }
	
}

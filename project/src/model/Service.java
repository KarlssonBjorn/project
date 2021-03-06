package model;

/**
 * A representation of a Service in the system.
 */

public class Service {

	private String company;
	private String title;
	private String description;
	private int id;
	private double price;
	
	/**
	 * Empty Constructor.
	 */
	
	public Service() {
		
	}
	
	/**
	 * Constructor.
	 * @param company
	 * @param title
	 * @param description
	 * @param price
	 */
	
	public Service(String company, String title, String description, double price) {
		this.company = company;
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	// GETTERS AND SETTERS
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}

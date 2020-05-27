package model;

/**
 * A representation of a Super Admin in the system.
 */

@SuppressWarnings("serial")
public class SuperAdmin extends Employee {

	//TODO Is this needed?
	//private static final long serialVersionUID = 6369704467594286628L;

	/**
	 * Constructor.
	 * @param phone
	 * @param email
	 * @param name
	 * @param company
	 * @param status
	 */
	
	public SuperAdmin(String phone, String email, String name, String company, String status) {
		super();
		this.setPhone(phone);
		this.setEmail(email);
		this.setName(name);
		this.setCompanyName(company);
		//this.setPassword(password); TODO Is this needed? Because employee already sets password
		
		this.setStatus("super_admin");
	}
	
	/**
	 * Empty Constructor.
	 */
	
	public SuperAdmin() {
		this.setStatus("super_admin");
	}
	
	// TODO Not implemented
	
	public User createUser() {
		return null;
	}
	
	public int removeUser() {
		return -1;
	}
	
	public User editUser() {
		return null;
	}
	
	public Shop createShop(String name, String address, String companyName) {
		return new Shop(companyName, address, name);
	}
	
	public Shop editShop(int id, String name, String address) {
		Shop shop = new Shop();
		shop.setId(id);
		shop.setName(name);
		shop.setAddress(address);
		
		return shop;
	}
	/**
	 * Creates a Service.
	 * @param companyName
	 * @param title
	 * @param description
	 * @param price
	 * @return Service.
	 */
	
	public Service createService(String companyName, String title, String description, double price) {
		Service service = new Service();
        service.setCompany(companyName);
        service.setTitle(title);
        service.setDescription(description);
        service.setPrice(price);
        
        return service;
	}
	
	// TODO Not implemented.
	
	public int removeService() {
		return -1;
	}
	
	/**
	 * Edits a Service.
	 * @param companyName
	 * @param title
	 * @param description
	 * @param price
	 * @param id
	 * @return Service
	 */
	
	public Service editService(String companyName, String title, String description, double price, int id) {
		Service service = new Service();
        service.setCompany(companyName);
        service.setTitle(title);
        service.setDescription(description);
        service.setPrice(price);
        service.setId(id);
        
        return service;
	}
	
	/**
	 * Creates a new Admin.
	 * @param name
	 * @param email
	 * @param phone
	 * @param password
	 * @param companyName
	 * @param shopId
	 * @return Admin
	 */
	
	public Admin createAdmin(String name, String email, String phone, String password, String companyName, int shopId) {
		Admin admin = new Admin();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setPassword(password);
        admin.setCompanyName(companyName);
        admin.setShopId(shopId);
        
        return admin;
	}
	
	// TODO Not implemented.
	
	public int removeAdmin() {
		return -1;
	}
	
	/**
	 * Edits a Admin.
	 * @param phone
	 * @param email
	 * @param name
	 * @param shopId
	 * @param id
	 * @return Admin
	 */
	
	public Admin editAdmin(String phone, String email, String name, int shopId, int id) {
		Admin admin = new Admin();
        admin.setPhone(phone);
        admin.setEmail(email);
        admin.setName(name);
        admin.setShopId(shopId);
        admin.setId(id);
        
        return admin;
	}
	
}

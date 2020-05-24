package model;

public class SuperAdmin extends Employee {

	private static final long serialVersionUID = 6369704467594286628L;

	public SuperAdmin(String phone, String email, String name, String company, String password) {
		super();
		this.setPhone(phone);
		this.setEmail(email);
		this.setName(name);
		this.setCompanyName(company);
		this.setPassword(password);
		
		this.setStatus("super_admin");
	}
	
	public SuperAdmin() {
		this.setStatus("super_admin");
	}
	
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
	
	public Service createService(String companyName, String title, String description, double price) {
		Service service = new Service();
        service.setCompany(companyName);
        service.setTitle(title);
        service.setDescription(description);
        service.setPrice(price);
        
        return service;
	}
	
	public int removeService() {
		return -1;
	}
	
	public Service editService(String companyName, String title, String description, double price, int id) {
		Service service = new Service();
        service.setCompany(companyName);
        service.setTitle(title);
        service.setDescription(description);
        service.setPrice(price);
        service.setId(id);
        
        return service;
	}
	
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
	
	public int removeAdmin() {
		return -1;
	}
	
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

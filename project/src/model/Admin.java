package model;

/**
 * Representation of the "Admin"-role of the system.
 */

public class Admin extends Employee {

	/**
	 * Constructor.
	 * @param phone
	 * @param email
	 * @param name
	 * @param company
	 * @param shopId
	 * @param password
	 */
	
	public Admin(String phone, String email, String name, String company, int shopId, String password) {
		super(phone, email, name, company, shopId, password);
		
		this.setStatus("admin");
	}
	
	/**
	 * Empty Constructor
	 */
	
	public Admin() {
		this.setStatus("admin");
	}
	
	/**
	 * Creates a User.
	 * @param name
	 * @param email
	 * @param phone
	 * @param password
	 * @param companyName
	 * @param shopId
	 * @return User
	 */
	
	public User createUser(String name, String email, String phone, String password, String companyName, int shopId) {
		User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setCompanyName(companyName);
        user.setShopId(shopId);
        
        return user;
	}
	
	/**
	 * Removes a User.
	 * @return Integer
	 */
	
	public int removeUser() {
		return -1; //TODO not implemented.
	}
	
	/**
	 * Edits a User.
	 * @param phone
	 * @param email
	 * @param name
	 * @param shopId
	 * @param id
	 * @return User the new User
	 */
	
	public User editUser(String phone, String email, String name, int shopId, int id) {
		User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setName(name);
        user.setShopId(shopId);
        user.setId(id);
        
        return user;
	}
	
	// TODO: These are not implemented
	
	public Service createService() {
		return null;
	}
	
	public int removeService() {
		return -1;
	}
	
	public Service editService() {
		return null;
	}
	
}

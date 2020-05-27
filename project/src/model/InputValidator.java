package model;

/**
 * Validates input from system user.
 */

public class InputValidator {
	
	/**
	 * Validates that the information entered is enough to create a Service.
	 * @param companyName
	 * @param title
	 * @param description
	 * @param price
	 * @return String
	 */
	
	public String validateServiceInput(String companyName, String title, String description, double price) {
		
		if (companyName.equalsIgnoreCase("")) return "Ops, something went wrong!";
		else if (title.equalsIgnoreCase("")) return "Title is missing!";
    	else if (price == -1 ) return "Price is missing!"; // make sure to pass in -1 if price is missing
    	else if (description.equalsIgnoreCase("")) return "Description is missing!";
		
		return "";
	}
	
	/**
	 * Validates that the information entered is enough to create a Customer.
	 * @param name
	 * @param email
	 * @param phone
	 * @param address
	 * @param id
	 * @return String
	 */
	
	public String validateCustomerInput(String name, String email, String phone, String address, int id) {
		
		if (name.equalsIgnoreCase("")) return "Name is missing!";
        else if (email.equalsIgnoreCase("")) return "Email is missing!";
        else if(new Email().validateEmail(email)==false) return "Not a valid email!";
        else if (phone.equalsIgnoreCase("")) return "Phone is missing!";
        else if (address.equalsIgnoreCase("")) return "Address is missing!";
        else if (id == 0) return "Ops, something went wrong!";
		
		return "";
	}
	
	/**
	 * Validates that the information entered is enough to create a Employee.
	 * @param name
	 * @param email
	 * @param phone
	 * @param password
	 * @param companyName
	 * @param shopId
	 * @param id
	 * @return String
	 */
	
	public String validateEmployeeInput(String name, String email, String phone, String password, String companyName, int shopId, int id) {
		
		if (name.equalsIgnoreCase("")) return "Name is missing!";
        else if (email.equalsIgnoreCase("")) return "Email is missing!";
        else if(new Email().validateEmail(email)==false) return "Not a valid email!";
        else if (phone.equalsIgnoreCase("")) return "Phone is missing!";
        else if (shopId == 0 || companyName.equalsIgnoreCase("")) return "Ops, something went wrong!";
        else if (password.equalsIgnoreCase("")) return "Password is missing!";
        else if (id == 0) return "Ops, something went wrong!";
		
		return "";
	}
	
	public String validateShopInput(String name, String address, String companyName) {
		if (name.equalsIgnoreCase("")) return "Name is missing!";
		else if (address.equalsIgnoreCase("")) return "Address is missing!";
		else if (companyName.equalsIgnoreCase("")) return "Company is missing!";
		
		return "";
	}
}

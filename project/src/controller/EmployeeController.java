package controller;

import java.util.ArrayList;

import model.Admin;
import model.Employee;
import model.InputValidator;
import model.SuperAdmin;
import model.database.EmployeeDatabase;
import security.PasswordHasher;

/**
 * Handles calls from View to Model which concerns any class that extends the Employee Class.
 */

public class EmployeeController {

	private EmployeeDatabase employeeDatabase;
	
	private InputValidator inputValidator;
	
	/**
	 * Constructor
	 */
	
	public EmployeeController() {
		employeeDatabase = new EmployeeDatabase();
		inputValidator = new InputValidator();
	}
	
	/**
	 * Makes a call to the employeeDatabase.saveEmployee() function.
	 * @param list A list of information about the new Super_admin.
	 */
	
	public String newSuperAdmin(String name, String email, String phone, String password, String company) {
		Employee superAdmin = new SuperAdmin();
		superAdmin.setEmail(email);
		superAdmin.setCompanyName(company);
		superAdmin.setName(name);
		superAdmin.setPhone(phone);
		String hashedPassword = PasswordHasher.hashPassword(password); // TODO hashedPassword never used?
		superAdmin.setPassword(hashedPassword); 
		superAdmin.setStatus("Super_Admin");
		boolean isSaved = employeeDatabase.saveEmployee(superAdmin);
		if (!isSaved) return "ops, something went wrong!";
        return "Super admin created successfully";
	}
	
	/**
	 * Creates a new User.
	 * @param name The name of the new User to be created.
	 * @param email The email of the new User to be created.
	 * @param phone The phone number of the new User to be created.
	 * @param password The password of the new User to be created.
	 * @param companyName The company name which the new user to be created belongs to.
	 * @param shopId The ID of the shop where the new User to be created is placed at.
	 * @return String
	 */
	
	public String newUser(String name, String email, String phone, String password, String companyName, int shopId) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, 1); // id as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
        
        Admin admin = new Admin();

        boolean isSaved = employeeDatabase.saveEmployee(admin.createUser(name, email, phone, password, companyName, shopId));

        if (!isSaved) return "ops, something went wrong!";
        return "User created successfully";
    }
	
	public String deleteUser(int id, String name) {
		
        boolean isDeleted = employeeDatabase.deleteUser(id);

        if (isDeleted) return name + " Deleted!";
        return "ops, something went wrong!";
    }
	
	/**
	 * Edits a user.
	 * @param phone
	 * @param email
	 * @param name
	 * @param shopId
	 * @param id
	 * @return String
	 */
	
	public String editUser(String phone, String email, String name, int shopId, int id) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, "password", "company", shopId, id); // password and company as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
        
        Admin admin = new Admin();

        boolean isEdited = employeeDatabase.editEmployee(admin.editUser(phone, email, name, shopId, id));
        if (isEdited) return "User edited successfully";
        return "Ops, something went wrong!";
    }
	
	/**
	 * Creates a new Admin.
	 * @param name
	 * @param email
	 * @param phone
	 * @param password
	 * @param companyName
	 * @param shopId
	 * @return String
	 */
	
	public String newAdmin(String name, String email, String phone, String password, String companyName, int shopId) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, 1); // id as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;

        SuperAdmin superAdmin = new SuperAdmin();

        boolean isSaved = employeeDatabase.saveEmployee(superAdmin.createAdmin(name, email, phone, password, companyName, shopId));

        if (!isSaved) return "ops, something went wrong!";
        return "Admin created successfully";
    }
	
	public String deleteAdmin(int id, String name) {
		
        boolean isDeleted = employeeDatabase.deleteAdmin(id);

        if (isDeleted) return name + " Deleted!";
        return "ops, something went wrong!";
    }
	
	/**
	 * A function which will edit a Admin.
	 * @param phone
	 * @param email
	 * @param name
	 * @param shopId
	 * @param id
	 * @return String
	 */
	
	public String editAdmin(String phone, String email, String name, int shopId, int id) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, "password", "company", shopId, id); // password and company as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
        
        SuperAdmin superAdmin = new SuperAdmin();

        boolean isEdited = employeeDatabase.editEmployee(superAdmin.editAdmin(phone, email, name, shopId, id));
        if (isEdited) return "Admin edited successfully";
        return "Ops, something went wrong!";
    }
	
	/**
	 * Retrieves all the Employees from a certain company.
	 * @param companyName The name of the company
	 * @return ArrayList<Employee>
	 */
	
	public ArrayList<Employee> getAllEmployees(String companyName) {
        return employeeDatabase.getAllEmployees(companyName);
    }

	public Employee login(String email, String password, boolean isSuperAdmin) {
		System.out.println(isSuperAdmin);
		if (isSuperAdmin)
			return employeeDatabase.validateSuperAdmin(email, password);
		else 
			return employeeDatabase.validateEmployee(email, password);
	}
}

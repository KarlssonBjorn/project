package controller;

import java.util.ArrayList;

import model.Admin;
import model.Employee;
import model.InputValidator;
import model.SuperAdmin;
import model.database.EmployeeDatabase;
import security.PasswordHasher;

public class EmployeeController {

	private EmployeeDatabase employeeDatabase;
	
	private InputValidator inputValidator;
	
	public EmployeeController() {
		employeeDatabase = new EmployeeDatabase();
		
		inputValidator = new InputValidator();
	}
	
	public String newSuperAdmin(String name, String email, String phone, String password, String companyName) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, 1, 1); // id as and shopId as stub
		if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
		
		SuperAdmin superAdmin = new SuperAdmin(phone, email, name, companyName, PasswordHasher.hashPassword(password));
		
		boolean isSaved = employeeDatabase.saveEmployee(superAdmin);
		
		if (!isSaved) return "ops, something went wrong!";
        return "SuperAdmin created successfully";
	}
	
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
	
	public String editUser(String phone, String email, String name, int shopId, int id) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, "password", "company", shopId, id); // password and company as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
        
        Admin admin = new Admin();

        boolean isEdited = employeeDatabase.editEmployee(admin.editUser(phone, email, name, shopId, id));
        if (isEdited) return "User edited successfully";
        return "Ops, something went wrong!";
    }
	
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
	
	public String editAdmin(String phone, String email, String name, int shopId, int id) {
		
		String inputCheck = inputValidator.validateEmployeeInput(name, email, phone, "password", "company", shopId, id); // password and company as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
        
        SuperAdmin superAdmin = new SuperAdmin();

        boolean isEdited = employeeDatabase.editEmployee(superAdmin.editAdmin(phone, email, name, shopId, id));
        if (isEdited) return "Admin edited successfully";
        return "Ops, something went wrong!";
    }
	
	public ArrayList<Employee> getAllEmployees(String companyName) {
        return employeeDatabase.getAllEmployees(companyName);
    }
	
	//TODO These needs implementation
	public boolean validateEmployee(Employee e, String p) {
			return false;
	}

	public Employee login(String email, String password) {
		return employeeDatabase.validateEmployee(email, password);
	}	
}

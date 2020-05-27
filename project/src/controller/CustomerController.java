package controller;

import java.util.ArrayList;

import model.Customer;
import model.InputValidator;
import model.User;
import model.database.CustomerDatabase;

/**
 * Controller class that handles calls from the View package to the Model package that concerns the Customer class
 * 
 */

public class CustomerController {

    private CustomerDatabase customerDatabase;
    private InputValidator inputValidator;

    public CustomerController() {
        customerDatabase = new CustomerDatabase();
        inputValidator = new InputValidator();
    }

    /**
     * Makes a call to the customerDatabase.saveCustomer function.
     * @param name The customers name.
     * @param email The customers email.
     * @param phone The customers phone number.
     * @param address The customers address.
     * @param company 
     * @return String
     */
    
    public String createCustomer(String name, String email, String phone, String address, String company) {
    	String inputCheck = inputValidator.validateCustomerInput(name, email, phone, address, 1); // id as stub

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;

        User user = new User();

        boolean customerIsSaved = customerDatabase.saveCustomer(user.createCustomer(name, email, phone, address, company));

        if (!customerIsSaved) return "ops, something went wrong!";

        return "Customer successfully added!";
    }
    
    /**
     * Makes a call to the customerDatabse.deleteCustomer() function.
     * @param id The ID of the customer to delete.
     * @param name The name of the Customer
     * @return String
     */

    public String deleteCustomer(int id, String name) {
        boolean isDeleted = customerDatabase.deleteCustomer(id);

        if (isDeleted) return name + " Deleted!"; // TODO: Maybe remove the name argument? It only adds cosmetics.
        return "ops, something went wrong!";
    }

    /**
     * Makes a call to the customerDatabase.editCustomer() function.
     * @param name The new name of the customer to be edited.
     * @param email The new email of the customer to be edited.
     * @param phone The new phone number of the customer to be edited.
     * @param address The new address of the customer to be edited.
     * @param isActive The new isActive status.
     * @param id The ID of the old Customer.
     * @return String
     */
    // TODO No company argument?
    
    public String editCustomer(String name, String email, String phone, String address, boolean isActive, int id) {
    	String inputCheck = inputValidator.validateCustomerInput(name, email, phone, address, id);

    	if (!inputCheck.equalsIgnoreCase("")) return inputCheck;
    	
        User user = new User();

        boolean isDeleted = customerDatabase.editCustomer(user.editCustomer(name, email, phone, address, isActive, id));

        if (isDeleted) return name + " Edited successfully!";
        return "ops, something went wrong!";

    }

    /**
     * Makes a call to the customerDatabse.getAllCustomers() function.
     * @param company The company which customers will be returned
     * @return ArrayList<Customer>
     */
    
    public ArrayList<Customer> getAllCustomers(String company) {

        ArrayList<Customer> customers = customerDatabase.getAllCustomers(company);

        return customers;
    }
    
    public String getCustomerNameById(int id) {
    	return customerDatabase.getCustomerById(id).getName();
    }
}

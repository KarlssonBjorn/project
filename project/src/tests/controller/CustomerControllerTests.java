package tests.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.CustomerController;
import model.Customer;

class CustomerControllerTests {

	CustomerController cc = new CustomerController();
	
	@Test
	void createCustomerSuccessful() {
		assertEquals(cc.createCustomer("Jesper", "je223@gmail.com", "070623123", "Rastensv2", "Test_company"), "Customer successfully added!");
	}
	
	@Test
	void createCustomerFailDueToEmptyName() {
		assertEquals(cc.createCustomer("", "je223@gmail.com", "070623123", "Rastensv2", "Test_company"), "Name is missing!");
	}
	
	@Test
	void createCustomerFailDueToMulitpleErrors() {
		assertEquals(cc.createCustomer("", "", "", "Rastensv2", "Test_company"), "Name is missing!"); // Maybe return a array with all the errors?
	}
	
	@Test
	void editCustomerSuccessful() {
		assertEquals(cc.editCustomer("Jesper", "je223@gmail.com", "070623123", "Rastensv2", true, 1), "Customer successfully added!");
	}
	
	@Test
	void editCustomerFailDueToEmptyName() {
		assertEquals(cc.editCustomer("", "je223@gmail.com", "070623123", "Rastensv2", true, 1), "Name is missing!");
	}
	
	@Test
	void editCustomerFailDueToMulitpleErrors() {
		assertEquals(cc.editCustomer("", "", "", "Rastensv2", true, 1), "Name is missing!"); // Maybe return a array with all the errors?
	}
	
	
	// TODO
	// Fix so that I can check wheter a function in a class is being called.
	@Test
	void deleteCustomerTest() {
		cc.createCustomer("Jesper", "je223@gmail.com", "070623123", "Rastensv2", "Test_company");
		cc.deleteCustomer(1, "Jesper Deleted!");
	}
	
	@Test
	void getAllCustomersTests() {
		// Check that getAllCustomers is being called.
	}

}

package tests.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.ServiceController;
import model.InputValidator;

class inputValidator {
	
	private InputValidator inputValidator;
	
	private String companyName, title, description;
	private double price;
	
	private String name, email, phone, address;
	private int id;
	
	private int shopId;
	private String password;
	
	public void init() {
		inputValidator = new InputValidator();
		
		// for service
		companyName = "company";
		title = "title";
		description = "description";
		price = 55;
		
		// for customer
		name = "Gabriel";
		email = "mail@email.com";
		phone = "8754638455";
		address = "street 5";
		id = 5;
		
		shopId = 7;
		password = "password";
	}
	
	// FOR SERVICES
	
	@Test
	void testingValidateServiceValidInput() {
		
		this.init();
		
		String result = inputValidator.validateServiceInput(companyName, title, description, price);
		
		assertEquals("", result);
	}
	
	@Test
	void testingValidateServiceNoCompanyName() {
		
		this.init();
		
		companyName = "";
		
		String result = inputValidator.validateServiceInput(companyName, title, description, price);
		
		assertEquals("Ops, something went wrong!", result);
	}
	
	@Test
	void testingValidateServiceNoTitle() {
		
		this.init();
		
		title = "";
		
		String result = inputValidator.validateServiceInput(companyName, title, description, price);
		
		assertEquals("Title is missing!", result);
	}
	
	@Test
	void testingValidateServiceNoDescription() {
		this.init();
		
		description = "";
		
		String result = inputValidator.validateServiceInput(companyName, title, description, price);
		
		assertEquals("Description is missing!", result);
	}
	
	@Test
	void testingValidateServiceNoPrice() {
		
		this.init();
		
		price = -1;
		
		String result = inputValidator.validateServiceInput(companyName, title, description, price);
		
		assertEquals("Price is missing!", result);
	}
	
	// FOR CUSTOMERS
	
	@Test
	void testingValidateCustomerValidInput() {
		
		this.init();
		
		String result = inputValidator.validateCustomerInput(name, email, phone, address, id);
		
		assertEquals("", result);
	}
	
	@Test
	void testingValidateCustomerNoName() {
		
		this.init();
		
		name = "";
		
		String result = inputValidator.validateCustomerInput(name, email, phone, address, id);
		
		assertEquals("Name is missing!", result);
	}
	
	@Test
	void testingValidateCustomerNoEmail() {
		
		this.init();
		
		email = "";
		
		String result = inputValidator.validateCustomerInput(name, email, phone, address, id);
		
		assertEquals("Email is missing!", result);
	}
	
	@Test
	void testingValidateCustomerNoPhone() {
		
		this.init();
		
		phone = "";
		
		String result = inputValidator.validateCustomerInput(name, email, phone, address, id);
		
		assertEquals("Phone is missing!", result);
	}
	
	@Test
	void testingValidateCustomerNoAddress() {
		
		this.init();
		
		address = "";
		
		String result = inputValidator.validateCustomerInput(name, email, phone, address, id);
		
		assertEquals("Address is missing!", result);
	}
	
	@Test
	void testingValidateCustomerNoId() {
		
		this.init();
		
		id = 0;
		
		String result = inputValidator.validateCustomerInput(name, email, phone, address, id);
		
		assertEquals("Ops, something went wrong!", result);
	}
	
	@Test
	void testingValidateEmployeeValidInput() {
		
		this.init();
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("", result);
	}
	
	@Test
	void testingValidateEmployeeNoName() {
		
		this.init();
		
		name = "";
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Name is missing!", result);
	}
	
	@Test
	void testingValidateEmployeeNoEmail() {
		
		this.init();
		
		email = "";
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Email is missing!", result);
	}
	
	@Test
	void testingValidateEmployeeNoPhone() {
		
		this.init();
		
		phone = "";
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Phone is missing!", result);
	}
	
	@Test
	void testingValidateEmployeeNoPassword() {
		
		this.init();
		
		password = "";
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Password is missing!", result);
	}
	
	@Test
	void testingValidateEmployeeNoCompanyName() {
		
		this.init();
		
		companyName = "";
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Ops, something went wrong!", result);
	}
	
	@Test
	void testingValidateEmployeeCompanyNoShopId() {
		
		this.init();
		
		shopId = 0;
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Ops, something went wrong!", result);
	}
	
	@Test
	void testingValidateEmployeeCompanyNoId() {
		
		this.init();
		
		id = 0;
		
		String result = inputValidator.validateEmployeeInput(name, email, phone, password, companyName, shopId, id);
		
		assertEquals("Ops, something went wrong!", result);
	}

}

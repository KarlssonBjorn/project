package tests.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Admin;
import model.Employee;
import model.SuperAdmin;
import model.User;
import model.database.EmployeeDatabase;

class EmployeeDatabaseUnitTests {
	
	EmployeeDatabase db;
	
	Employee user1;
	Employee user2;
	
	Employee admin1;
	Employee admin2;
	
	Employee superAdmin1;
	Employee superAdmin2;

	public void init() throws SQLException {
		db = new EmployeeDatabase();
		
		// Initating the Employees
		
		user1 = new User("123", "email1@email.com", "Jesper", "Saab", 0, "User");
		user2 = new User("345", "email2@email.com", "Tom", "Volvo", 1, "User");
		
		admin1 = new Admin("678", "email3@email.com", "Jerry", "IKEA", 2, "Admin");
		admin2 = new Admin("9012", "email4@email.com", "Tim", "IKEA", 2, "Admin");
		
		superAdmin1 = new SuperAdmin("3456", "email5@email.com", "John", "IKEA", "SuperAdmin");
		superAdmin2 = new SuperAdmin("7890", "email6@email.com", "Jake", "IKEA", "SuperAdmin");
		
		// Reseting database for each test
		
		db.resetAdmin();
		db.resetSuperAdmin();
		db.resetUser();
		
	}
	
	/**
	 * The .getNames() should be generated with Mockito since we will have other tests testing the Model package.
	 */
	
	@Test
	void testingReturnAllEmployees() throws SQLException {
		this.init();
		
		db.saveEmployee(admin1);
		
		ArrayList<Employee> testArr = db.getAllEmployees("IKEA");
		
		assertEquals("Jerry", testArr.get(0).getName());
	}
	
	@Test
	void testingGetUserById() throws SQLException {
		this.init();
		
		db.saveEmployee(user1);
		assertEquals("Jesper", db.getUserById(1).getName());
	}
	
	@Test
	void testingGetAdminById() throws SQLException {
		this.init();
		
		db.saveEmployee(admin1);
		assertEquals("Jerry", db.getAdminById(1).getName());
	}
	
	@Test
	void testingGetSuperAdminById() throws SQLException {
		this.init();
		
		db.saveEmployee(superAdmin1);
		assertEquals("John", db.getAdminById(1).getName());
	}
	
	@Test
	void testingEditEmployee() throws SQLException {
		this.init();
		
		db.saveEmployee(user1);
		
		db.editEmployee(user2);
		
		assertEquals(user2.getName(), db.getUserById(1).getName());
	}
	
	@Test
	void testingValidateEmployee() throws SQLException {
		this.init();
		
		db.saveEmployee(user1);
		
		// This is hard to test due to password implmentation havent been done yet.
		
		assertTrue(db.validateEmployee(user1.getEmail(), "correct_password"));
	}
	
	
	
	

}

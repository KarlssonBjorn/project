package tests.database;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import model.Customer;
import model.database.CustomerDatabase;

class CustomerDatabaseUnitTests {

	CustomerDatabase db = new CustomerDatabase();;
	Customer c;
	Customer b;
	
	public void init() throws SQLException {
		
		db.reset();
		c = new Customer();
		c.setActive(true);
		c.setAddress("Street 1");
		c.setEmail("tom@email.com");
		c.setName("TOM");
		c.setPhone("123");
		
		
		b = new Customer();
		b.setActive(true);
		b.setAddress("Street 2");
		b.setEmail("john@email.com");
		b.setName("JOHN");
		b.setPhone("456");
	}
	
	@Test
	void saveCustomerSuccess() throws InvalidKeySpecException, SQLException {
			this.init();
		
			db.saveCustomer(c);
			assertEquals("TOM", db.getCustomerById("1").getName()); // Checks that the retrieved name is equal to the saved one.
			db.reset();
		
	}
	
	@Test
	void saveCustomerFailureDueToDuplicateUser() throws InvalidKeySpecException, SQLException {
		this.init();
		
		assertThrows(MySQLIntegrityConstraintViolationException.class, () -> {
			db.saveCustomer(c);
			db.saveCustomer(c);
		});
		
		db.reset();
	}
	
	@Test
	void testDeleteCustomer() throws InvalidKeySpecException, SQLException {
		this.init();
		
		db.saveCustomer(c);
		assertEquals(1, db.deleteCustomer(1));
		db.reset();
	}
	
	
	@Test
	void testEditCustomerSuccess() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		this.init();
		
		db.saveCustomer(c);
		//db.editCustomer("1", b); TODO NEEDS FIXING
		
		assertEquals("JOHN", db.getCustomerById("1").getName());
		
		db.reset();
	}
	
	/**
	 * TODO: Its hard to test edit because it doesn't throw any Exception when entering a invalid ID.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws SQLException
	 */
	@Test
	void testEditCustomerFailureDueToInvalidId() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		this.init();

		db.saveCustomer(c);

		assertThrows(InvalidKeySpecException.class, () -> {
			//db.editCustomer("1", c); Invalid ID 2. TODO NEEDS FIXING
		});
		
		
		db.reset();
	
	}
	
	@Test
	void testGetAllCustomers() throws InvalidKeySpecException, SQLException {
		this.init();
		
		db.saveCustomer(c);
		db.saveCustomer(b);
	
		/*ArrayList<Customer> testArr = db.getAllCustomers(); TODO NEEDS FIXING
		
		assertEquals(c.getName(), testArr.get(0).getName());
		assertEquals(b.getName(), testArr.get(1).getName());
		*/
		db.reset();
	}

}

package model.database.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Customer;
import model.Service;
import model.database.ServiceDatabase;

class ServiceDatabaseUnitTests {

	Service a;
	Service b;
	ServiceDatabase db;
	
	@Test
	void mainTest() throws SQLException {
		db = new ServiceDatabase();
		a = new Service();
		
		a.setCompany("D�ckbyte AB");
		a.setDescription("Vi byter dina d�ck.");
		a.setPrice(100);
		a.setTitle("D�ckbyte");
		
	    b = new Service();
		
		b.setCompany("D�ckbyte ABC");
		b.setDescription("Vi byter dina d�ck.");
		b.setPrice(100);
		b.setTitle("D�ckbyte");
		
		db.saveService(a); // SAVE WORKS
		
		// db.deleteService("1"); // DELETE WORKS
		
		// db.editService("1", b); // EDIT WORKS
		
		ArrayList<Service> services = db.getAllServices();
		assertEquals("D�ckbyte AB", services.get(0).getCompany());
		
		
	}

}

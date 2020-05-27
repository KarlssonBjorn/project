package tests.database;

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
	ServiceDatabase db = new ServiceDatabase();
	
	
	public void init() throws SQLException {
		
		db.reset();
		
		a = new Service();
		
		a.setCompany("Company A");
		a.setDescription("Service A");
		a.setPrice(100);
		a.setTitle("Service A");
		
	    b = new Service();
		
		b.setCompany("Company B");
		b.setDescription("Service B");
		b.setPrice(100);
		b.setTitle("Service B");
	}
	
	@Test
	void testGetAllServices() throws SQLException {
		this.init();
		
		ArrayList<Service> testArr = new ArrayList<Service>();
		
		db.saveService(a);
		db.saveService(b);
		
		assertEquals(a.getCompany(), testArr.get(0).getCompany());
		assertEquals(b.getCompany(), testArr.get(1).getCompany());
		
		db.reset();
	}
	
	@Test
	void testSaveServiceSuccess() throws SQLException {
		this.init();

		db.saveService(a);
		
		assertEquals(a.getCompany(), db.getServiceById(1).getCompany());
		
		db.reset();
	}
	
	@Test
	void testDeleteService() throws SQLException {
		this.init();

		db.saveService(a);
		
		assertEquals(1, db.deleteService(1));
		
		db.reset();
	}
	
	@Test
	void testEditService() throws SQLException {
		this.init();
		
		db.saveService(a);
		db.editService(b);
		
		assertEquals(b.getCompany(), db.getServiceById(1).getCompany());
	}


}

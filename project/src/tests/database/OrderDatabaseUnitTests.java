package tests.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Order;
import model.Service;
import model.database.OrderDatabase;

class OrderDatabaseUnitTests {
	
	Order a;
	Order b;
	OrderDatabase db = new OrderDatabase();
	
	public void init() throws SQLException {
		a = new Order(1, 1, "testdatum", 1, "company_a", 12, false);
		b = new Order(2, 2, "testdatum", 1, "company_b", 15, false);
		
		db.reset();
	}

	@Test
	void testGetAllOrders() throws SQLException {
		this.init();
		
		db.saveOrder(a);
		db.saveOrder(b);
		
		ArrayList<Order> testArr = db.getAllOrders(1);
		
		for(int i = 0; i < testArr.size(); i++) {
			assertEquals("testdatum", testArr.get(i).getDate());
		}
		
	}
	
	@Test
	void testGetOrderById() throws SQLException {
		this.init();
		
		db.saveOrder(a);
		db.saveOrder(b);
		
		assertEquals(a.getPrice(), db.getOrderById(1).getPrice());
		
	}
	
	@Test
	void testEditOrder() throws SQLException {
		this.init();
		
		db.saveOrder(a);
		db.editOrder(b);
		
		assertEquals(b.getPrice(), db.getOrderById(1).getPrice());
	}
	

}

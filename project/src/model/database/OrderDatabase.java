package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Order;

/**
 * A class that handles the Order Database.
 */

public class OrderDatabase implements DatabaseConnector {
	
	private Connection connection = DatabaseConnector.getConnection();

	/**
	 * Gets all orders from a certain shop.
	 * @param shopId
	 * @return ArrayList<Order>
	 */
	
	public ArrayList<Order> getAllOrders(int shopId) {
		ArrayList<Order> orders = new ArrayList<>();
		
		try {
			String statement = "SELECT id FROM orders WHERE shop_id = " + shopId + ";";
			Statement query = connection.prepareStatement(statement);
			ResultSet result = query.executeQuery(statement);
			
			while(result.next()) {
				orders.add(getOrderById(result.getInt("id")));
			}
			
			return orders;
			
		} catch (SQLException e1) { 
			e1.printStackTrace(); 
			return null;
		}
	}
	
	public ArrayList<Order> getAllOrdersCompany(String companyName) {
		ArrayList<Order> orders = new ArrayList<>();
		
		try {
			String statement = "SELECT id FROM orders WHERE company_name = '" + companyName + "';";
			Statement query = connection.prepareStatement(statement);
			ResultSet result = query.executeQuery(statement);
			
			while(result.next()) {
				orders.add(getOrderById(result.getInt("id")));
			}
			
			return orders;
			
		} catch (SQLException e1) { 
			e1.printStackTrace(); 
			return null;
		}
	}
	/**
	 * Gets a Order by ID.
	 * @param id
	 * @return Order
	 */
	
	public Order getOrderById(int id) {
		Order order = new Order();
		try {
			String statement = "SELECT * FROM orders WHERE id = " + id + ";";
			Statement query = connection.prepareStatement(statement);
			ResultSet result = query.executeQuery(statement);
			
			while(result.next()) {
				order.setServiceId(result.getInt("service_id"));
				order.setCustomerId(result.getInt("customer_id"));
				order.setShopId(result.getInt("shop_id"));
				order.setcompanyName(result.getString("company_name"));
				order.setPaypalID(result.getString("paypal_id"));
				order.setPrice(result.getInt("price"));
				order.setCompleted(result.getBoolean("completed"));
				order.setDate(result.getString("date"));
				order.setId(result.getInt("id"));
				order.setPaidStatus(result.getString("paid_status"));
			}
			return order;
			
		} catch (SQLException e1) { e1.printStackTrace(); }
		return null;
	}
	
	/**
	 * Saves a Order
	 * @param o
	 * @return boolean.
	 */
	
	public boolean saveOrder(Order o) {
		PreparedStatement create;
		try {
			create = connection.prepareStatement("INSERT INTO orders (service_id, customer_id, shop_id, company_name, paypal_id, price, completed, date)"
						+ "VALUES ("+ o.getServiceId() +", "
						+ o.getCustomerId() +","
						+ o.getShopId() +","
						+ "'"+ o.getcompanyName() +"', "
						+ "'"+ o.getPaypalID() +"', "
						+ o.getPrice() +","
						+ o.getCompleted() +","
						+ "'"+ o.getDate() +"');");
			create.executeUpdate();
			
			return true;
			
		} catch (SQLException e1) {
			e1.printStackTrace(); 
			return false;
			}
	}
	
	/**
	 * Deletes a Order.
	 * @param id
	 * @return boolean
	 */
	
	public boolean deleteOrder(int id) {
		PreparedStatement create;
		try {
			create = connection.prepareStatement("DELETE FROM orders WHERE id = " + id + ";");
			create.executeUpdate();
			
			return true;
			
		} catch (SQLException e1) { 
			e1.printStackTrace();
			return false;
			}
	}
	
	/**
	 * Edits a Order.
	 * @param o
	 * @return boolean
	 */
	
	public boolean editOrder(Order o) {
		PreparedStatement edit;
		try {
			edit = connection.prepareStatement("UPDATE orders "
						+ "SET service_id = "+ o.getServiceId() +", "
						+ "customer_id = "+ o.getCustomerId() +","
						+ "price = "+ o.getPrice() +","
						+ "completed = "+ o.getCompleted() +" "
						+ "WHERE id = "+ o.getId() +";");
			edit.executeUpdate();
			
			return true;
			
		} catch (SQLException e1) { 
			e1.printStackTrace();
			return false;
			}
	}

	/**
	 * Sets a order to complete
	 * @param id
	 * @return boolean
	 */
	
	public boolean setOrderToCompleted(int id) {
		PreparedStatement edit;
		try {
			edit = connection.prepareStatement("UPDATE orders "
						+ "SET completed = true "
						+ "WHERE id = "+ id +";");
			edit.executeUpdate();
			
			return true;
			
		} catch (SQLException e1) { 
			e1.printStackTrace();
			return false;
		}
	}
	
	 /**
	 * Sets a Order to uncompleted.
	 * @param id
	 * @return boolean
	 */
	
	public boolean setOrderToUnCompleted(int id) {
		PreparedStatement edit;
		try {
			edit = connection.prepareStatement("UPDATE orders "
						+ "SET completed = false "
						+ "WHERE id = "+ id +";");
			edit.executeUpdate();
			
			return true;
			
		} catch (SQLException e1) { 
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean setPayPalInvoiceID(int id, String invoiceID) {
		PreparedStatement edit;
		try {
			edit = connection.prepareStatement("UPDATE orders "
						+ "SET paypal_id = '"+ invoiceID +"' "
						+ "WHERE id = "+ id +";");
			edit.executeUpdate();
			
			return true;
			
		} catch (SQLException e1) { 
			e1.printStackTrace();
			return false;
			}
	}
	
	// TODO TEST METHOD
	public void reset() throws SQLException {
		String statement = "TRUNCATE TABLE orders";
		PreparedStatement query = connection.prepareStatement(statement);
		query.executeUpdate();

	}

}

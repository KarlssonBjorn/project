package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controller.CustomerController;
import controller.OrderController;
import controller.ServiceController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.Customer;
import model.Employee;
import model.Order;
import model.Service;

public class OrderView{
	
	private ArrayList<Cell> uncompList;
	private ArrayList<Cell> compList;
	private ListView<Cell> uncompLv;
	private ListView<Cell> compLv;
	
	private OrderController orderController;
	private ServiceController serviceController;
	private CustomerController customerController;
	
	private Employee loggedInUser;
	
	public OrderView() {
		orderController = new OrderController();
		serviceController = new ServiceController();
		customerController = new CustomerController();
		
		uncompList = new ArrayList<Cell>();
		compList = new ArrayList<Cell>();
	}
	
	public BorderPane getCenter(Stage window) {
		ObservableList<Cell> uncompObsList;
		ObservableList<Cell> compObsList;
		
		loggedInUser = Employee.getLoggedInUser();
		
		setList();
			
		BorderPane bp = new BorderPane();
		
		Button createButton = new Button();
		createButton.setGraphic(new ImageView(new Image("view/images/create.png")));
		createButton.setTooltip(new Tooltip("Create new order"));
		
		// do not allow super admin to create orders since it does not have a shop.
		createButton.setOnAction(e ->  {
			if (!loggedInUser.getStatus().equalsIgnoreCase("super_admin"))
				create();
			else
				Popup.displayErrorMessage("Super admins can not create orders");
		});
		
		uncompLv = new ListView<Cell>();
		compLv = new ListView<Cell>();
		uncompObsList = FXCollections.observableList(uncompList);
		compObsList = FXCollections.observableList(compList);
		uncompLv.setItems(uncompObsList);
		compLv.setItems(compObsList);
		
		uncompLv.prefWidthProperty().bind(window.widthProperty().multiply(.495));
		compLv.prefWidthProperty().bind(window.widthProperty().multiply(.495));
		
		bp.setTop(createButton);
		bp.setLeft(uncompLv);
		bp.setRight(compLv);
		
		return bp;
	}
	
	private void setList() {
		
		ArrayList<Order> allOrders = orderController.getAllOrders(loggedInUser.getShopId());
		
		uncompList.clear();
		compList.clear();
		
		for(Order o : allOrders) {
			if(!o.getCompleted())
				uncompList.add(new Cell(o.getCustomerId(), o.getPrice(), o.getCompleted(), o.getServiceId(), o.getDate(), o.getShopId(), o.getcompanyName(), o.getId(), o.getPaidStatus()));
			else
				compList.add(new Cell(o.getCustomerId(), o.getPrice(), o.getCompleted(), o.getServiceId(), o.getDate(), o.getShopId(), o.getcompanyName(), o.getId(), o.getPaidStatus()));
		}
	}
	
	private void create() {
		GridPane pane = new GridPane();
		pane.setVgap(5);
		Button button = new Button("Create");
		DoubleTextField priceField = new DoubleTextField();
		ComboBox<String> serviceBox = new ComboBox<String>();
		ComboBox<String> customerBox = new ComboBox<String>();
		
		ArrayList<Service> allServices = serviceController.getAllServices(loggedInUser.getCompanyName());
		int i = 1;
		for (Service s : allServices) {
			serviceBox.getItems().add(i + ". " + s.getTitle() + "  |  $" + s.getPrice());
			if (i == 1) serviceBox.setValue(i + ". " + s.getTitle() + "  |  $" + s.getPrice());
		i++;
		}
		
		ArrayList<Customer> allCustomers = customerController.getAllCustomers(loggedInUser.getCompanyName());
		
		i = 1;
		for (Customer c : allCustomers) {
			customerBox.getItems().add(i + ". " + c.getName() + "  |  " + c.getEmail());
			if (i == 1) customerBox.setValue(i + ". " + c.getName() + "  |  " + c.getEmail());
			i++;
		}
		
		pane.add(new Label("Customer:"), 0, 0);
		pane.add(customerBox, 0, 1);
		pane.add(new Label("Service:"), 0, 2);
		pane.add(serviceBox, 0, 3);
		pane.add(new Label("Price:"), 0, 4);
		pane.add(priceField, 0, 5);
		pane.add(button, 0, 6);
		
		Scene scene = new Scene(pane, 300, 600);
		Stage window = new Stage();
		
		button.setOnAction(e -> {
			
			int serviceVal = serviceBox.getValue().indexOf(".");
			serviceVal = Integer.parseInt(serviceBox.getValue().substring(0, serviceVal)) - 1;
			
			int customerVal = customerBox.getValue().indexOf(".");
			customerVal = Integer.parseInt(customerBox.getValue().substring(0, customerVal)) - 1;
			
			int serviceId = allServices.get(serviceVal).getId();
			int customerId = allCustomers.get(customerVal).getId();
			String date = this.getTodaysDate();
			int shopId = loggedInUser.getShopId();
			String companyName = loggedInUser.getCompanyName();
			double price = Double.parseDouble(priceField.getText());
			
			uncompLv.refresh();
			compLv.refresh();
			window.close();
			
			String message = orderController.newOrder(customerId, serviceId, date, shopId, companyName, price);
			
			setList();
			
			if (message.contains("successfully"))
				Popup.displaySuccessMessage(message);
			else
				Popup.displayErrorMessage(message);
		});
		
		window.setTitle("Create new order");
		window.setScene(scene);
		window.show();
	}
	
	private void edit(Cell cell) {
		
		GridPane pane = new GridPane();
		Button editBtn = new Button("Edit");
		DoubleTextField priceField = new DoubleTextField("" + cell.getPrice());
		ComboBox<String> serviceBox = new ComboBox<String>();
		ComboBox<String> customerBox = new ComboBox<String>();
		
		ArrayList<Service> allServices = serviceController.getAllServices(loggedInUser.getCompanyName());
		int i = 1;
		for (Service s : allServices) {
			serviceBox.getItems().add(i + ". " + s.getTitle() + "  |  $" + s.getPrice());
			if (cell.getServiceId() == s.getId()) serviceBox.setValue(i + ". " + s.getTitle() + "  |  $" + s.getPrice());
		i++;
		}
		
		ArrayList<Customer> allCustomers = customerController.getAllCustomers(loggedInUser.getCompanyName());
		
		i = 1;
		for (Customer c : allCustomers) {
			customerBox.getItems().add(i + ". " + c.getName() + "  |  " + c.getEmail());
			if (cell.getCustomerId() == c.getId()) customerBox.setValue(i + ". " + c.getName() + "  |  " + c.getEmail());
			i++;
		}
		
		pane.add(new Label("Customer:"), 0, 0);
		pane.add(customerBox, 0, 1);
		pane.add(new Label("Service:"), 0, 2);
		pane.add(serviceBox, 0, 3);
		pane.add(new Label("Price:"), 0, 4);
		pane.add(priceField, 0, 5);
		pane.add(editBtn, 0, 6);
		
		Scene scene = new Scene(pane, 300, 600);
		Stage window = new Stage();
		
		editBtn.setOnAction(e -> {
			
			int serviceVal = serviceBox.getValue().indexOf(".");
			serviceVal = Integer.parseInt(serviceBox.getValue().substring(0, serviceVal)) - 1;
			
			int customerVal = customerBox.getValue().indexOf(".");
			customerVal = Integer.parseInt(customerBox.getValue().substring(0, customerVal)) - 1;
			
			int id = cell.getID();
			int customerId = allCustomers.get(customerVal).getId();
			int serviceId = allServices.get(serviceVal).getId();
			double price = Double.parseDouble(priceField.getText());
			
			uncompLv.refresh();
			compLv.refresh();
			window.close();
			
			String message = orderController.editOrder(id, customerId, serviceId, price);
			
			setList();
			
			if (message.contains("successfully"))
				Popup.displaySuccessMessage(message);
			else
				Popup.displayErrorMessage(message);
		});
		
		window.setTitle("Edit " + cell.getCustomerId() + "'s order");
		window.setScene(scene);
		window.show();
	}
	
	private void remove(Cell cell) {
		String message = orderController.deleteOrder(cell.getID());
		
		uncompLv.refresh();
		compLv.refresh();
		
		setList();
		
		Popup.displayErrorMessage(message);
	}
	
	private String getTodaysDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
	}
	
	public class Cell extends HBox {
		Label paidStatusLabel = new Label();
		Label customerLabel = new Label();
		Label priceLabel = new Label();
		Label dateLabel = new Label();
		Button editButton = new Button("Edit");
		Button removeButton = new Button("Remove");
		Button completeButton = new Button();
		int customerId;
		double price;
		boolean completed;
		int serviceId;
		String date;
		int shopId;
		String companyName;
		int id;
		
		String paidStatus;

		Cell(int customerId, double price, boolean completed, int serviceId, String date, int shopId, String companyName, int id, String paidStatus) {
			super();
			
			this.serviceId = serviceId;
			this.completed = completed;
			this.shopId = shopId;
			this.companyName = companyName;
			this.id = id;
			
			if (paidStatus == null || paidStatus.equals("SENT"))
				this.paidStatus = "UNPAID";
			else
				this.paidStatus = paidStatus;
			paidStatusLabel.setText("Pay Status: " + this.paidStatus);
			paidStatusLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(paidStatusLabel, Priority.ALWAYS);
			
			this.customerId = customerId;
			customerLabel.setText("Customer id: " + customerId);
			customerLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(customerLabel, Priority.ALWAYS);
			
			this.price = price;
			priceLabel.setText("Price: $" + price);
			priceLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(priceLabel, Priority.ALWAYS);
			
			this.date = date;
			dateLabel.setText("Date: " + date);
			dateLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(dateLabel, Priority.ALWAYS);
			
			
			if(completed) {
				completeButton.setGraphic(new ImageView(new Image("view/images/uncomplete.png")));
				completeButton.setTooltip(new Tooltip("Uncomplete " + id));
				completeButton.setText("Uncomplete");
				completeButton.setOnAction(e -> {
					String message = orderController.setOrderToUnCompleted(id, this.paidStatus);
					
					if (message.contains("set to completed!")) {
						Popup.displaySuccessMessage(message);
						orderController.cancelInvoice(id);
					}
					else
						Popup.displayErrorMessage(message);
					
					uncompLv.refresh();
					compLv.refresh();
					
					setList();
				});
			} else {
				completeButton.setGraphic(new ImageView(new Image("view/images/complete.png")));
				completeButton.setTooltip(new Tooltip("Complete " + id));
				completeButton.setText("Complete");
				completeButton.setOnAction(e -> {
					if(!orderController.isEmailValid(id)){
                        Popup.displayErrorMessage("Invalid email!");
                    }
					else {
						orderController.setOrderToCompleted(id);
						orderController.sendInvoice(id);
					}

					uncompLv.refresh();
					compLv.refresh();
					
					setList();
				});
			}
			
			editButton.setGraphic(new ImageView(new Image("view/images/edit.png")));
			editButton.setTooltip(new Tooltip("Edit " + id));
			editButton.setOnAction(e -> {
				if (this.paidStatus.equalsIgnoreCase("PAID"))
					Popup.displayErrorMessage("Already paid order, can not be edited!");
				else
					edit(this);
			});
			
			removeButton.setGraphic(new ImageView(new Image("view/images/remove.png")));
			removeButton.setTooltip(new Tooltip("Remove " + id));
			removeButton.setOnAction(e -> {
				if (loggedInUser.getStatus().equalsIgnoreCase("super_admin") || loggedInUser.getStatus().equalsIgnoreCase("admin"))
					remove(this);
				else
					Popup.displayErrorMessage("You do not have permission to remove orders");
			});
			
			this.getChildren().addAll(customerLabel, priceLabel, paidStatusLabel, dateLabel, completeButton ,editButton, removeButton);
		}

		public int getCustomerId() {
			return customerId;
		}

		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

		public int getServiceId() {
			return serviceId;
		}

		public void setServiceId(int serviceId) {
			this.serviceId = serviceId;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getShopId() {
			return shopId;
		}

		public void setShopId(int shopId) {
			this.shopId = shopId;
		}

		public String getCompanyName() {
			return companyName;
		}
		
		public String getPaidStatus() {
			return paidStatus;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		
		public int getID() {
			return id;
		}
		
		public Order getAsOrder() {
			return new Order(customerId, serviceId, date, shopId, companyName, price, completed);
		}
	}
}

package view;

import java.io.IOException;
import java.util.ArrayList;

import controller.EmployeeController;
import controller.ShopController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.Employee;
import model.Shop;
import security.PasswordHasher;

public class EmployeeView {
	
	private ArrayList<Cell> list;
	private ListView<Cell> lv;
	
	private ShopController shopController;
	private EmployeeController employeeController;
	
	Employee loggedInUser;
	
	public EmployeeView() {
		list = new ArrayList<Cell>();
		employeeController = new EmployeeController();
		shopController = new ShopController();
	}
	
	public BorderPane getCenter() {
		ObservableList<Cell> obsList;
		
		loggedInUser = Employee.getLoggedInUser();
		
		setList();
		
		BorderPane bp = new BorderPane();
		Button createButton = new Button("Create");
		
		createButton.setOnAction(e -> {
			if (loggedInUser.getStatus().equalsIgnoreCase("admin") || loggedInUser.getStatus().equalsIgnoreCase("super_admin"))
				create();
			else
				Popup.displayErrorMessage("You do not have permission to create users!");
		});
		
		lv = new ListView<Cell>();
		obsList = FXCollections.observableList(list);
		lv.setItems(obsList);
		bp.setCenter(lv);
		bp.setTop(createButton);
		return bp;
	}
	
	private void setList() {
		ArrayList<Employee> allEmployees;

			allEmployees = employeeController.getAllEmployees(Employee.getLoggedInUser().getCompanyName());
			list.clear();
			
			for (Employee employee : allEmployees) {
				list.add(new Cell(employee.getName(), employee.getEmail(), employee.getPhone(), employee.getCompanyName(), employee.getShopId(), employee.getId(), employee.getStatus()));
			}
	}
	
	private void create() {
		GridPane pane = new GridPane();
		Button button = new Button("Create");
		TextField nameField = new TextField();
		TextField passwordField = new PasswordField();
		ComboBox<String> statusBox = new ComboBox<>();
		TextField emailField = new TextField();
		ComboBox<String> shopBox = new ComboBox<>();
		TextField phoneField = new TextField();
		
		ArrayList<Shop> allShops = shopController.getAllShops(loggedInUser.getCompanyName());
		int i = 1;
		for (Shop s : allShops) {
			shopBox.getItems().add(i + ". " + s.getName() + "  |  " + s.getAddress());
			if (i == 1) shopBox.setValue(i + ". " + s.getName() + "  |  " + s.getAddress());
		i++;
		}
		
		statusBox.getItems().addAll("User", "Admin");
		statusBox.setValue("User");
		
		pane.add(new Label("Name:"), 0, 0);
		pane.add(nameField, 0, 1);
		pane.add(new Label("Email:"), 0, 2);
		pane.add(emailField, 0, 3);
		pane.add(new Label("Phone:"), 0, 4);
		pane.add(phoneField, 0, 5);
		pane.add(new Label("Status:"), 0, 6);
		pane.add(statusBox, 0, 7);
		pane.add(new Label("Shop:"), 0, 8);
		pane.add(shopBox, 0, 9);
		pane.add(new Label("Password:"), 0, 10);
		pane.add(passwordField, 0, 11);
		pane.add(button, 0, 12);
		
		Scene scene = new Scene(pane, 300, 600);
		Stage window = new Stage();
		
		button.setOnAction(e -> {
			
			int val = shopBox.getValue().indexOf(".");
			val = Integer.parseInt(shopBox.getValue().substring(0, val)) - 1;
			
			String phone = phoneField.getText();
			String email = emailField.getText();
			String name = nameField.getText();
			String companyName = loggedInUser.getCompanyName();
			int shopId = allShops.get(val).getId();
			String password = PasswordHasher.hashPassword(passwordField.getText());
			String status = statusBox.getValue();
			
			lv.refresh();
			window.close();
			
			String message = "";
			
			if (status.equalsIgnoreCase("user"))
				message = employeeController.newUser(name, email, phone, password, companyName, shopId);
			else if (status.equalsIgnoreCase("admin"))
				message = employeeController.newAdmin(name, email, phone, password, companyName, shopId);
			else
				message = "No such status";
				
			// update view
			setList();
			
			// must display message last
			if (message.contains("successfully"))
				Popup.displaySuccessMessage(message);
			else
				Popup.displayErrorMessage(message);
		});
		window.setTitle("Create new employee");
		window.setScene(scene);
		window.show();
	}
	
	private void edit(Cell cell) {
		GridPane pane = new GridPane();
		Button button = new Button("Edit");
		TextField nameField = new TextField("" + cell.getName());
		ComboBox<String> shopBox = new ComboBox<>();
		TextField emailField = new TextField("" + cell.getEmail());
		TextField phoneField = new TextField("" + cell.getPhone());
		
		ArrayList<Shop> allShops = shopController.getAllShops(loggedInUser.getCompanyName());
		int i = 1;
		for (Shop s : allShops) {
			shopBox.getItems().add(i + ". " + s.getName() + "  |  " + s.getAddress());
			if (cell.getShopId() == s.getId()) shopBox.setValue(i + ". " + s.getName() + "  |  " + s.getAddress());
		i++;
		}
		
		pane.add(new Label("Name:"), 0, 0);
		pane.add(nameField, 0, 1);
		pane.add(new Label("Shop Id:"), 0, 2);
		pane.add(shopBox, 0, 3);
		pane.add(new Label("Email:"), 0, 4);
		pane.add(emailField, 0, 5);
		pane.add(new Label("Phone:"), 0, 6);
		pane.add(phoneField, 0, 7);
		pane.add(button, 0, 8);
		
		Scene scene = new Scene(pane, 300, 600);
		Stage window = new Stage();
		
		button.setOnAction(e -> {
			
			int val = shopBox.getValue().indexOf(".");
			val = Integer.parseInt(shopBox.getValue().substring(0, val)) - 1;
			
			int id = cell.getID();
			String phone = phoneField.getText();
			String email = emailField.getText();
			String name = nameField.getText();
			int shopId = allShops.get(val).getId();
			
			String message = "";
			
			if (cell.getStatus().equalsIgnoreCase("user"))
				message = employeeController.editUser(phone, email, name, shopId, id);
			else if (cell.getStatus().equalsIgnoreCase("admin"))
				message = employeeController.editAdmin(phone, email, name, shopId, id);
			else
				message = "No such status";
			
			lv.refresh();
			window.close();
			
			// update view
			setList();
			
			// must display message last
			if (message.contains("successfully"))
				Popup.displaySuccessMessage(message);
			else
				Popup.displayErrorMessage(message);
		});
		window.setTitle("Edit " + cell.getName());
		window.setScene(scene);
		window.show();
	}
	
	private void remove(Cell cell) {
		String message = "";
		if (cell.getStatus().equalsIgnoreCase("user"))
			message = employeeController.deleteUser(cell.getID(), cell.getName());
		else if (cell.getStatus().equalsIgnoreCase("admin"))
			message = employeeController.deleteAdmin(cell.getID(), cell.getName());
		else message = "No such status";
		
		lv.refresh();
		
		// update view
		setList();
		
		// must display message last
		Popup.displayErrorMessage(message);
	}
	
	public class Cell extends HBox {
		Label nameLabel = new Label();
		Label statusLabel = new Label();
		Label emailLabel = new Label();
		Label phoneLabel = new Label();
		Button editButton = new Button("Edit");
		Button removeButton = new Button("Remove");
		String name;
		String email;
		String phone;
		String company;
		int shopId;
		int id;
		String status;

		Cell(String name, String email, String phone, String company, int shopId, int id, String status) {
			super();
			
			this.id = id;
			this.company = company;
			this.shopId = shopId;
			
			this.name = name;
			nameLabel.setText("Name: " + name);
			nameLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(nameLabel, Priority.ALWAYS);
			
			this.status = status;
			statusLabel.setText("Status: " + status);
			statusLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(statusLabel, Priority.ALWAYS);
			
			this.email = email;
			emailLabel.setText("Email: " + email);
			emailLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(emailLabel, Priority.ALWAYS);
			
			this.phone = phone;
			phoneLabel.setText("Phone: " + phone);
			phoneLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(phoneLabel, Priority.ALWAYS);
			
			editButton.setOnAction(e -> {
				if (loggedInUser.getStatus().equalsIgnoreCase("admin") || loggedInUser.getStatus().equalsIgnoreCase("super_admin"))
					edit(this);
				else
					Popup.displayErrorMessage("You do not have permission to edit users!");
			});
			
			removeButton.setOnAction(e -> {
				if (loggedInUser.getStatus().equalsIgnoreCase("super_admin"))
					remove(this);
				else
					Popup.displayErrorMessage("You do not have permission to remove users!");
			});
			
			this.getChildren().addAll(nameLabel, statusLabel, emailLabel, phoneLabel, editButton, removeButton);
		}

		public String getName() {
			return name;
		}
		
		public String getStatus() {
			return status;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getCompany() {
			return company;
		}
		
		public int getID() {
			return id;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public int getShopId() {
			return shopId;
		}

		public void setShopId(int shopId) {
			this.shopId = shopId;
		}
		
	}
}

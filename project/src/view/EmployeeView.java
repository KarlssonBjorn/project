package view;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import controller.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Employee;
import view.ServiceView.Cell;

public class EmployeeView {
	
	private ArrayList<Cell> list;
	private ListView<Cell> lv;
	
	private EmployeeController employeeController;
	
	public EmployeeView() {
		list = new ArrayList<Cell>();
		employeeController = new EmployeeController();
	}
	
	public BorderPane getCenter() {
		ObservableList<Cell> obsList;
		
		setList();
		
		BorderPane bp = new BorderPane();
		Button createButton = new Button("Create");
		
		//TODO Make this check permissions
		createButton.setOnAction(e -> create());
		
		lv = new ListView<Cell>();
		obsList = FXCollections.observableList(list);
		lv.setItems(obsList);
		bp.setCenter(lv);
		bp.setTop(createButton);
		return bp;
	}
	
	private void setList() {
		ArrayList<Employee> allEmployees = employeeController.getAllEmployees("company"); // TODO get company from logged in user
		
		list.clear();
		
		for (Employee employee : allEmployees) {
			list.add(new Cell(employee.getName(), employee.getEmail(), employee.getPhone(), employee.getCompanyName(), employee.getShopId(), employee.getId(), employee.getStatus()));
		}
	}
	
	private void create() {
		GridPane pane = new GridPane();
		Button button = new Button("Create");
		TextField nameField = new TextField();
		TextField companyField = new TextField();
		TextField shopIdField = new TextField();
		TextField emailField = new TextField();
		TextField phoneField = new TextField();
		
		// TODO remove shop id
		// TODO add password, status
		pane.add(new Label("Name:"), 0, 0);
		pane.add(nameField, 0, 1);
		pane.add(new Label("Company:"), 0, 2);
		pane.add(companyField, 0, 3);
		pane.add(new Label("Shop Id:"), 0, 4);
		pane.add(shopIdField, 0, 5);
		pane.add(new Label("Email:"), 0, 6);
		pane.add(emailField, 0, 7);
		pane.add(new Label("Phone:"), 0, 8);
		pane.add(phoneField, 0, 9);
		pane.add(button, 0, 10);
		
		Scene scene = new Scene(pane, 300, 600);
		Stage window = new Stage();
		
		button.setOnAction(e -> {
			
			String phone = phoneField.getText();
			String email = emailField.getText();
			String name = nameField.getText();
			String companyName = "company"; // TODO get company from logged in user
			int shopId = 1; // TODO get shopId from logged in user
			String password = "password"; // TODO set password
			String status = "user"; // TODO set status
			
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
		});
		window.setTitle("Create new employee");
		window.setScene(scene);
		window.show();
	}
	
	private void edit(Cell cell) {
		GridPane pane = new GridPane();
		Button button = new Button("Edit");
		TextField nameField = new TextField("" + cell.getName());
		TextField companyField = new TextField("" + cell.getCompany());
		TextField shopIdField = new TextField("" + cell.getShopId());
		TextField emailField = new TextField("" + cell.getEmail());
		TextField phoneField = new TextField("" + cell.getPhone());
		
		// TODO remove company
		pane.add(new Label("Name:"), 0, 0);
		pane.add(nameField, 0, 1);
		pane.add(new Label("Company:"), 0, 2);
		pane.add(companyField, 0, 3);
		pane.add(new Label("Shop Id:"), 0, 4);
		pane.add(shopIdField, 0, 5);
		pane.add(new Label("Email:"), 0, 6);
		pane.add(emailField, 0, 7);
		pane.add(new Label("Phone:"), 0, 8);
		pane.add(phoneField, 0, 9);
		pane.add(button, 0, 10);
		
		Scene scene = new Scene(pane, 300, 600);
		Stage window = new Stage();
		
		button.setOnAction(e -> {
			
			int id = cell.getID();
			String phone = phoneField.getText();
			String email = emailField.getText();
			String name = nameField.getText();
			int shopId = Integer.parseInt(shopIdField.getText());
			
			// TODO display message
			String message = "";
			
			if (cell.getStatus().equalsIgnoreCase("user"))
				message = employeeController.editUser(phone, email, name, shopId, id);
			else if (cell.getStatus().equalsIgnoreCase("admin"))
				message = employeeController.editAdmin(phone, email, name, shopId, id);
			else
				message = "No such status";
			
			System.out.println(message);
			
			lv.refresh();
			window.close();
			
			// update view
			setList();
		});
		window.setTitle("Edit " + cell.getName());
		window.setScene(scene);
		window.show();
	}
	
	private void remove(Cell cell) {
		// TODO add remove
	}
	
	public class Cell extends HBox {
		Label nameLabel = new Label();
		Label companyLabel = new Label();
		Label shopIdLabel = new Label();
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
			
			this.email = email;
			this.phone = phone;
			this.id = id;
			this.status = status;
			
			this.name = name;
			nameLabel.setText("Name: " + name);
			nameLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(nameLabel, Priority.ALWAYS);
			
			this.company = company;
			companyLabel.setText("Company: " + company);
			companyLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(companyLabel, Priority.ALWAYS);
			
			this.shopId = shopId;
			shopIdLabel.setText("Shop Id: " + shopId);
			shopIdLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(shopIdLabel, Priority.ALWAYS);
			
			editButton.setOnAction(e -> {
				edit(this);
			});
			
			removeButton.setOnAction(e -> {
				remove(this);
			});
			
			this.getChildren().addAll(nameLabel, companyLabel, shopIdLabel, editButton, removeButton);
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

package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import controller.OrderController;
import controller.ServiceController;
import javafx.geometry.HPos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Employee;
import model.Order;
import model.Service;

public class HomeView {

	public String[] buttons = {"Homepage", "Information", "Settings", "Help"};
	
	private int lastPressed = 0;
	
	private ArrayList<Order> allOrders;
	private ArrayList<Service> allServices;
	private OrderController orderController;
	private ServiceController serviceController;
	
	public HomeView() {
		orderController = new OrderController();
		serviceController = new ServiceController();
	}
	
	public String[] getButtons() {
		return buttons;
	}

	public GridPane getPane(int value) {
		
		GridPane pane = new GridPane();
		pane.getChildren().clear();
		switch(value) {
		case 0:
			displayHomePage(pane);
			break;
		case 1:
			displayInformationPage(pane);
			break;
		case 2:
			displaySettingsPage(pane);
			break;
		case 3:
			displayHelpPage(pane);
			break;
		default:
			break;
		}
		pane.getStylesheets().add("view/css/pane.css");
		return pane;
	}
	
	private void displayHomePage(GridPane pane) {
		
		Employee employee;
		
			employee = Employee.getLoggedInUser();
			allOrders = orderController.getAllOrdersCompany(employee.getCompanyName());
			allServices = serviceController.getAllServices(employee.getCompanyName());
			
			Text companyHeader = new Text("Your Company: " + employee.getCompanyName());
			companyHeader.setId("companyHeader");
			
			Text loggedInAsHeader = new Text("Logged In As: " + employee.getName());
			loggedInAsHeader.setId("loggedInAsHeader");
			
			GridPane centerPane = new GridPane();
			centerPane.setVgap(60);
			
			centerPane.add(companyHeader, 0, 0);
			centerPane.add(loggedInAsHeader, 1, 0);
			centerPane.add(this.createBarChart(), 1, 1);
			centerPane.add(this.createLineChart(), 0, 2);
			centerPane.add(this.createMostPopularServiceChart(), 1, 2);
			
			GridPane.setHalignment(companyHeader, HPos.CENTER);
			GridPane.setHalignment(loggedInAsHeader, HPos.CENTER);
			
			pane.getChildren().add(centerPane);
	}
	
	private void displayInformationPage(GridPane pane) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(20);
		
		Text infromationHeader = new Text("Information");
		infromationHeader.setId("informationHeader");
		
		Text informationText = new Text("This is an application for handeling customers,\n "
				+ "services, orders and shops. You can also give notifications to your \n"
				+ "customers via email. It is also possible to send invocies along with the notification.\n"
				+ "There is also an android application avaliable!");
		informationText.setId("informationText");
		informationText.setTextAlignment(TextAlignment.CENTER);
		
		centerPane.add(infromationHeader, 0, 0);
		centerPane.add(informationText, 0, 1);
		
		GridPane.setHalignment(infromationHeader, HPos.CENTER);
		
		pane.getChildren().add(centerPane);
		
	}
	
	private void displaySettingsPage(Pane pane) {
		GridPane centerPane = new GridPane();
		
		Text commingSoon = new Text("Cooming Soon!");
		
		centerPane.add(commingSoon, 0, 0);
		
		pane.getChildren().add(centerPane);
	}
	
	private void displayHelpPage(Pane pane) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(15);
		
		Text orderHeader = new Text("Order");
		Text serviceHeader = new Text("Service");
		Text customerHeader = new Text("Customer");
		Text employeeHeader = new Text("Employee");
		Text otherHeader = new Text("Other");
		
		Text orderText = new Text("Orders can be found in the main navigation.\n"
				+ "Orders contain a service and a customer, along with a price \n"
				+ "Each order have a remove button if you need to delete an order. \n"
				+ "They also have the functionallity to edit an order. \n"
				+ "When an order has been completed make sure to press completed, \n"
				+ "This will notify the customer via email along with an invocie. \n"
				+ "When the customer has paid it will be shown in the column.");
		Text serviceText = new Text("Services can be found in the main navigfation.\n"
				+ "Each service contains a title, description and price.\n"
				+ "Just like orders you can edit and delete each order\n"
				+ "if you made a misstake when creating the order.");
		Text customerText = new Text("Customers can be found in the main navigfation.\n"
				+ "Each customer have a name, email, phone number, and an address.\n"
				+ "Just like the other columns you can edit and delete each customer\n"
				+ "if you made a misstake when creating the customer.");
		Text employeeText = new Text("Employees can be found in the main navigfation.\n"
				+ "Each employee have a name, email, phone number, and a shop.\n"
				+ "Just like the other columns you can edit and delete each employee\n"
				+ "if you made a misstake when creating the customer.\n"
				+ "Each employee have a status, these are, Super Admin, Admin, and User.\n"
				+ "Each company have one Super Admin. Each shop have one Admin,\n"
				+ "but each shop can have multiple users");
		Text otherText = new Text("Logout - Can be found in settings.\n"
				+ "Static - Can be found on the main page. \n"
				+ "Contact - Email us at some.email@email.com");
		
		orderText.setTextAlignment(TextAlignment.CENTER);
		serviceText.setTextAlignment(TextAlignment.CENTER);
		customerText.setTextAlignment(TextAlignment.CENTER);
		employeeText.setTextAlignment(TextAlignment.CENTER);
		otherText.setTextAlignment(TextAlignment.CENTER);
		GridPane.setHalignment(orderText, HPos.CENTER);
		GridPane.setHalignment(serviceText, HPos.CENTER);
		GridPane.setHalignment(customerText, HPos.CENTER);
		GridPane.setHalignment(employeeText, HPos.CENTER);
		GridPane.setHalignment(otherText, HPos.CENTER);
		
		GridPane.setHalignment(orderHeader, HPos.CENTER);
		GridPane.setHalignment(serviceHeader, HPos.CENTER);
		GridPane.setHalignment(customerHeader, HPos.CENTER);
		GridPane.setHalignment(employeeHeader, HPos.CENTER);
		GridPane.setHalignment(otherHeader, HPos.CENTER);
		
		orderHeader.setId("orderHeader");
		serviceHeader.setId("serviceHeader");
		customerHeader.setId("customerHeader");
		employeeHeader.setId("employeeHeader");
		otherHeader.setId("otherHeader");
		
		centerPane.add(orderHeader, 0, 1);
		centerPane.add(orderText, 0, 2);
		
		centerPane.add(serviceHeader, 0, 3);
		centerPane.add(serviceText, 0, 4);
		
		centerPane.add(customerHeader, 0, 5);
		centerPane.add(customerText, 0, 6);
		
		centerPane.add(employeeHeader, 0, 7);
		centerPane.add(employeeText, 0, 8);
		
		centerPane.add(otherHeader, 0, 9);
		centerPane.add(otherText, 0, 10);
		
		pane.getChildren().add(centerPane);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Chart createLineChart() {
		
		CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        
        chart.setTitle("Revenue");
        chart.setId("lineChart");
        chart.setMinSize(0, 0);
        chart.setPrefSize(750, 300);
        chart.setCreateSymbols(false);
        
        XYChart.Series series = new XYChart.Series();
        series.setName("Months");
        
        if (allOrders == null) return chart;
        
        HashMap<String, Double> values = new HashMap<>();
        
        for (Order order : allOrders) {
        	// includes only completed orders
        	if (!order.getCompleted()) continue;
        	
        	String date = order.getDate();
        	if (values.get(date) == null) {
        		values.put(date, order.getPrice());
        	} else {
        		values.put(date, values.get(date) + order.getPrice());
        	}
        }
        
        Map<String, Double> sortedValues = new TreeMap<>(values);
        
        int i = 0;
        Iterator it = sortedValues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            
            series.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
            it.remove();
            
            // display max 30
            if (i >= 30) break;
            i++;
        }
        
        chart.getData().add(series);

        return chart;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Chart createBarChart() {
		CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Quarterly Revenue");
        xAxis.setLabel("Quarter");
        yAxis.setLabel("Revenue");
        
        bc.setPrefSize(750, 300);
        
        HashMap<String, Double> values = new HashMap<>();
        values.put("Q1", 0.0);
        values.put("Q2", 0.0);
        values.put("Q3", 0.0);
        values.put("Q4", 0.0);
        
        if (allOrders == null) return bc;
        
        for (Order order : allOrders) {
        	// includes only completed orders
        	if (!order.getCompleted()) continue;
        	
        	int date = Integer.parseInt(order.getDate().substring(5, 7));
        	
        	if (date <= 3)
        		values.put("Q1", values.get("Q1") + order.getPrice());
        	else if (date <= 6 && date >= 4)
        		values.put("Q2", values.get("Q2") + order.getPrice());
        	else if (date <= 9 && date >= 7)
        		values.put("Q3", values.get("Q3") + order.getPrice());
        	else
        		values.put("Q4", values.get("Q4") + order.getPrice());
        }
 
        XYChart.Series series = new XYChart.Series();
        
        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            
            series.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
            it.remove();
        }  
        
        bc.getData().add(series);
        
        return bc;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Chart createMostPopularServiceChart() {
		CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Most Popular Services");
        xAxis.setLabel("Service");
        yAxis.setLabel("Count");
        
        bc.setPrefSize(750, 300);
 
        XYChart.Series series = new XYChart.Series();      
        
        if (allServices == null || allOrders == null) return bc;
        
        HashMap<Integer, Integer> values = new HashMap<>();
        // setup
        for (Service service : allServices) {
        	if (values.get(service.getId()) == null)
        		values.put(service.getId(), 0);
        }
        
        for (Order order : allOrders) {
        	// includes only completed orders
        	if (!order.getCompleted()) continue;
        	
        	// Making sure the service has not been deleted
        	if (values.get(order.getServiceId()) != null) {
        		values.put(order.getServiceId(), values.get(order.getServiceId()) + 1);
        	}
        }
        
        
        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            
            String title = "";
            
            for (Service service : allServices) {
            	if (Integer.parseInt(pair.getKey().toString()) == service.getId()) {
            		title = service.getTitle();
            		break;
            	}
            }
            
            series.getData().add(new XYChart.Data(title, pair.getValue()));
            it.remove();
        }
        
        bc.getData().add(series);
        
        return bc;
	}

	public BorderPane getCenter() {
		BorderPane bp = new BorderPane();
		bp.setLeft(getBar(bp));
		bp.setCenter(getPane(0));
		return bp;
	}
	
	public VBox getBar(BorderPane bp) {
		Button[] buttons = new Button[this.buttons.length];
		VBox vBox = new VBox();
		
		vBox.prefWidthProperty().bind(bp.widthProperty().multiply(.15));
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button(this.buttons[i]);
			buttons[i].prefWidthProperty().bind(vBox.widthProperty());
			buttons[i].prefHeightProperty().bind(vBox.heightProperty().multiply(.1));
			
			int index = i;
			buttons[i].setOnAction(e -> {
				if(lastPressed != index) {
					bp.setCenter(getPane(index));
					lastPressed = index;
				}
			});
		}
		
		vBox.getChildren().addAll(buttons);
		vBox.getStylesheets().addAll("view/css/buttons.css");
		return vBox;
	}
	
}

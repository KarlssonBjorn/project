package view;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView {
	
	private Stage window = null;
	private final int WIDTH = 1800;
	private final int HEIGHT = 900;
	private String[] menuText = {"Home", "Order", "Service", "Customer", "Employee", "Shop", "Logout"};
	private int lastPressed = 0;
	
	private HomeView hv;
	private OrderView ov;
	private EmployeeView ev;
	private CustomerView cv;
	private ServiceView sv;
	private ShopView shopView;
	
	public MainView() {
		hv = new HomeView();
		ov = new OrderView();
		ev = new EmployeeView();
		cv = new CustomerView();
		sv = new ServiceView();
		shopView = new ShopView();
	}
	
	public void render(Stage primaryStage) {
		
		window = primaryStage;
		BorderPane pane = new BorderPane();
		
		Image[] menuImages = {
			new Image("view/images/home.png"),
			new Image("view/images/order.png"),
			new Image("view/images/service.png"),
			new Image("view/images/customer.png"),
			new Image("view/images/employee.png"),
			new Image("view/images/shop.png"),
			new Image("view/images/home.png")
		};
		
		ToggleGroup group = new ToggleGroup();
		ToggleButton[] menuButtons = new ToggleButton[menuText.length];
		for(int i = 0; i < menuButtons.length; i++) {
			menuButtons[i] = new ToggleButton(menuText[i]);
			menuButtons[i].getStylesheets().add("view/css/menu-buttons.css");
			menuButtons[i].setGraphic(new ImageView(menuImages[i]));
			menuButtons[i].setToggleGroup(group);
		}
		
		menuButtons[0].setSelected(true);
		
		menuButtons[0].setOnAction(e -> {
			if(lastPressed != 0) {
				pane.setCenter(hv.getCenter());
				lastPressed = 0;
			}
		});
		menuButtons[1].setOnAction(e -> {
			if(lastPressed != 1) {
				pane.setCenter(ov.getCenter(window));
				lastPressed = 1;
			}
		});
		menuButtons[2].setOnAction(e -> {
			if(lastPressed != 2) {
				pane.setCenter(sv.getCenter());
				lastPressed = 2;
			}
		});
		menuButtons[3].setOnAction(e -> {
			if(lastPressed != 3) {
				pane.setCenter(cv.getCenter());
				lastPressed = 3;
			}
		});
		menuButtons[4].setOnAction(e -> {
			if(lastPressed != 4) {
				pane.setCenter(ev.getCenter());
				lastPressed = 4;
			}
		});
		menuButtons[5].setOnAction(e -> {
			if(lastPressed != 5) {
				pane.setCenter(shopView.getCenter());
				lastPressed = 5;
			}
		});
		menuButtons[6].setOnAction(e -> {
			if(lastPressed != 6) {
				window.close();
				new LoginView().render(window);
				
				lastPressed = 6;
			}
		});
		
		ToolBar toolBarH = new ToolBar();
		toolBarH.getItems().addAll(menuButtons);
		toolBarH.setOrientation(Orientation.HORIZONTAL);
		
		pane.setCenter(hv.getCenter());
		pane.setTop(toolBarH);
		Scene scene = new Scene(pane, WIDTH, HEIGHT);
		
		window.setScene(scene);
	    window.setMaximized(false);
	    window.setTitle("Service Application");
	    window.show();
	}
}

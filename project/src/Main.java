import javafx.application.Application;
import javafx.stage.Stage;
import model.database.CreateTables;
import view.LoginView;


public class Main extends Application {
	
	public static void main(String[] args) {
		
		// Create new tables (if it does not already exists)
		CreateTables newTables = new CreateTables();
		newTables.executeCreateAllTables();
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new LoginView().render(primaryStage);
	}
}

package view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomeView implements IView {

public String[] buttons = {"Homepage", "Information", "Help", "Settings"};
	
	public String[] getButtons() {
		return buttons;
	}

	public GridPane getPane(int value) {
		GridPane pane = new GridPane();
		pane.getChildren().clear();
		switch(value) {
		case 0:
		case 1:
		case 2:
		case 3:
			pane.getChildren().add(new Text("Home" + buttons[value]));
			break;
		default:
			break;
		}
		
		return pane;
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
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button(this.buttons[i]);
			int index = i;
			buttons[i].setOnAction(e -> {
				bp.setCenter(getPane(index));
			});
		}
		
		vBox.getChildren().addAll(buttons);
		return vBox;
	}
	
}
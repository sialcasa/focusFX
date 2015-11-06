package de.saxsys.focusfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Starter extends Application {
	
	
	private static final VBox V_BOX = new VBox();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vbox4 = createView();
		VBox vbox3 = createView();
		VBox vbox2 = createView();
		VBox vbox1 = createView();
		
		VBox bigBox = new VBox(vbox1, vbox2, vbox3, vbox4);
		
		FXFocusManager.setParentsToTraverse(vbox1, vbox2, vbox3, vbox4);
		
		bigBox.setSpacing(5);
		
		primaryStage.setScene(new Scene(bigBox));
		primaryStage.show();
		
	}
	
	private VBox createView() {
		TextField tf1 = new TextField("1");
		TextField tf2 = new TextField("2");
		TextField tf3 = new TextField("3");
		TextField tf4 = new TextField("4");
		TextField tf5 = new TextField("5");
		
		VBox vbox = new VBox(tf1, tf2, tf3, tf4, tf5);
		
		FXFocusManager.setNodesToFocus(vbox, FXCollections.observableArrayList(tf1, tf2, tf3, tf4, tf5));
		FXFocusManager.applyDefaultPolicy(vbox);
		return vbox;
	}
}

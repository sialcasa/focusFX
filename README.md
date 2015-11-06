FocusManager for JavaFX
=========
First implementation of a FocusManager for JavaFX. Feedback welcome in the issuetracker.



[![Build Status](https://travis-ci.org/sialcasa/focusFX.svg?branch=master)](https://travis-ci.org/sialcasa/jfx-testrunner)
###Maven dependency (Sonatype Snapshot Repo)###
```
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>focusfx</artifactId>
		<version>0.1-SNAPSHOT</version>
</dependency>
```

###Example###

```Java

public class FocusExample extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		TextField tf1 = new TextField("1");
		TextField tf2 = new TextField("2");
		TextField tf3 = new TextField("3");
		TextField tf4 = new TextField("4");
		TextField tf5 = new TextField("5");
		
		VBox vbox1 = new VBox(tf1, tf2, tf3, tf4, tf5);
		
		//Set Nodes which should get focustraversed in the container
		FXFocusManager.setNodesToFocus(vbox1, FXCollections.observableArrayList(tf5, tf3, tf2, tf4));
		FXFocusManager.applyDefaultPolicy(vbox1);
		
		TextField tf6 = new TextField("6");
		TextField tf7 = new TextField("7");
		TextField tf8 = new TextField("8");
		TextField tf9 = new TextField("9");
		TextField tf10 = new TextField("10");
		
		VBox vbox2 = new VBox(tf6, tf7, tf8, tf9, tf10);
		
		//Set Nodes which should get focustraversed in the container
		FXFocusManager.setNodesToFocus(vbox2, FXCollections.observableArrayList(tf6, tf7, tf8, tf9));
		FXFocusManager.applyDefaultPolicy(vbox2);
		
		TextField tf11 = new TextField("11");
		TextField tf12 = new TextField("12");
		TextField tf13 = new TextField("13");
		TextField tf14 = new TextField("14");
		TextField tf15 = new TextField("15");
		
		VBox vbox3 = new VBox(tf11, tf12, tf13, tf14, tf15);
		
		//Set Nodes which should get focustraversed in the container
		FXFocusManager.setNodesToFocus(vbox3, FXCollections.observableArrayList(tf11, tf12, tf13, tf14, tf15));
		FXFocusManager.applyDefaultPolicy(vbox3);
		
		//Chain Parents which are managed by the FXFocusManager
		FXFocusManager.setParentsToTraverse(vbox1, vbox3, vbox2);
		
		primaryStage.setScene(new Scene(new VBox(vbox1, vbox2, vbox3)));
		primaryStage.show();
	}	
}
```


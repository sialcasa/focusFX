FocusManager for JavaFX
=========
First implementation of a FocusManager for JavaFX. Feedback is welcome in the issuetracker. Please file enhancement requests for your use-cases.

Currently I'm working on the API which is based on the [java.awt.FocusTraversalPolicy](http://www.java2s.com/Tutorial/Java/0260__Swing-Event/UseFocusTraversalPolicy.htm).

The default implementation has a very basic behavior: [DefaultFocusTraversalPolicy](https://github.com/sialcasa/focusFX/blob/master/src/main/java/de/saxsys/focusfx/DefaultFocusTraversalPolicy.java). You can use it by calling ```FXFocusManager.applyDefaultPolicy```.

[![Build Status](https://travis-ci.org/sialcasa/focusFX.svg?branch=master)](https://travis-ci.org/sialcasa/jfx-testrunner)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://opensource.org/licenses/Apache-2.0)

###Maven dependency (Sonatype Snapshot Repo)###
```
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>focusfx</artifactId>
		<version>0.2-SNAPSHOT</version>
</dependency>
```

###Usage of the DefaultFocusTraversalPolicy###

```Java

public class FocusExample extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
				tf1 = new TextField("1");
		TextField tf2 = new TextField("2");
		TextField tf3 = new TextField("3");
		TextField tf4 = new TextField("4");
		TextField tf5 = new TextField("5");
		VBox vbox1 = new VBox(tf1, tf2, tf3, tf4, tf5);
		FocusFX.applyDefaultPolicy(vbox1, FXCollections.observableArrayList(tf5, tf3, tf2, tf4));
		
		TextField tf6 = new TextField("6");
		TextField tf7 = new TextField("7");
		TextField tf8 = new TextField("8");
		TextField tf9 = new TextField("9");
		TextField tf10 = new TextField("10");
		VBox vbox2 = new VBox(tf6, tf7, tf8, tf9, tf10);
		
		ObservableList<Node> nodesToFocus = FXCollections.observableArrayList(tf6, tf7, tf8, tf9);
		FocusFX.applyDefaultPolicy(vbox2, nodesToFocus);
		
		// Is not in traversal chaing, because it was not added to the nodesToFocus list
		TextField tf10a = new TextField("10");
		vbox2.getChildren().add(tf10a);
		
		TextField tf11 = new TextField("11");
		TextField tf12 = new TextField("12");
		TextField tf13 = new TextField("13");
		TextField tf14 = new TextField("14");
		TextField tf15 = new TextField("15");
		VBox vbox3 = new VBox(tf11, tf12, tf13, tf14, tf15);
		
		FocusFX.applyDefaultPolicy(vbox3, vbox3.getChildren());
		
		// Is also in traversal cycle, because setAllChildrenFocusTraversalEnabled was called
		TextField tf16 = new TextField("16");
		vbox3.getChildren().add(tf16);
		
		FocusFX.setParentTraversalChain(vbox1, vbox3, vbox2);		
		primaryStage.setScene(new Scene(new VBox(vbox1, vbox2, vbox3)));
		primaryStage.show();
	}	
}
```
### Implement own FocusTraversalPolicy###
```
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FocusTraversalPolicy focusTraversalPolicy1 = new FocusTraversalPolicy() {
			
			@Override
			public Node getNodeBefore(Parent aContainer, Node aNode) {
				Node focusOwner = vbox1.getScene().getFocusOwner();
				ObservableList<Node> children = vbox1.getChildren();
				int indexOfFocusOwner = children.indexOf(focusOwner);
				return children
						.get(indexOfFocusOwner == 0 ? children.size() - 1
								: indexOfFocusOwner - 1);
			}
			
			@Override
			public Node getNodeAfter(Parent aContainer, Node aNode) {
				Node focusOwner = vbox1.getScene().getFocusOwner();
				ObservableList<Node> children = vbox1.getChildren();
				int indexOfFocusOwner = children.indexOf(focusOwner);
				
				//Skip second TextField				
				if (indexOfFocusOwner == 0) {
					return vbox1.getChildren().get(2);
				}
				
				if (indexOfFocusOwner == children.size() - 1) {
					return null; //Exit this Focus Cycle and move to unmanaged Node with Focus
				}
				
				return vbox1.getChildren().get(indexOfFocusOwner + 1);
			}
			
			@Override
			public Node getLastNode(Parent aContainer) {
				return vbox1.getChildren().get(vbox1.getChildren().size() - 1);
			}
			
			@Override
			public Node getFirstNode(Parent aContainer) {
				return vbox1.getChildren().get(0);
			}
			
			@Override
			public Node getDefaultNode(Parent aContainer) {
				return vbox1.getChildren().get(0);
			}
		};
		
		FocusTraversalPolicy focusTraversalPolicy2 = new FocusTraversalPolicy() {
			
			
			@Override
			public Node getNodeBefore(Parent aContainer, Node aNode) {
				Node focusOwner = vbox2.getScene().getFocusOwner();
				ObservableList<Node> children = vbox2.getChildren();
				int indexOfFocusOwner = children.indexOf(focusOwner);
				
				if (indexOfFocusOwner == 0) {
					return null; // Exit Focus Cycle
				}
				
				return children
						.get(indexOfFocusOwner - 1);
			}
			
			@Override
			public Node getNodeAfter(Parent aContainer, Node aNode) {
				Node focusOwner = vbox2.getScene().getFocusOwner();
				ObservableList<Node> children = vbox2.getChildren();
				int indexOfFocusOwner = children.indexOf(focusOwner);
				
				if (indexOfFocusOwner == children.size() - 1) {
					//Exit this Focus Cycle and move to unmanaged Node with Focus
					return null;
				}
				
				return vbox2.getChildren().get(indexOfFocusOwner + 1);
			}
			
			@Override
			public Node getLastNode(Parent aContainer) {
				return vbox2.getChildren().get(0);
			}
			
			@Override
			public Node getFirstNode(Parent aContainer) {
				return vbox2.getChildren().get(vbox1.getChildren().size() - 1);
			}
			
			@Override
			public Node getDefaultNode(Parent aContainer) {
				return vbox2.getChildren().get(0);
			}
		};
		
		
		TextField tf1 = new TextField("1");
		TextField tf2 = new TextField("2");
		TextField tf3 = new TextField("3");
		TextField tf4 = new TextField("4");
		TextField tf5 = new TextField("5");
		VBox vbox1 = new VBox(tf1, tf2, tf3, tf4, tf5);
		
		FocusFX.applyFocusTraversalPolicy(focusTraversalPolicy1, vbox1, vbox1.getChildren(),
				FXCollections.observableArrayList(EventTuple.TAB_FORWARD),
				FXCollections.observableArrayList(EventTuple.SHIFT_TAB_BACKWARDS));
				
				
		TextField tf6 = new TextField("6");
		TextField tf7 = new TextField("7");
		TextField tf8 = new TextField("8");
		TextField tf9 = new TextField("9");
		TextField tf10 = new TextField("10");
		VBox vbox2 = new VBox(tf6, tf7, tf8, tf9, tf10);
		
		FocusFX.applyFocusTraversalPolicy(focusTraversalPolicy2, vbox2, vbox2.getChildren(),
				FXCollections.observableArrayList(EventTuple.TAB_FORWARD),
				FXCollections.observableArrayList(EventTuple.SHIFT_TAB_BACKWARDS));
				
		TextField unmanaged1 = new TextField("unmanaged1");
		TextField unmanaged2 = new TextField("unmanaged2");
		
		primaryStage.setScene(new Scene(new VBox(vbox1, unmanaged1, unmanaged2, vbox2)));
		primaryStage.show();
	}
```

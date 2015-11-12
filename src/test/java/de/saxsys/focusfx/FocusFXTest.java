package de.saxsys.focusfx;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FocusFXTest extends ApplicationTest {
	
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	private TextField tf5;
	private TextField tf6;
	private TextField tf7;
	private TextField tf8;
	private TextField tf9;
	private TextField tf10;
	private VBox vbox1;
	private VBox vbox2;
	
	private TextField unmanaged1;
	private TextField unmanaged2;
	
	@Test
	public void navigateForward() throws Exception {
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf1.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf2.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf3.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf4.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf5.focusedProperty());
		
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, unmanaged1.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, unmanaged2.focusedProperty());
		
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf6.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf7.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf8.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf9.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf10.focusedProperty());
		type(KeyCode.TAB);
	}
	
	@Test
	public void navigateBackward() throws Exception {
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf1.focusedProperty());
		
		clickOn(tf10);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf10.focusedProperty());
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf9.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf8.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf7.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf6.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, unmanaged2.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, unmanaged1.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf5.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf4.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf3.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf2.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		press(KeyCode.SHIFT, KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf1.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
		
		
		
	}
	
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
				
				if (indexOfFocusOwner == children.size() - 1) {
					return null; // Exit Focus Cycle
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
		
		
		tf1 = new TextField("1");
		tf2 = new TextField("2");
		tf3 = new TextField("3");
		tf4 = new TextField("4");
		tf5 = new TextField("5");
		vbox1 = new VBox(tf1, tf2, tf3, tf4, tf5);
		
		FocusFX.setFocusOrderToChildrenRank(vbox1);
		FocusFX.applyFocusTraversalPolicy(focusTraversalPolicy1, vbox1,
				FXCollections.observableArrayList(EventTuple.TAB_FORWARD),
				FXCollections.observableArrayList(EventTuple.SHIFT_TAB_BACKWARDS));
				
				
		tf6 = new TextField("6");
		tf7 = new TextField("7");
		tf8 = new TextField("8");
		tf9 = new TextField("9");
		tf10 = new TextField("10");
		vbox2 = new VBox(tf6, tf7, tf8, tf9, tf10);
		
		FocusFX.setFocusOrderToChildrenRank(vbox2);
		FocusFX.applyFocusTraversalPolicy(focusTraversalPolicy2, vbox2,
				FXCollections.observableArrayList(EventTuple.TAB_FORWARD),
				FXCollections.observableArrayList(EventTuple.SHIFT_TAB_BACKWARDS));
				
		unmanaged1 = new TextField("unmanaged1");
		unmanaged2 = new TextField("unmanaged2");
		
		primaryStage.setScene(new Scene(new VBox(vbox1, unmanaged1, unmanaged2, vbox2)));
		primaryStage.show();
	}
	
}

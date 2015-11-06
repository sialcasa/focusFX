/*******************************************************************************
 * Copyright 2013 Alexander Casall
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.focusfx;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FocusTraversalPolicyTest extends ApplicationTest {
	
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
	private TextField tf11;
	private TextField tf12;
	private TextField tf13;
	private TextField tf14;
	private TextField tf15;
	private VBox vbox1;
	private VBox vbox2;
	private VBox vbox3;
	
	@Test
	public void navigateForward() throws Exception {
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf5.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf3.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf2.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf4.focusedProperty());
		
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf11.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf12.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf13.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf14.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf15.focusedProperty());
		
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf6.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf7.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf8.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf9.focusedProperty());
		type(KeyCode.TAB);
		
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf6.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf7.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf8.focusedProperty());
		type(KeyCode.TAB);
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf9.focusedProperty());
		type(KeyCode.TAB);
	}
	
	@Test
	public void navigateBackward() throws Exception {
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf5.focusedProperty());
		
		clickOn(tf9);
		
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf9.focusedProperty());
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
		WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, tf15.focusedProperty());
		release(KeyCode.SHIFT, KeyCode.TAB);
	}
	
	@Test(expected = Exception.class)
	public void initChainWithUnmanagedParent() throws Exception {
		FXFocusManager.setParentsToTraverse(vbox1, vbox3, vbox2, new VBox());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		tf1 = new TextField("1");
		tf2 = new TextField("2");
		tf3 = new TextField("3");
		tf4 = new TextField("4");
		tf5 = new TextField("5");
		vbox1 = new VBox(tf1, tf2, tf3, tf4, tf5);
		FXFocusManager.setNodesToFocus(vbox1, FXCollections.observableArrayList(tf5, tf3, tf2, tf4));
		FXFocusManager.applyDefaultPolicy(vbox1);
		
		tf6 = new TextField("6");
		tf7 = new TextField("7");
		tf8 = new TextField("8");
		tf9 = new TextField("9");
		tf10 = new TextField("10");
		vbox2 = new VBox(tf6, tf7, tf8, tf9, tf10);
		FXFocusManager.setNodesToFocus(vbox2, FXCollections.observableArrayList(tf6, tf7, tf8, tf9));
		FXFocusManager.applyDefaultPolicy(vbox2);
		
		tf11 = new TextField("11");
		tf12 = new TextField("12");
		tf13 = new TextField("13");
		tf14 = new TextField("14");
		tf15 = new TextField("15");
		vbox3 = new VBox(tf11, tf12, tf13, tf14, tf15);
		FXFocusManager.setNodesToFocus(vbox3, FXCollections.observableArrayList(tf11, tf12, tf13, tf14, tf15));
		FXFocusManager.applyDefaultPolicy(vbox3);
		
		FXFocusManager.setParentsToTraverse(vbox1, vbox3, vbox2);
		
		primaryStage.setScene(new Scene(new VBox(vbox1, vbox2, vbox3)));
		primaryStage.show();
	}
	
	
}

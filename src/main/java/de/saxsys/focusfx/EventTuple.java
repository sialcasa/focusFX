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

import java.util.function.Predicate;

import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EventTuple<EventTypeInt extends InputEvent> {
	
	public static EventTuple<KeyEvent> TAB_FORWARD = createDefaultForward();
	public static EventTuple<KeyEvent> SHIFT_TAB_BACKWARDS = createDefaultBackward();
	
	private final EventType<EventTypeInt> event;
	private final Predicate<EventTypeInt> check;
	
	public EventTuple(EventType<EventTypeInt> keyPressed, Predicate<EventTypeInt> check) {
		this.event = keyPressed;
		this.check = check;
	}
	
	public EventType<EventTypeInt> getEvent() {
		return event;
	}
	
	public Predicate<EventTypeInt> getCheck() {
		return check;
	}
	
	private static EventTuple<KeyEvent> createDefaultForward() {
		Predicate<KeyEvent> checkForward = event -> event.getCode() == KeyCode.TAB && !event.isShiftDown();
		EventTuple<KeyEvent> forward = new EventTuple<>(KeyEvent.KEY_PRESSED, checkForward);
		return forward;
	}
	
	private static EventTuple<KeyEvent> createDefaultBackward() {
		Predicate<KeyEvent> checkBack = event -> event.getCode() == KeyCode.TAB && event.isShiftDown();
		EventTuple<KeyEvent> backward = new EventTuple<>(KeyEvent.KEY_PRESSED, checkBack);
		return backward;
	}
}

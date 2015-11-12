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

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

public class FocusFX {
	
	static final String IMPL_TRAVERSAL_FOR_PARENT = "IMPL_TRAVERSAL_FOR_PARENT";
	static final String IMPL_FOCUS_NODES_LIST_PROPERTY = "FOCUS_NODES";
	static final String IMPL_CONTAINER_BEFORE = "FOCUS_NODE_BEFORE";
	static final String IMPL_CONTAINER_AFTER = "FOCUS_FOLLOWING_NODE";
	
	public static void applyDefaultPolicy(Parent parent) {
		checkConfiguration(parent);
		
		FocusTraversalPolicy focusTraversalPolicy = new DefaultFocusTraversalPolicy(
				getFocusNodes(parent));
				
		List<EventTuple> forwardEvents = new ArrayList<>();
		List<EventTuple> backwardEvents = new ArrayList<>();
		
		forwardEvents.add(EventTuple.TAB_FORWARD);
		backwardEvents.add(EventTuple.SHIFT_TAB_BACKWARDS);
		
		applyFocusTraversalPolicy(focusTraversalPolicy, parent, forwardEvents, backwardEvents);
		Platform.runLater(() -> getTraversalPolicyForParent(parent).getFirstNode(parent).requestFocus());
	}
	
	
	
	public static void applyFocusTraversalPolicy(FocusTraversalPolicy traversal, Parent parent,
			List<EventTuple> forwardEvents, List<EventTuple> backEvents) {
		checkConfiguration(parent);
		
		parent.getProperties().put(IMPL_TRAVERSAL_FOR_PARENT, traversal);
		ObservableList<Node> focusNodes = getFocusNodes(parent);
		
		focusNodes.addListener((ListChangeListener<Node>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().forEach((n) -> {
						forwardEvents.forEach(tuple -> applyForwardFocusHandling(traversal, parent, n, tuple));
						backEvents.forEach(tuple -> applyBackwardFocusHandling(traversal, parent, n, tuple));
					});
				}
			}
		});
		focusNodes.forEach((node) -> {
			forwardEvents.forEach(tuple -> applyForwardFocusHandling(traversal, parent, node, tuple));
			backEvents.forEach(tuple -> applyBackwardFocusHandling(traversal, parent, node, tuple));
		});
	}
	
	public static void setFocusOrder(Parent node, ObservableList<Node> nodes) {
		if (node == null) {
			throw new IllegalArgumentException("Node is null. This is forbidden.");
		}
		
		if (nodes.size() < 1) {
			throw new IllegalArgumentException("Put more then 1 node for a focuschain");
		}
		
		node.getProperties().put(IMPL_FOCUS_NODES_LIST_PROPERTY, nodes);
	}
	
	public static void setFocusOrderToChildrenRank(Parent parent) {
		setFocusOrder(parent, parent.getChildrenUnmodifiable());
	}
	
	public static void setParentTraversalChain(Parent... parents) {
		if (parents.length < 2) {
			throw new IllegalArgumentException("Put more then 1 node for a focuschain");
		}
		for (int i = 0; i < parents.length; i++) {
			if (getTraversalPolicyForParent(parents[i]) == null) {
				throw new IllegalStateException(
						"One of the Nodes in the chain has no Focus enabled. Use the applyFocus... functions.");
			}
			if (i + 1 < parents.length) {
				parents[i].getProperties().put(IMPL_CONTAINER_AFTER, parents[i + 1]);
			}
			if (i > 0) {
				parents[i].getProperties().put(IMPL_CONTAINER_BEFORE, parents[i - 1]);
			}
		}
		
		Platform.runLater(() -> getTraversalPolicyForParent(parents[0]).getFirstNode(parents[0]).requestFocus());
		
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	private static void applyForwardFocusHandling(FocusTraversalPolicy traversal, Parent parent, Node node,
			EventTuple tuple) {
		node.addEventFilter(tuple.getEvent(), event -> {
			if (tuple.getCheck().test(event)) {
				
				if (checkAndMoveToNextContainer(traversal, parent, node)) {
					event.consume();
					return;
				}
				
				Node nodeAfter = traversal.getNodeAfter(parent, node);
				while (nodeAfter != null && !nodeAfter.isFocusTraversable()) {
					nodeAfter = traversal.getNodeAfter(parent, nodeAfter);
				}
				
				if (nodeAfter == null) {
					return;
				}
				
				nodeAfter.requestFocus();
				event.consume();
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	private static void applyBackwardFocusHandling(FocusTraversalPolicy traversal, Parent parent, Node node,
			EventTuple tuple) {
		node.addEventFilter(tuple.getEvent(), event -> {
			if (tuple.getCheck().test(event)) {
				if (checkAndMoveToBeforeContainer(traversal, parent, node)) {
					event.consume();
					return;
				}
				Node nodeBefore = traversal.getNodeBefore(parent, node);
				while (nodeBefore != null && !nodeBefore.isFocusTraversable()) {
					nodeBefore = traversal.getNodeBefore(parent, nodeBefore);
				}
				
				
				if (nodeBefore == null) {
					return;
				}
				
				
				nodeBefore.requestFocus();
				event.consume();
			}
		});
		
	}
	
	private static boolean checkAndMoveToNextContainer(FocusTraversalPolicy traversal, Parent parent, Node node) {
		Parent nextContainer = getContainerAfter(parent);
		if (node == traversal.getLastNode(parent) && nextContainer != null) {
			getTraversalPolicyForParent(nextContainer)
					.getFirstNode(nextContainer)
					.requestFocus();
			return true;
		}
		return false;
	}
	
	
	private static boolean checkAndMoveToBeforeContainer(FocusTraversalPolicy traversal, Parent parent, Node node) {
		Parent beforeContainer = getContainerBefore(parent);
		if (node == traversal.getFirstNode(parent) && beforeContainer != null) {
			FocusTraversalPolicy focusTraversalPolicy = getTraversalPolicyForParent(beforeContainer);
			focusTraversalPolicy
					.getLastNode(beforeContainer).requestFocus();
			return true;
		}
		return false;
	}
	
	private static FocusTraversalPolicy getTraversalPolicyForParent(Parent container) {
		return (FocusTraversalPolicy) container.getProperties().get(IMPL_TRAVERSAL_FOR_PARENT);
	}
	
	private static void checkConfiguration(Parent parent) {
		ObservableList<Node> nodes = getFocusNodes(parent);
		if (nodes == null) {
			throw new IllegalArgumentException(
					"Use FXFocusManager.focusNodes(...) before you use the Focustraversal");
		}
		
		if (!parent.getChildrenUnmodifiable().containsAll(nodes)) {
			throw new IllegalStateException(
					"Not all nodes you've added with FXFocusManager.focusNodes(...), are children of your target container.");
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	private static ObservableList<Node> getFocusNodes(Parent parent) {
		return (ObservableList<Node>) parent.getProperties()
				.get(FocusFX.IMPL_FOCUS_NODES_LIST_PROPERTY);
	}
	
	private static Parent getContainerBefore(Parent parent) {
		return (Parent) parent.getProperties().get(IMPL_CONTAINER_BEFORE);
	}
	
	private static Parent getContainerAfter(Parent parent) {
		return (Parent) parent.getProperties().get(IMPL_CONTAINER_AFTER);
	}
	
	
	
	
	
}

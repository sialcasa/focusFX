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

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

public class DefaultFocusTraversalPolicy extends FocusTraversalPolicy {
	
	private final ObservableList<Node> nodes;
	
	public DefaultFocusTraversalPolicy(ObservableList<Node> observableList) {
		this.nodes = observableList;
	}
	
	@Override
	public Node getNodeBefore(Parent aContainer, Node aNode) {
		int indexOf = nodes.indexOf(aNode);
		indexOf--;
		if (indexOf < 0)
			indexOf = nodes.size() - 1;
		return nodes.get(indexOf);
	}
	
	@Override
	public Node getNodeAfter(Parent aContainer, Node aNode) {
		int indexOf = nodes.indexOf(aNode);
		return nodes.get((indexOf + 1) % nodes.size());
	}
	
	@Override
	public Node getLastNode(Parent aContainer) {
		return nodes.get(nodes.size() - 1);
	}
	
	@Override
	public Node getFirstNode(Parent aContainer) {
		return nodes.get(0);
	}
	
	@Override
	public Node getDefaultNode(Parent aContainer) {
		return nodes.get(0);
	}
}
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

import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class FocusTraversalPolicy {
	
	public abstract Node getNodeAfter(Parent aContainer,
			Node aNode);
			
	public abstract Node getNodeBefore(Parent aContainer,
			Node aNode);
			
	public abstract Node getFirstNode(Parent aContainer);
	
	
	public abstract Node getLastNode(Parent aContainer);
	
	
	public abstract Node getDefaultNode(Parent aContainer);
	
	
	public Node getInitialNode(Parent parent) {
		if (parent == null) {
			throw new IllegalArgumentException("Parent cannot be equal to null.");
		}
		Node def = getDefaultNode(parent);
		if (def == null) {
			def = parent;
		}
		return def;
	}
	
}

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

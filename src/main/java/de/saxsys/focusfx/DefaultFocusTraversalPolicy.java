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
import java.util.LinkedList;

import de.htwmaps.server.algorithm.Node;


public class a {
public static void main(String[] args) {
	LinkedList<Integer> nodesContainer = new LinkedList<Integer>();
	nodesContainer.add(1);
	nodesContainer.add(2);
	nodesContainer.add(3);
	Integer first = nodesContainer.getFirst();
	int i = 0;
	while (!first.equals(nodesContainer.getLast())) {
		nodesContainer.add(i, nodesContainer.removeLast()); 
		i++;
	}
}
}

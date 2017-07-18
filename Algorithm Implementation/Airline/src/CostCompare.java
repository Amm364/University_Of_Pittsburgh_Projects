import java.util.Comparator;

public class CostCompare implements Comparator<Edge> {

	@Override
	public int compare(Edge e1, Edge e2) {
		if (e1.getCost()>e2.getCost()) return 1;
		else if (e1.getCost()<e2.getCost()) return -1;
		else return 0;
	}

}

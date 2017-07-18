import java.util.Comparator;

public class WeightCompare implements Comparator<Edge>{

	@Override
	public int compare(Edge e1, Edge e2) {
		if (e1.getWeight()>e2.getWeight()) return 1;
		else if (e1.getWeight()<e2.getWeight()) return -1;
		else return 0;
	}

}

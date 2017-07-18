import java.util.ArrayList;

public class PQ{
	ArrayList<Edge> pq;
	
	PQ(){
		pq = new ArrayList<Edge>();
	}
	public void addToPQ(Edge e){
		pq.add(e);
	}
	
}

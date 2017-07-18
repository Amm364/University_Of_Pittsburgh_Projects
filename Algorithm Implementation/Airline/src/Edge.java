

public class Edge {
	private int startCity=0;
	private int endCity=0;
	private double cost=0;
	private int distance=0;
	public Edge nextEdge=null;
	private int currentWeight=0;
	
	Edge(int sC,int eC,double c,int d){
		startCity=sC;
		endCity=eC;
		cost=c;
		distance=d;
	}
	
	public int getStartCity(){
		return startCity; 
	}
	public int getEndCity(){
		return endCity; 
	}
	public double getCost(){
		return cost; 
	}
	public int getDistance(){
		return distance; 
	}
	public String toString(){
		return startCity + " " + endCity + " " + distance + " " + cost;
	}
	public void setWeight(int i){
		currentWeight=i;
	}
	public int getWeight(){
		return currentWeight;
	}
}

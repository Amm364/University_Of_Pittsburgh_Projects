import java.util.ArrayList;


class Node{
	boolean visited=false;
	private String city;
	private int cityNum=0;
	private int treeNum=0;
	private Edge theEdge=null;
	Node connectedCity=null;
	Node previousCity=null;
	ArrayList<Edge> tripPath=new ArrayList<Edge>();
	private int weight=0;
	
 
	
	Node(String place, int cNum,int tNum){
		city=place;	
		cityNum=cNum;
		treeNum=tNum;
	}
	Node(Edge e){
		theEdge=e;
	}
	public String getCity(){
		return city;
	}
	public boolean getVisit(){	
		return visited;
	}
	public void visitCity(){
		visited=true;
	}
	public int getNum(){
		return cityNum;
	}
	public Edge getTheEdge(){
		return theEdge;
	}
	public void setWeight(int i){
		weight=i;
	}
	public int getWeight(){
		return weight;
	}
	
	
}
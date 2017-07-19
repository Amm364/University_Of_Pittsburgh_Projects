import java.io.*;
import java.util.*;


public class hold {
	static ArrayList<Edge> allEdges=new ArrayList<Edge>();
	public static void main(String[] args) throws IOException {
		ArrayList<Node> cityList= new ArrayList<Node>();
		ArrayList<Edge> mst= new ArrayList<Edge>();
		int numCities=0;
		BufferedReader read_file = null;
		System.out.print("Please enter an input file: ");
		Scanner in = new Scanner(System.in);
		try {
			read_file = new BufferedReader(new FileReader(in.next()));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		System.out.println("---------------------------------------\n");
		numCities=Integer.parseInt(read_file.readLine());
		cityList.add(new Node("Place holder",0,0));		//Node placed so that city 1 can be referenced by .get(1) instead of .get(0)
		for (int i=1;i<numCities+1;i++){
			cityList.add(new Node(read_file.readLine(),i,i)); //set values to 0 initially
		}
		while(read_file.ready()){
			String line = read_file.readLine();
			String[] hold=line.split(" ");
			int startCity=Integer.parseInt(hold[0]);
			int endCity=Integer.parseInt(hold[1]);
			int distance=Integer.parseInt(hold[2]);
			double cost=Double.parseDouble(hold[3]);
			cityList=AddToAdjacencyList(startCity,endCity,distance,cost,cityList);
		}
		mst=CreateMST(cityList,mst,numCities);
		System.out.println("Minimum Spanning Tree\n---------------------------------------\n");
		System.out.println("The edges in the MST based on distance follow: \n");
		for (Edge e : mst){
			System.out.println(cityList.get(e.getStartCity()).getCity()+", "+cityList.get(e.getEndCity()).getCity()+" : "+e.getDistance());
		}
	}

	private static ArrayList<Node> AddToAdjacencyList(int startCity, int endCity, int distance, double cost, ArrayList<Node> cityList) {
		Node currentNode=cityList.get(startCity);
		while (currentNode.connectedCity!=null){
			currentNode=currentNode.connectedCity;
		}
		Edge newEdge=new Edge(startCity,endCity,cost,distance);
		allEdges.add(newEdge);
		currentNode.connectedCity=new Node(newEdge);
		currentNode=cityList.get(endCity);
		while(currentNode.connectedCity!=null){
			currentNode=currentNode.connectedCity;
		}
		currentNode.connectedCity=new Node(new Edge(endCity,startCity,cost,distance));
		return cityList;
	}

	private static ArrayList<Edge> CreateMST(ArrayList<Node> cityList, ArrayList<Edge> mst, int numCities) {
		int[] num = new int[numCities];
		for (int i=0;i<num.length;i++){
			num[i]=i+1;
		}
		boolean done=false;
		Edge bestEdge = null;
		int count=0;
		while(!done){
			for (int i=0;i<allEdges.size();i++){
				if(bestEdge==null || allEdges.get(i).getDistance()<bestEdge.getDistance()){
					if (num[(allEdges.get(i).getStartCity())-1] != num[(allEdges.get(i).getEndCity())-1])
					bestEdge=allEdges.get(i);
				}
			}
			if(bestEdge==null) done=true;
			else{
				mst.add(bestEdge);
				num=Union(bestEdge,num);
				count++;
				bestEdge=null;
			}
		}
		return mst;
	}

	private static int[] Union(Edge bestEdge, int[] num) {
		int treeNum=num[bestEdge.getStartCity()-1];
		int oldTreeNum=num[bestEdge.getEndCity()-1];
		for (int i=0;i<num.length;i++){
			if (num[i]==oldTreeNum){
				num[i]=treeNum;
			}
		}
		return num;
	}
}
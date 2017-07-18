import java.io.*;
import java.util.*;


public class Airline {
	static ArrayList<Edge> allEdges=new ArrayList<Edge>();
	public static void main(String[] args) throws IOException {
		ArrayList<Node> cityList= new ArrayList<Node>();
		ArrayList<Edge> mst= new ArrayList<Edge>();
		boolean changes=false;
		int numCities=0;
		BufferedReader read_file = null;
		System.out.print("Please enter an input file: ");
		Scanner in = new Scanner(System.in);
		File file = new File(in.nextLine());
		try {
			read_file = new BufferedReader(new FileReader(file));
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
		in = new Scanner(System.in);
		
		while(true){
			System.out.println("\n--------------------------------------- ");
			cityList=Reset(cityList);
			System.out.println("What would you like to do?\nType '1' to find a path.\nType '2' to create the MST.\nType '3' to find paths of specific cost.\nType '4' to remove a route.\n"
					+ "Type '5' to add a route.\nType '6' to print the graph.\nType '7' to exit.");
			System.out.print("Choice: ");
			int choice=in.nextInt();
			System.out.println();
			if (choice==1){
				FindAPath(cityList,numCities);
			}
			else if (choice==2){
				mst=CreateMST(cityList,mst,numCities);
				PrintMST(mst,cityList);
			}
			else if (choice==3){
				in = new Scanner(System.in);
				System.out.print("Whats your budget? ");
				double max=in.nextDouble();
				for (int i=1;i<numCities+1;i++){
					double currentCost=0;
					Node currentCity=cityList.get(i).connectedCity;
					int cCity=i;
					ArrayList<Edge> path = new ArrayList<Edge>();
					cityList.get(cCity).visitCity();
					while(currentCity!=null){
						FindRoutes(max,cityList,currentCost,currentCity,path);
						if (path.size()==1){
							path.remove(0);
						}
						currentCity=currentCity.connectedCity;
					}
					cityList.get(cCity).visited=false;
				}
			}
			else if (choice==4){
				System.out.print("What is the start city of the removed route? ");
				in=new Scanner(System.in);
				String startingCity=in.nextLine();
				startingCity=startingCity.toLowerCase();
				System.out.print("End city? ");
				in=new Scanner(System.in);
				String endingCity=in.nextLine();
				endingCity=endingCity.toLowerCase();
				cityList=RemoveCity(cityList,startingCity,endingCity,numCities);
				changes = true;
			}
			else if (choice==5){
				in=new Scanner(System.in);
				System.out.print("Start city: ");
				String startCity=in.nextLine();
				startCity.toLowerCase();
				System.out.print("End city: ");
				String endCity=in.nextLine();
				endCity.toLowerCase();
				System.out.print("Distance: ");
				int distance=in.nextInt();
				System.out.print("Cost: ");
				double cost=in.nextDouble();
				int start=0;
				int end=0;
				for (int i=0;i<numCities+1;i++){
					if (cityList.get(i).getCity().toLowerCase().equals(startCity.toLowerCase())){
						start=i;
					}
					else if (cityList.get(i).getCity().toLowerCase().equals(endCity.toLowerCase())){
						end=i;
					}
				}
				AddToAdjacencyList(start,end,distance,cost,cityList);
				changes = true;
			}
			else if (choice==6){
				for(Edge e:allEdges){
					System.out.println(cityList.get(e.getStartCity()).getCity() + " to " + cityList.get(e.getEndCity()).getCity() + "| Distance: " + e.getDistance() + " Cost: " + e.getCost() + "0");
				}
			}
			else if (choice==7){
				if (changes){
					MakeUpdatedFile(cityList,numCities,file);
				}
				System.exit(0);
			}
			else System.out.println("Not a choice. Try again.");
		}
	}

	private static void FindRoutes(double max, ArrayList<Node> cityList, double currentCost, Node currentCity, ArrayList<Edge> path) {
		currentCost+=currentCity.getTheEdge().getCost();
		int cCity = currentCity.getTheEdge().getEndCity();
		if (currentCost>max || cityList.get(currentCity.getTheEdge().getEndCity()).getVisit()){
			currentCost-=currentCity.getTheEdge().getCost();
			return;
		}
		else if (!cityList.get(currentCity.getTheEdge().getEndCity()).getVisit()){
			cityList.get(cCity).visitCity();
			path.add(currentCity.getTheEdge());
			System.out.print("Cost: " + currentCost + "0 | Path: ");
			for (Edge e:path){
				System.out.print(cityList.get(e.getStartCity()).getCity() + " " + e.getCost() + "0 ");
			}
			System.out.println(cityList.get(currentCity.getTheEdge().getEndCity()).getCity());
		}
		
		currentCity=cityList.get(currentCity.getTheEdge().getEndCity()).connectedCity;
		while (currentCity!=null){
			FindRoutes(max,cityList,currentCost,currentCity,path);
			currentCity=currentCity.connectedCity;
		}
		path.remove(path.size()-1);
		cityList.get(cCity).visited=false;
		return;
	}

	private static void MakeUpdatedFile(ArrayList<Node> cityList, int numCities, File file) throws IOException {
		PrintWriter write = new PrintWriter(file);
		write.print("");
		write.println(numCities);
		for (Node n:cityList){
			if (n.getCity().equals("Place holder")) continue;
			write.println(n.getCity());
		}
		for (Node n:cityList){
			Node currentCity = n.connectedCity;
			while (currentCity!=null){
				write.println(currentCity.getTheEdge().toString());
				RemoveCity(cityList,cityList.get(currentCity.getTheEdge().getStartCity()).getCity(),cityList.get(currentCity.getTheEdge().getEndCity()).getCity(),numCities);
				currentCity=currentCity.connectedCity;
			}
		}
		write.close();
		
	}

	private static ArrayList<Node> RemoveCity(ArrayList<Node> cityList,
			String startingCity, String endingCity, int numCities) {
		int start=0;
		int end=0;
		for (int i=0;i<numCities+1;i++){
			if (cityList.get(i).getCity().toLowerCase().equals(startingCity.toLowerCase())){
				start=i;
			}
			else if (cityList.get(i).getCity().toLowerCase().equals(endingCity.toLowerCase())){
				end=i;
			}
		}
		Node currentCity=cityList.get(start).connectedCity;
		Node prevCity=cityList.get(start);
		while(currentCity!=null){
			if (currentCity.getTheEdge().getEndCity()==end){
				prevCity.connectedCity=currentCity.connectedCity;
				break;
			}
			currentCity=currentCity.connectedCity;
			prevCity=prevCity.connectedCity;
		}
		currentCity=cityList.get(end).connectedCity;
		prevCity=cityList.get(end);
		while(currentCity!=null){
			if (currentCity.getTheEdge().getEndCity()==start){
				prevCity.connectedCity=currentCity.connectedCity;
				break;
			}
			currentCity=currentCity.connectedCity;
			prevCity=prevCity.connectedCity;
		}
		currentCity=cityList.get(start).connectedCity;
		return cityList;
	}

	private static ArrayList<Node> Reset(ArrayList<Node> cityList) {
		for (Node n:cityList){
			Node currentNode=n;
			currentNode.setWeight(0);
			currentNode.visited=false;
			currentNode=currentNode.connectedCity;
			
			while(currentNode!=null){
				currentNode.getTheEdge().setWeight(0);
				currentNode=currentNode.connectedCity;
			}
		}
		return cityList;
	}

	private static void PrintMST(ArrayList<Edge> mst, ArrayList<Node> cityList) {
		System.out.println("Minimum Spanning Tree\n---------------------------------------\n");
		System.out.println("The edges in the MST based on distance follow: \n");
		for (Edge e : mst){
			System.out.println(cityList.get(e.getStartCity()).getCity()+", "+cityList.get(e.getEndCity()).getCity()+" : "+e.getDistance());
		}
		
	}

	private static void FindAPath(ArrayList<Node> cityList, int numCities) {
		
		Scanner in=new Scanner(System.in);
		String decision="";
		String startingCity="";
		String endingCity="";
		boolean correct=false;
		while (!correct){
			System.out.print("\nPlease answer the following guidlines:\nDo you want a path based on 'cost', 'distance', or number of 'hops'?: ");
			decision=in.nextLine();
			decision.toLowerCase();
			if (decision.equals("cost") || decision.equalsIgnoreCase("distance") || decision.equals("hops")){
				correct=true;
			}
			else System.out.println("That was not an option.");
		}
		System.out.print("Starting city: ");
		startingCity=in.nextLine();
		System.out.print("Ending city: ");
		endingCity=in.nextLine();
		int startCity=0;
		int endCity=0;
		for (int i=0;i<numCities+1;i++){
			if (cityList.get(i).getCity().toLowerCase().equals(startingCity.toLowerCase())){
				startCity=i;
			}
			else if (cityList.get(i).getCity().toLowerCase().equals(endingCity.toLowerCase())){
				endCity=i;
			}
		}
		if (startCity==0 || endCity==0){
			System.out.println("City not found.");
			System.exit(0);
		}
		ArrayList<Edge> pq = new ArrayList<Edge>();
		ArrayList<Edge> currentPath = new ArrayList<Edge>();
			Node currentCity=cityList.get(startCity).connectedCity;
			cityList.get(startCity).visitCity();
			int bestWeight=0;
			while(!cityList.get(endCity).getVisit()){
				while(currentCity!=null){
					if (cityList.get(currentCity.getTheEdge().getEndCity()).getVisit()!=true){
						if (decision.toLowerCase().equals("distance")){
							int newWeight=bestWeight+currentCity.getTheEdge().getDistance();
							if (newWeight<cityList.get(currentCity.getTheEdge().getEndCity()).getWeight() || cityList.get(currentCity.getTheEdge().getEndCity()).getWeight()==0){
								currentCity.getTheEdge().setWeight(bestWeight+currentCity.getTheEdge().getDistance());
								cityList.get(currentCity.getTheEdge().getEndCity()).setWeight(bestWeight+currentCity.getTheEdge().getDistance());
							}
							else{
								currentCity=currentCity.connectedCity;
								continue;
							}
						}
						else if (decision.toLowerCase().equals("cost")){
							int newWeight=(int)(bestWeight+currentCity.getTheEdge().getCost());
							if (newWeight<cityList.get(currentCity.getTheEdge().getEndCity()).getWeight() || cityList.get(currentCity.getTheEdge().getEndCity()).getWeight()==0){
								currentCity.getTheEdge().setWeight((int)(bestWeight+currentCity.getTheEdge().getCost()));
								cityList.get(currentCity.getTheEdge().getEndCity()).setWeight((int)(bestWeight+currentCity.getTheEdge().getCost()));
							}
							else{
								currentCity=currentCity.connectedCity;
								continue;
							}
						}
						else if (decision.toLowerCase().equals("hops")){
							int newWeight=bestWeight+1;
							if (newWeight<cityList.get(currentCity.getTheEdge().getEndCity()).getWeight() || cityList.get(currentCity.getTheEdge().getEndCity()).getWeight()==0){
								currentCity.getTheEdge().setWeight(bestWeight++);
								cityList.get(currentCity.getTheEdge().getEndCity()).setWeight(bestWeight++);
							}
							else{
								currentCity=currentCity.connectedCity;
								continue;
							}
						}
						pq.add(currentCity.getTheEdge());
						currentPath.add(currentCity.getTheEdge());
						Node destinationCity=cityList.get(currentCity.getTheEdge().getEndCity());
						destinationCity.tripPath=new ArrayList<Edge>();
						for (Edge e:currentPath){
							destinationCity.tripPath.add(e);
						}
						currentPath.remove(currentPath.size()-1);
					}
					currentCity=currentCity.connectedCity;
				}
				Collections.sort(pq,new WeightCompare());
				cityList.get(pq.get(0).getEndCity()).visitCity();
				currentCity=cityList.get(pq.get(0).getEndCity());
				currentPath=new ArrayList<Edge>();
				for(Edge e:currentCity.tripPath){
					currentPath.add(e);
				}
				currentCity=currentCity.connectedCity;
				bestWeight=pq.get(0).getWeight();
				pq.remove(0);
				
			}
			if (decision.toLowerCase().equals("distance")){
				System.out.println("The shortest distance from " + startingCity + " to " + endingCity + " is " + cityList.get(endCity).getWeight());
				for (Edge e:cityList.get(endCity).tripPath){
					System.out.print(cityList.get(e.getStartCity()).getCity() + " " + e.getDistance() + " ");
				}
				System.out.println(cityList.get(endCity).getCity());
			}
			else if (decision.toLowerCase().equals("cost")){
				System.out.println("The shortest cost from " + startingCity + " to " + endingCity + " is " + cityList.get(endCity).getWeight());
				for (Edge e:cityList.get(endCity).tripPath){
					System.out.print(cityList.get(e.getStartCity()).getCity() + " " + e.getCost() + " ");
				}
				System.out.println(cityList.get(endCity).getCity());
			}
			else if (decision.toLowerCase().equals("hops")){
				System.out.println("The shortest amount of hops from " + startingCity + " to " + endingCity + " is " + cityList.get(endCity).getWeight());
				for (Edge e:cityList.get(endCity).tripPath){
					System.out.print(cityList.get(e.getStartCity()).getCity() + " ");
				}
				System.out.println(cityList.get(endCity).getCity());
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class vmsim {
	static int totalAccess=0;
	static int pageFaults=0;
	static int writes=0;
	static File file;
	static int numberOfFrames;
	public static void main(String args[]) throws IOException{
		String[] flags = new String[4];
		flags[0]=args[0];
		flags[1]=args[2];
		flags[2]=args[4];
		flags[3]=args[6];
		file=new File(args[8]);
		numberOfFrames=Integer.parseInt(args[1]);
		String[] algorithms;
		algorithms=args[3].split("[|]");
		int refresh=Integer.parseInt(args[5]);
		int tau=Integer.parseInt(args[7]);
		File file = new File(args[8]);
		
		for (String s:algorithms){
			if (s.equals("opt")){
				OptAlgorithm(file,"Opt");
				totalAccess=0;
				pageFaults=0;
				System.out.println();
			}
			else if (s.equals("clock")){
				ClockAlgorithm(file,"Clock");
				totalAccess=0;
				pageFaults=0;
				System.out.println();
			}
			else if (s.equals("nru")){
				NruAlgorithm(file,refresh,"Nru");
				totalAccess=0;
				pageFaults=0;
				System.out.println();
			}
			else if (s.equalsIgnoreCase("work")){
				WorkingClockAlgorithm(file,refresh,tau,"Work");
				totalAccess=0;
				pageFaults=0;
				System.out.println();
			}
		}
	}

	private static void WorkingClockAlgorithm(File file, int refresh, int tau, String name) throws NumberFormatException, IOException {
		Node currentNode=new Node();
		Node firstNode= currentNode;
		BufferedReader in = new BufferedReader(new FileReader(file));
		PageTable pageTable = new PageTable();
		for (int i=0; i<numberOfFrames;i++){
			if (i==numberOfFrames-1){
				currentNode.nextNode=firstNode;
			}
			else{
				Node newNode = new Node();
				currentNode.nextNode=newNode;
				currentNode=newNode;
			}
		}
		currentNode=firstNode;
		int count=0;
		int frames=0;
		while (in.ready()){
			count++;
			String address=in.readLine();
			String[] contents=address.split(" ");
			contents[0]=contents[0].substring(0,5);
			int indexOfPage=Integer.parseInt(contents[0],16);
			String PageIndex=Integer.toString(indexOfPage);
			String longestLastingPage="";
			int lLPTime=0;
			totalAccess++;
			if (frames<numberOfFrames){
				if (!pageTable.CheckPage(address,totalAccess)){
					pageTable.PageTable.get(PageIndex).clockTime=totalAccess;
					pageFaults++;
					frames++;
					currentNode.pageNumber=PageIndex;
					currentNode=currentNode.nextNode;
				}
				else{
					pageTable.PageTable.get(PageIndex).clockTime=totalAccess;
				}
			}
			else {
				if (!pageTable.CheckPage(address,totalAccess)){
					pageTable.PageTable.get(PageIndex).clockTime=totalAccess;
					pageFaults++;
					int c = 0;
					Node nodeOfEviction = null;
					while (true){
						c++;
						if (c>numberOfFrames){
							pageTable.PageTable.remove(longestLastingPage);
							nodeOfEviction.pageNumber=PageIndex;
							break;
						}
						Value currentPage = pageTable.PageTable.get(currentNode.pageNumber);
						if (currentPage.referenceBit==0){
							if (currentPage.dirtyBit==0){
								pageTable.PageTable.remove(currentNode.pageNumber);
								currentNode.pageNumber=PageIndex;
								break;
							}
							else {
								int elapsedTime=totalAccess-currentPage.clockTime;
								if (elapsedTime>tau){
									currentPage.dirtyBit=0;
									writes++;
								}
							}
						}
						if (lLPTime==0 || currentPage.clockTime<lLPTime){
							longestLastingPage=currentNode.pageNumber;
							lLPTime=currentPage.clockTime;
							nodeOfEviction=currentNode;
						}
						currentNode=currentNode.nextNode;
					}
				}
				else{
					pageTable.PageTable.get(PageIndex).clockTime=totalAccess;
					pageTable.PageTable.get(PageIndex).referenceBit=1;
				}
			}
			if (count==refresh){
				count=0;
				for (String key: pageTable.PageTable.keySet()){
					pageTable.PageTable.get(key).Dereference();
					
				}
			}
		}
		System.out.println("Frames: " + numberOfFrames);
		System.out.println("Algorithm: " + name);
		System.out.println("Total Accesses: " + totalAccess);
		System.out.println("Total Page Faults: " + pageFaults);
		System.out.println("Total Writes: " + writes);
		in.close();
	}

	private static void NruAlgorithm(File file, int refresh, String name) throws NumberFormatException, IOException {
		int count=0;
		int frames=0;
		PageTable pageTable = new PageTable();
		BufferedReader in = new BufferedReader(new FileReader(file));
		while (in.ready()){
			count++;
			String address=in.readLine();
			String[] contents=address.split(" ");
			contents[0]=contents[0].substring(0,5);
			int indexOfPage=Integer.parseInt(contents[0],16);
			String PageIndex=Integer.toString(indexOfPage);
			totalAccess++;
			if (frames<numberOfFrames){
				if (!pageTable.CheckPage(address,totalAccess)){
					pageFaults++;
					frames++;
				}
			}
			else {
				if (!pageTable.CheckPage(address,totalAccess)){
					pageFaults++;
					String currentChoice="";
					int currentRefBit=2;
					int dBit=0;
					for (String key: pageTable.PageTable.keySet()){
						if (pageTable.PageTable.get(key).referenceBit==0){
							if (pageTable.PageTable.get(key).dirtyBit==0){
								currentChoice=key;
								break;
							}
							else{
								currentChoice=key;
								dBit=pageTable.PageTable.get(key).dirtyBit;
								currentRefBit=0;
							}
						}
						else {
							if((pageTable.PageTable.get(key).dirtyBit==0 && pageTable.PageTable.get(key).referenceBit==1 && currentRefBit!=0) || currentChoice.equals("")){
								currentChoice=key;
								dBit=0;
							}
							else if (pageTable.PageTable.get(key).dirtyBit==1 && currentChoice.equals("")){
								currentChoice=key;
								dBit=1;
							}
						}
					}
					if (dBit==1){
						writes++;
						pageTable.PageTable.remove(currentChoice);
					}
					else {
						pageTable.PageTable.remove(currentChoice);
					}
				}
			}
			if (count==refresh){
				count=0;
				//System.out.println("Refreshing");
				for (String key: pageTable.PageTable.keySet()){
					pageTable.PageTable.get(key).Dereference();
				}
			}
		}
		System.out.println("Frames: " + numberOfFrames);
		System.out.println("Algorithm: " + name);
		System.out.println("Total Accesses: " + totalAccess);
		System.out.println("Total Page Faults: " + pageFaults);
		System.out.println("Total Writes: " + writes);
		in.close();
	}

	private static void ClockAlgorithm(File file, String name) throws NumberFormatException, IOException {
		Node currentNode=new Node();
		Node firstNode= currentNode;
		PageTable pageTable = new PageTable();
		BufferedReader in = new BufferedReader(new FileReader(file));
		for (int i=0; i<numberOfFrames;i++){
			if (i==numberOfFrames-1){
				currentNode.nextNode=firstNode;
			}
			else{
				Node newNode = new Node();
				currentNode.nextNode=newNode;
				currentNode=newNode;
			}
		}
		currentNode=firstNode;
		int frames=0;
		while (in.ready()){
			String address=in.readLine();
			String[] contents=address.split(" ");
			contents[0]=contents[0].substring(0,5);
			int indexOfPage=Integer.parseInt(contents[0],16);
			String PageIndex=Integer.toString(indexOfPage);
			totalAccess++;
			if (frames<numberOfFrames){
				if (!pageTable.CheckPage(address,totalAccess)){
					pageFaults++;
					frames++;
					currentNode.pageNumber=PageIndex;
					currentNode=currentNode.nextNode;
				}
			}
			else {
				if (!pageTable.CheckPage(address,totalAccess)){
					pageFaults++;
					while (true){
						if (pageTable.PageTable.get(currentNode.pageNumber).referenceBit==1){
							pageTable.PageTable.get(currentNode.pageNumber).Dereference();
							currentNode=currentNode.nextNode;
						}
						else {
							if (pageTable.PageTable.get(currentNode.pageNumber).dirtyBit==1) writes++;
							pageTable.PageTable.remove(currentNode.pageNumber);
							currentNode.pageNumber=PageIndex;
							currentNode=currentNode.nextNode;
							break;
						}
					}
				}
			}
		}
		System.out.println("Frames: " + numberOfFrames);
		System.out.println("Algorithm: " + name);
		System.out.println("Total Accesses: " + totalAccess);
		System.out.println("Total Page Faults: " + pageFaults);
		System.out.println("Total Writes: " + writes);
		in.close();
	}

	private static void OptAlgorithm(File file2, String name) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file2));
		PageTable optimal = new PageTable();
		PageTable pageTable = new PageTable();
		String[] RAM = new String[numberOfFrames];
		while (in.ready()){
			String address=in.readLine();
			totalAccess++;
			optimal.CheckPage(address,totalAccess);
		}
		in.close();
		in = new BufferedReader(new FileReader(file));
		int frames=0;
		totalAccess=0;
		while (in.ready()){
			String address=in.readLine();
			String optimalEviction="";
			int evictionIndex=0;
			String[] contents=address.split(" ");
			contents[0]=contents[0].substring(0,5);
			int indexOfPage=Integer.parseInt(contents[0],16);
			String PageIndex=Integer.toString(indexOfPage);
			totalAccess++;
			if (frames<numberOfFrames){
				if (!pageTable.CheckPage(address,totalAccess)){
					for (int i=0;i<numberOfFrames;i++){
						if (RAM[i]==null){
							RAM[i]=PageIndex;
							break;
						}
					}
					pageFaults++;
					frames++;
				}
			}
			else {
				if (!pageTable.CheckPage(address,totalAccess)){
					pageFaults++;
					for (String key : RAM){
						int timesUsed=pageTable.PageTable.get(key).timesUsed;
						int totalTimesUsed = optimal.PageTable.get(key).timesUsed;
						if (totalTimesUsed==timesUsed){
							optimalEviction=key;
							break;
						}
						else {
							if (optimal.PageTable.get(key).index.get(timesUsed)>evictionIndex){
								evictionIndex=optimal.PageTable.get(key).index.get(timesUsed);
								optimalEviction=key;
							}
						}
					}
					if (pageTable.PageTable.get(optimalEviction).dirtyBit==1) writes++;
					for (int i=0;i<numberOfFrames;i++){
						if (RAM[i].equals(optimalEviction)){
							RAM[i]=PageIndex;
							break;
						}
					}
					pageTable.PageTable.remove(optimalEviction);
				}
			}
			//System.out.println("Faults: " + pageFaults + " Total: " + totalAccess + " Address: " + contents[0]);
		}
		System.out.println("Frames: " + numberOfFrames);
		System.out.println("Algorithm: " + name);
		System.out.println("Total Accesses: " + totalAccess);
		System.out.println("Total Page Faults: " + pageFaults);
		System.out.println("Total Writes: " + writes);
		in.close();
	}
}

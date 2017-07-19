import java.util.ArrayList;

public class DLB implements DictionaryInterface{
	
	private class Node{
		private Node nextLevel;
		private Node anotherCharacter;
		private char data='\0';
		private boolean endOfWord=false;
		private StringBuilder findWord;
		
		private Node(char c){
			data=c;
		}
		public boolean isNextLevel(){
			return nextLevel !=null;
		}
		public boolean isAnotherChar(){
			return anotherCharacter != null;
		}
	}
	
	private Node rootNode;
	private Node currentNode;
	private char[] arrayOfChars;
	private int currentIndex;
	private int guessedIndex=0;
	
	public boolean add(String s) {
		currentIndex=0;
		arrayOfChars=s.toCharArray();
		currentNode=rootNode;
		if (rootNode==null){
			rootNode=new Node(arrayOfChars[currentIndex++]);
			rootNode.nextLevel=new Node('\0');
			currentNode=rootNode.nextLevel;
		}
		for (;currentIndex<arrayOfChars.length;currentIndex++){
			if (currentNode.data=='\0' && !currentNode.endOfWord){
				currentNode.data=arrayOfChars[currentIndex];
				currentNode.nextLevel=new Node('\0');
				currentNode=currentNode.nextLevel;
			}
			else{
				while ((currentNode.isAnotherChar() && currentNode.data!=arrayOfChars[currentIndex])){
					currentNode=currentNode.anotherCharacter;
				}
				
				if (currentNode.data==arrayOfChars[currentIndex]){
					currentNode=currentNode.nextLevel;
				}
				else{
					currentNode.anotherCharacter=new Node(arrayOfChars[currentIndex]);
					currentNode=currentNode.anotherCharacter;
					currentNode.nextLevel=new Node('\0');
					currentNode=currentNode.nextLevel;
				}
			}
		}
		currentNode.endOfWord=true;
		currentNode=rootNode;
		return true;
	}

	public int search(StringBuilder s) {
		StringBuilder sb=s;
		int currentState=0;
		int cIndex=0;
		boolean notFound=false;
		currentNode=rootNode;
		for (;cIndex<sb.length() && !notFound;cIndex++){
			while (currentNode.isAnotherChar() && currentNode.data!=sb.charAt(cIndex) && sb.charAt(cIndex)!='*'){
				currentNode=currentNode.anotherCharacter;
			}
			if (currentNode.data==sb.charAt(cIndex)){
				currentNode=currentNode.nextLevel;
			}
			else if (sb.charAt(cIndex)=='*' && !currentNode.endOfWord){
				currentNode=currentNode.nextLevel;
			}
			
			else{
				return 0;
			}
		}
		if (currentNode.data=='\0' && currentNode.isAnotherChar()){
			return 3;
		}
		else if (currentNode.data=='\0' && !currentNode.isAnotherChar()){
			return 2;
		}
		else{
			return 1;
		}
	}
}

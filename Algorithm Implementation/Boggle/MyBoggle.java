import java.io.*;
import java.util.*;


public class MyBoggle {

	public static void main(String[] args) throws IOException {
		boolean running=true;
		boolean[][] check= new boolean[4][4];
		ArrayList<String> addedWords=new ArrayList<String>();
		ArrayList<String> guessedWords=new ArrayList<String>();
		DictionaryInterface bD;
		DictionaryInterface eD;
		StringBuilder sB=new StringBuilder();
		char[][] boggle= new char[4][4];
		
		if (args[0].equals("-b")){
			bD=new SimpleDictionary();
			eD=new SimpleDictionary();
			if(args[3].toLowerCase().equals("dlb")){
				bD=new DLB();
				eD=new DLB();
			}
			else if (args[3].toLowerCase().equals("simple")){
				bD=new SimpleDictionary();
				eD=new SimpleDictionary();
			}
			else{
				System.out.println("Please input a specific type of Dictionary.");
			}
			boggle=CreateBoard(args[1].toUpperCase(),boggle);
			eD=CreateDictionaryOfWords("dictionary.txt",eD);
		}
		else if (args[0].equals("-d")){
			bD=new SimpleDictionary();
			eD=new SimpleDictionary();
			if(args[1].toLowerCase().equals("dlb")){
				bD=new DLB();
				eD=new DLB();
			}
			else if (args[1].toLowerCase().equals("simple")){
				bD=new SimpleDictionary();
				eD=new SimpleDictionary();
			}
			else if (args[1].equals("-b")){
				bD=new SimpleDictionary();
				eD=new SimpleDictionary();
			}
			else if ((args[3].equals(null))){
				bD=new SimpleDictionary();
				eD=new SimpleDictionary();
			}
			else{
				System.out.println("Please input a specific type of Dictionary.");
			}
			boggle=CreateBoard(args[3].toUpperCase(),boggle);
			eD=CreateDictionaryOfWords("dictionary.txt",eD);
		}
		else {
			bD=new SimpleDictionary();
			eD=new SimpleDictionary();
		}
		for (int x=0;x<4;x++){
			for (int y=0;y<4;y++){
			ScanTheBoard(boggle,x,y,check,sB,bD,eD,addedWords);
			}
		}
		System.out.println("Welcome to Boggle! (If you need to reprint the board, type 'reprnt')");
		PrintBoard(boggle);
		while (running) {
			running=AskForWord(bD,running,boggle,guessedWords);
		}
		FinishingStats(guessedWords,addedWords);
	}
  
	private static void FinishingStats(ArrayList<String> guessedWords, ArrayList<String> addedWords) {
		System.out.println("Game Over. \n");
		Collections.sort(guessedWords);
		System.out.println("Here are the words you guessed correctly:\n");
		for (int i=0;i<guessedWords.size();i++){
			System.out.println(guessedWords.get(i));
		}
		System.out.println("\nHere are all of the available words you could've guessed:\n");
		Collections.sort(addedWords);
		for (int i=0;i<addedWords.size();i++){
			System.out.println(addedWords.get(i));
		}
		System.out.println("There were a total of " + addedWords.size() + " words in this Boggle board, and you guessed " + guessedWords.size() +" of them.");
		System.out.println("You found %" + ((double)guessedWords.size()/(double)addedWords.size())*100 + " of the words.");
	}

	private static StringBuilder ScanTheBoard(char[][] boggle, int x, int y, boolean[][] check, StringBuilder sB,
			DictionaryInterface bD, DictionaryInterface eD, ArrayList<String> addedWords) {
		if (check[x][y]==false){
			check[x][y]=true;
			sB.append(boggle[x][y]);
			if (boggle[x][y]=='*'){
				sB.deleteCharAt(sB.length()-1);
				char[] alphabet={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
				for (int i=0;i<alphabet.length;i++){
					check[x][y]=true;
					SearchWithAsterisk(boggle,x,y,check,sB,bD,eD,addedWords,alphabet,i);
					sB.deleteCharAt(sB.length()-1);
					check[x][y]=false;
				}
			}
			else{
			int result=eD.search(sB);
			if (result==2){
				String word=sB.toString();
				boolean f=false;
				for(int i=0;i<addedWords.size();i++){
					if (word.toUpperCase().equals(addedWords.get(i).toUpperCase())){
						f=true;
					}
				}
				if (!f){
					addedWords.add(word.toUpperCase());
				}
				bD.add(word);
				sB=sB.deleteCharAt(sB.length()-1);
				check[x][y]=false;
				return sB;
			}
			else if (result==3){
				String word=sB.toString();
				boolean f=false;
				for(int i=0;i<addedWords.size();i++){
					if (word.toUpperCase().equals(addedWords.get(i).toUpperCase())){
						f=true;
					}
				}
				if (!f){
					addedWords.add(word.toUpperCase());
				}
				bD.add(word);
			}
			else if (result==0){
				sB=sB.deleteCharAt(sB.length()-1);
				check[x][y]=false;
				return sB;
			}
			
		
		if (y!=3 && check[x][y+1]==false) sB=ScanTheBoard(boggle,x,y+1,check,sB,bD,eD,addedWords);
		if (x!=3 && y!=3 && check[x+1][y+1]==false) sB=ScanTheBoard(boggle,x+1,y+1,check,sB,bD,eD,addedWords);
		if (x!=3 && check[x+1][y]==false) sB=ScanTheBoard(boggle,x+1,y,check,sB,bD,eD,addedWords);
		if (y!=0 && x!=3 && check[x+1][y-1]==false) sB=ScanTheBoard(boggle,x+1,y-1,check,sB,bD,eD,addedWords);
		if (y!=0 && check[x][y-1]==false) sB=ScanTheBoard(boggle,x,y-1,check,sB,bD,eD,addedWords);
		if (x!=0 && y!=0 && check[x-1][y-1]==false) sB=ScanTheBoard(boggle,x-1,y-1,check,sB,bD,eD,addedWords);
		if (x!=0 && check[x-1][y]==false) sB=ScanTheBoard(boggle,x-1,y,check,sB,bD,eD,addedWords);
		if (x!=0 && y!=3&& check[x-1][y+1]==false) sB=ScanTheBoard(boggle,x-1,y+1,check,sB,bD,eD,addedWords);
		check[x][y]=false;
		return sB.deleteCharAt(sB.length()-1);
			}
		}
		return sB;
	}

	private static StringBuilder SearchWithAsterisk(char[][] boggle, int x, int y,
			boolean[][] check, StringBuilder sB, DictionaryInterface bD,
			DictionaryInterface eD, ArrayList<String> addedWords, char[] alphabet, int i) {
		sB.append(alphabet[i]);
		int result=eD.search(sB);
		if (result==2){
			String word=sB.toString();
			boolean f=false;
			for(int j=0;j<addedWords.size();j++){
				if (word.toUpperCase().equals(addedWords.get(j).toUpperCase())){
					f=true;
				}
			}
			if (!f){
				addedWords.add(word.toUpperCase());
			}
			bD.add(word);
			check[x][y]=false;
			return sB;
		}
		else if (result==3){
			String word=sB.toString();
			boolean f=false;
			for(int j=0;j<addedWords.size();j++){
				if (word.toUpperCase().equals(addedWords.get(j).toUpperCase())){
					f=true;
				}
			}
			if (!f){
				addedWords.add(word.toUpperCase());
			}
			bD.add(word);
		}
		else if (result==0){
			check[x][y]=false;
			return sB;
		}
	
		if (y!=3 && check[x][y+1]==false) sB=ScanTheBoard(boggle,x,y+1,check,sB,bD,eD,addedWords);
		if (x!=3 && y!=3 && check[x+1][y+1]==false) sB=ScanTheBoard(boggle,x+1,y+1,check,sB,bD,eD,addedWords);
		if (x!=3 && check[x+1][y]==false) sB=ScanTheBoard(boggle,x+1,y,check,sB,bD,eD,addedWords);
		if (y!=0 && x!=3 && check[x+1][y-1]==false) sB=ScanTheBoard(boggle,x+1,y-1,check,sB,bD,eD,addedWords);
		if (y!=0 && check[x][y-1]==false) sB=ScanTheBoard(boggle,x,y-1,check,sB,bD,eD,addedWords);
		if (x!=0 && y!=0 && check[x-1][y-1]==false) sB=ScanTheBoard(boggle,x-1,y-1,check,sB,bD,eD,addedWords);
		if (x!=0 && check[x-1][y]==false) sB=ScanTheBoard(boggle,x-1,y,check,sB,bD,eD,addedWords);
		if (x!=0 && y!=3&& check[x-1][y+1]==false) sB=ScanTheBoard(boggle,x-1,y+1,check,sB,bD,eD,addedWords);
		check[x][y]=false;
		return sB;
	}

	private static DictionaryInterface CreateDictionaryOfWords(String arg, DictionaryInterface eD) {
		Scanner scan;
		String word="";
		ArrayList<String> aW = new ArrayList<String>();
		try {
			scan = new Scanner(new FileInputStream(arg));
			while (scan.hasNext()){
			word = scan.nextLine();
			eD.add(word.toUpperCase());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary not found.");
			System.exit(0);
		}
		return eD;
	}

	private static boolean AskForWord(DictionaryInterface bD, boolean running,char[][] boggle, ArrayList<String> guessedWords) {
		String word="";
		Scanner in = new Scanner(System.in);
		System.out.print("Please insert a word(Type '/0' to quit): ");
		word=in.next();
		StringBuilder s = new StringBuilder(word.toUpperCase());
		int choice=bD.search(s);
		if (word.equals("reprnt") || word.equals("/0")) choice=4;
		if (choice==0){
			System.out.println(word + " is not a word.");
		}
		else if (choice==1){
			System.out.println(word + " is a prefix.");
		}
		else if (choice==2){
			System.out.println(word + " is a word.");
			boolean f=false;
			for(int i=0;i<guessedWords.size();i++){
				if (word.toUpperCase().equals(guessedWords.get(i).toUpperCase())){
					f=true;
				}
			}
			if (!f){
				guessedWords.add(word.toUpperCase());
			}
		}
		else if (choice==3){
			System.out.println(word + " is a word and a prefix.");
			boolean f=false;
			for(int i=0;i<guessedWords.size();i++){
				if (word.toUpperCase().equals(guessedWords.get(i).toUpperCase())){
					f=true;
				}
			}
			if (!f){
				guessedWords.add(word.toUpperCase());
			}
		}
		if (word.equals("/0")){
			running=false;
		}
		if (word.equals("reprnt")){
			PrintBoard(boggle);
		}
		return running;
	}

	private static void PrintBoard(char[][] boggle) {
		
		System.out.println("| | | | |\n|---|---|---|---|");
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				System.out.print("|" + boggle[i][j]);
			}
			System.out.println("|");
		}
	}

	private static char[][] CreateBoard(String args, char[][] board) throws IOException {
		String contents="";
		int index=0;
		FileInputStream in=null;
		try{
			in = new FileInputStream(args);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			if(bf.ready()){
				contents=bf.readLine();
			}
			char[] hold = contents.toCharArray();
			for (int i=0;i<4;i++){
				for (int j=0;j<4;j++){
					board[i][j]=hold[index++];
				}
			}
		}
		catch (FileNotFoundException e) {
			if (args.length()==16){
				char[] hold = args.toCharArray();
				for (int i=0;i<4;i++){
					for (int j=0;j<4;j++){
						board[i][j]=hold[index++];
					}
				}
			}
			else{
				System.out.println("Please input a complete Boggle board.");
			}
		}
		return board;
	}
}
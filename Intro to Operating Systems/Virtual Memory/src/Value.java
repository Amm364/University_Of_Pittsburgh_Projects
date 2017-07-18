import java.util.ArrayList;

public class Value {
	Byte referenceBit;
	Byte dirtyBit;
	String hex;
	int timesUsed;
	int clockTime;
	ArrayList<Integer> index = new ArrayList<Integer>();
	
	public Value(Byte dBit){
		referenceBit=1;
		dirtyBit=dBit;
		timesUsed=0;
		clockTime=0;
		hex="";
	}
	public Value(Integer i){
		index.add(i);
	}
	
	public void Dereference(){
		referenceBit=0;
	}
}

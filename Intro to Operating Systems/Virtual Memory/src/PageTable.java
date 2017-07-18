import java.util.HashMap;

public class PageTable {
	HashMap<String, Value> PageTable;
	
	public PageTable(){
		PageTable = new HashMap<String,Value>();
	}
	
	public boolean CheckPage(String address,int total){
		Byte dirty=0;
		String[] contents=address.split(" ");
		if (contents[1].equals("W")){
			dirty = 1;
		}
		else{
			dirty = 0;
		}
		Value PageInfo = new Value(dirty);
		contents[0]=contents[0].substring(0,5);
		PageInfo.hex=contents[0];
		int indexOfPage=Integer.parseInt(contents[0],16);
		String PageIndex=Integer.toString(indexOfPage);
		if (PageTable.containsKey(PageIndex)){
			Value hold=PageTable.get(PageIndex);
			hold.index.add(total);
			hold.timesUsed++;
			PageTable.put(PageIndex,hold);
			return true;
		}
		else {
			PageInfo.timesUsed++;
			PageInfo.index.add(total);
			PageTable.put(PageIndex, PageInfo);
			return false;
		}
		
	}
}

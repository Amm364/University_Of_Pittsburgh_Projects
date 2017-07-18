import java.util.ArrayList;

public class Location {
	private String name;
	private boolean outside;
	ArrayList<Street> streets = new ArrayList<Street>();
	private int numberOfAdjacentStreets;
	
	public Location(String locationName,boolean isOutside){
		name=locationName;
		outside=isOutside;
		numberOfAdjacentStreets=0;
	}
	
	public String getLocationName(){
		return name;
	}
	
	public int getNumberOfAdjacentStreets(){
		return numberOfAdjacentStreets;
	}
	
	public boolean isOutsideCity(){
		return outside;
	}
	
	public boolean addAdjacentStreet(Street s){
		if (streets.add(s)){
			numberOfAdjacentStreets++;
			return true;
		}
		else return false;
	}
	
	public Street getStreet(int index){
		return streets.get(index);
	}
	
	public String toString(){
		return name;
	}
	
}

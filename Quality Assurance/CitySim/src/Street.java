
public class Street {
	private String name;
	private Location location1;
	private Location location2;
	
	public Street(String streetName){
		name=streetName;
	}
	
	public String getStreetName(){
		return name;
	}
	
	public Location traverseStreet(Location start){
		if(start!=location1 && location1 != null){
			return location1;
		}
		else{
			return location2;
		}
	}
	
	public boolean addLocation(Location l){
		if (location1==null && l != location2){
			location1=l;
			return true;
		}
		else if (location2==null && l != location1){
			location2=l;
			return true;
		}
		else{
			System.out.println("Error trying to add that location.");
			return false;
		}
	}
	
}


public class Cat {
	String name;
	Boolean rented;
	double cost;
	int id;
	int currentDaysRented;
	public Cat(String catName,double c,int cid){
		name=catName;
		rented=false;
		cost=c;
		id=cid;
		currentDaysRented=0;
	}
	
	public boolean rentCat(){
		if (rented){
			System.out.println("Cat with ID " + id + " has already been rented");
			return false;
		}
		else{
			rented=true;
			System.out.println("Cat with ID " + id + " is now being rented.");
			return true;
		}
	}
	public boolean returnCat(){
		if (rented){
			rented=false;
			System.out.println("Cat with ID " + id + " has been returned.");
			return true;
		}
		else{
			System.out.println("Cat with ID " + id + " is not currently rented.");
			return false;
		}
	}
}

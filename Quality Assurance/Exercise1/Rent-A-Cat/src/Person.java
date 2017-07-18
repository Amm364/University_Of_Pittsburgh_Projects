
public class Person {
	String name;
	int id;
	String catName;
	Boolean rentedACat;
	int catID;
	
	public Person(String n,int i){
		name=n;
		id=i;
		rentedACat=false;
		catID=0;
	}
	
	public boolean rentCat(Cat c){
		if (rentedACat){
			System.out.println("Person " + id + " has already rented a cat.");
			return false;
		}
		else{
			rentedACat=true;
			catName=c.name;
			c.rentCat();
			catID=c.id;
			System.out.println("Person " + id + " has rented " + catName);
			return true;
		}
	}
	public boolean returnCat(Cat c){
		if (rentedACat){
			if (c.id==catID){
				rentedACat=false;
				catID=0;
				c.returnCat();
				System.out.println("Person " + id + " has returned " + catName);
				return true;
			}
			else{
				System.out.println("Person does not own that cat.");
				return false;
			}
		}
		else{
			System.out.println("Person " + id + " does not have a cat to return.");
			return false;
		}
	}
}

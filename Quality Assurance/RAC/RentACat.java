
public class RentACat {
	public static void main(String args[]){
		Cat c1 = new Cat("Billy",25.00,1);
		Cat c2 = new Cat("George",20.00,2);
		Cat c3 = new Cat("Sally",35.00,3);
		Cat c4 = new Cat("Danny",50.00,4);
		Cat c5 = new Cat("Alex",15.00,5);
		Cat c6 = new Cat("Damani",23.00,6);
		Cat c7 = new Cat("Dj",31.50,7);
		
		Person p1 = new Person("Alex",10);
		Person p2 = new Person("Josh",11);
		Person p3 = new Person("Chris",12);
		Person p4 = new Person("Adam",13);
		
		p1.rentCat(c1);
		p1.rentCat(c2);
		p2.rentCat(c3);
		p3.rentCat(c4);
		p4.rentCat(c7);
		p1.returnCat(c4);
		p1.returnCat(c1);
		
	}
	
	public boolean rentCat(Person p,Cat c){
		if (!p.rentedACat && !c.rented){
			p.rentCat(c);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean returnCat(Person p,Cat c){
		if (p.rentedACat && c.rented){
			p.returnCat(c);
			return true;
		}
		else{
			return false;
		}
	}
}

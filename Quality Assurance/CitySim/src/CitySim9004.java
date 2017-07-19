import java.util.Random;

public class CitySim9004 {
	public static void main(String args[]){
		int seed = getSeed(args);
		if (seed==-1){
			System.out.println("Shutting down.");
			System.exit(0);
		}
		Location presby = new Location("Presby",false);
		Location union = new Location("Union",false);
		Location hillman = new Location("Hillman",false);
		Location sennott = new Location("Sennott",false);
		Location cleveland = new Location("Cleveland",true);
		Location philadelphia = new Location("Philadelphia",true);
		Street bill = new Street("Bill St.");
		Street phil = new Street("Phil St.");
		Street fourth = new Street("Fourth Ave.");
		Street fourthToPhilly = new Street("Fourth Ave.");
		Street fifth = new Street("Fifth Ave.");
		Street fifthToCleveland = new Street("Fifth Ave.");
		Location[] startingPoint = {presby,union,hillman,sennott};
		connectStreetAndLocation(presby,fourth);
		connectStreetAndLocation(union,fourth);
		connectStreetAndLocation(presby,bill);
		connectStreetAndLocation(union,fourthToPhilly);
		connectStreetAndLocation(union,phil);
		connectStreetAndLocation(hillman,phil);
		connectStreetAndLocation(hillman,fifth);
		connectStreetAndLocation(sennott,bill);
		connectStreetAndLocation(sennott,fifth);
		connectStreetAndLocation(sennott,fifthToCleveland);
		connectStreetAndLocation(cleveland,fifthToCleveland);
		connectStreetAndLocation(philadelphia,fourthToPhilly);
		
		Random rand = new Random(seed);
		Driver driver = new Driver(1);
		while (driver.getDriverNumber()<6){
			int randomStartingNum=rand.nextInt(4);
			Location driverLocation=startingPoint[randomStartingNum];
			while (driverLocation.isOutsideCity()==false){
				if (driverLocation.getLocationName().equals("Sennott")) driver.visitLaboon();
				int streetNum=driver.getRandomDirection(rand, driverLocation.getNumberOfAdjacentStreets());
				Location nextLocation=moveDriver(streetNum,driverLocation,driver.getDriverNumber());
				driverLocation=nextLocation;
			}
			System.out.println("Driver " + driver.getDriverNumber() + " has left for " + driverLocation.getLocationName() + "!");
			System.out.println(laboonOutput(driver));
			int newDriver=driver.getDriverNumber();
			newDriver++;
			driver = new Driver(newDriver);
			System.out.println("-----");
		}
	}
	

	public static int getSeed(String[] args) {
		if (args.length!=1){
			System.out.println("Error! Invalid arguments. CitySim required arguement(s): <Integer>");
			return -1;
		}
		else{
			try{
				return Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e){
				System.out.println("Error! This program is expecting an Integer, not a String!");
				return -1;
			}
		}	
	}


	public static boolean connectStreetAndLocation(Location l, Street s){
		if (l.addAdjacentStreet(s) && s.addLocation(l)) return true;
		else return false;
	}
	
	public static Location moveDriver(int streetNum,Location driverLocation, int driver){
		Street streetToTraverse=driverLocation.getStreet(streetNum);
		Location nextLocation = streetToTraverse.traverseStreet(driverLocation);
		printWords(driver,driverLocation,nextLocation,streetToTraverse);
		return nextLocation;
	}
	
	public static String laboonOutput(Driver driver){
		if (driver.getTimesVisitedLaboon()==0){
			return "Driver " + driver.getDriverNumber() + " did not visit Professor Laboon. That student missed out!";
		}
		else if (driver.getTimesVisitedLaboon()==1){
			return "Driver " + driver.getDriverNumber() + " visited Professor Laboon " + driver.getTimesVisitedLaboon() + " time.";
		}
		else if (driver.getTimesVisitedLaboon()>1){
			if (driver.getTimesVisitedLaboon()>=3){
				return "Driver " + driver.getDriverNumber() + " visited Professor Laboon " + driver.getTimesVisitedLaboon() + " times. That student needed a lot of help!";
			}
			else{
				return "Driver " + driver.getDriverNumber() + " visited Professor Laboon " + driver.getTimesVisitedLaboon() + " times.";
			}
		}
		
		else{
			return "";
		}
	}
	
	public static boolean printWords(int driverNum,Location driverLocation, Location nextLocation,Street traverse){
		if (driverLocation!=null && nextLocation!=null && traverse!=null){
			System.out.println("Driver " + driverNum + " heading from " + driverLocation.getLocationName() + " to " + nextLocation.getLocationName() + " via " + traverse.getStreetName());
			return true;
		}
		else return false;
	}
}

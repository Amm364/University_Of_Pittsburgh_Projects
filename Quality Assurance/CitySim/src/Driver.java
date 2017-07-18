import java.util.Random;

public class Driver {
	private int driverNumber;
	private int timesVisitedLaboon;
	public Driver(int num){
		driverNumber=num;
		timesVisitedLaboon=0;
	}
	
	public int getRandomDirection(Random rand,int max) {
		return rand.nextInt(max);
	}
	
	public int getDriverNumber(){
		return driverNumber;
	}
	
	public int getTimesVisitedLaboon(){
		return timesVisitedLaboon;
	}
	
	public boolean visitLaboon(){
		timesVisitedLaboon++;
		return true;
	}
	
}

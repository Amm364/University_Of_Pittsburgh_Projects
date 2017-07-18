import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import org.mockito.*;

public class CitySimUnitTest {
	
	/**
	 * Test to see if I can get the location name of a location
	 * In the test, I make the Location 'Presby' and expect 'Presby' to be returned.
	 */
	@Test
	public void testGetLocationName(){
		Location l = new Location("Presby",false);
		assertEquals("Presby",l.getLocationName());
	}
	
	/**
	 * Test to see if the current city is an outside city.
	 * Should return true if it is an outside city.
	 */
	@Test
	public void testIsOutsideCity(){
		Location l = new Location("Presby",true);
		assertEquals(true,l.isOutsideCity());
	}
	
	/**
	 * Test to see how many streets are connected to this Location
	 * This location is connected to one street and should return 1
	 */
	@Test
	public void testGetNumberOfAdjacentStreets(){
		Location l = new Location("Presby",false);
		Street s = Mockito.mock(Street.class);
		l.addAdjacentStreet(s);
		assertEquals(1,l.getNumberOfAdjacentStreets());
	}
	
	/**
	 * Tests to see if a street was successfully added to the Location
	 * Should return true if successful
	 */
	@Test
	public void testAddAdjacentStreet(){
		Location l = new Location("Presby",false);
		Street s = Mockito.mock(Street.class);
		assertEquals(true,l.addAdjacentStreet(s));
	}
	
	/**
	 * Testing to see if a location was successfully added to a street.
	 * Should return true if successful
	 */
	@Test
	public void testAddLocationToStreet(){
		Location l = Mockito.mock(Location.class);
		Street s = new Street("Bill St.");
		assertEquals(true,s.addLocation(l));
	}
	
	/**
	 * Tests to see if the method handles an error adding a 3rd location to a street
	 * Should return false because streets should only have 2 connected locations
	 */
	@Test
	public void testAddLocationToStreetFail(){
		Location l1 = Mockito.mock(Location.class);
		Location l2 = Mockito.mock(Location.class);
		Location l3 = Mockito.mock(Location.class);
		Street s = new Street("Bill St.");
		s.addLocation(l1);
		s.addLocation(l2);
		assertEquals(false,s.addLocation(l3));
	}
	
	/**
	 * Tests to see if the method traverStreet successfully traverses to the next location
	 * Should return the location that the driver went to from the street.(Presby->Hillman)
	 */
	@Test
	public void testTraverseStreet(){
		Location l1 = new Location("Presby",false);
		Location l2 = new Location("Hillman",false);
		Location current=l1;
		Street s = new Street("Bill");
		s.addLocation(l1);
		s.addLocation(l2);
		current=s.traverseStreet(current);
		assertEquals(l2,current);
	}
	
	/*
	 * Attempt to add the same location to the edge.
	 * Should return fail if adding the same location to the street
	 */
	@Test
	public void testAddSameLocationFail(){
		Location l1 = new Location("Presby",false);
		Street s = new Street("Bill");
		s.addLocation(l1);
		assertEquals(false,s.addLocation(l1));
	}
	
	/**
	 * Test the method that creates a connection between a location and a street.
	 * This method calls addLocationToStreet and addAdjacentStreet and returns success or not. 
	 **/
	@Test
	public void testConnectStreetAndLocation(){
		Location l = Mockito.mock(Location.class);
		Street s = Mockito.mock(Street.class);
		Mockito.when(l.addAdjacentStreet(s)).thenReturn(true);
		Mockito.when(s.addLocation(l)).thenReturn(true);
		boolean result=CitySim9004.connectStreetAndLocation(l, s);
		assertEquals(true,result);
	}
	/**
	 * This method will test the getRandomDirection method, which is supposed to return
	 * an integer value that will be used to determine the direction of the driver. (ie. which road to take)
	 * I make a mock driver and test its random direction class. Returns true if it return a num between 0-10
	 */
	@Test
	public void testGetRandomDirection(){
		Random rand = new Random();
		int r=0;
		boolean result;
		Driver d = new Driver(1);
		r=d.getRandomDirection(rand, 10);
		if (r>=0 && r<10){
			result=true;
		}
		else{
			result=false;
		}
		assertEquals(true,result);
	}
	
	/**
	 * Tests the moveDriver method in the CitySim class.
	 */
	@Test
	public void testMoveDriver(){
		Location presby = new Location("Presby",false);
		Random rand = Mockito.mock(Random.class);
		Location union = new Location("Union",false);
		Street fourth = new Street("Fourth Ave.");
		CitySim9004.connectStreetAndLocation(presby,fourth);
		CitySim9004.connectStreetAndLocation(union,fourth);
		Location driverLocation=presby;
		Driver d = Mockito.mock(Driver.class);
		Mockito.when(d.getRandomDirection(rand,1)).thenReturn(0);
		Location end = CitySim9004.moveDriver(d.getRandomDirection(rand, 1), driverLocation, d.getDriverNumber());
		assertEquals(end.getLocationName(),"Union");
	}
	
	
	/**
	 * Tests to see if the method visitLaboon increments the visited variable correctly
	 * Should return true after calling the method
	 */
	@Test
	public void testVisitLaboon(){
		Driver d = new Driver(1);
		assertEquals(d.visitLaboon(),true);
	}
	
	/**
	 * Tests to see if the method returns a value coorosponding to the number of Laboon visits
	 * Should return 1 after 1 visit.
	 */
	@Test
	public void testGetNumberOfVisits(){
		Driver d = new Driver(1);
		d.visitLaboon();
		assertEquals(d.getTimesVisitedLaboon(),1);
	}
	
	/**
	 * Tests to see if the method laboonOutput outputs the correct output for 0 visits, which is an edge case
	 * Should print out special output saying that the driver missed out
	 */
	@Test
	public void testLaboonOutputZero(){
		Driver d = Mockito.mock(Driver.class);
		Mockito.when(d.getTimesVisitedLaboon()).thenReturn(0);
		Mockito.when(d.getDriverNumber()).thenReturn(1);
		assertEquals("Driver " + d.getDriverNumber() + " did not visit Professor Laboon. That student missed out!",CitySim9004.laboonOutput(d));
	}
	
	/**
	 * Tests to see if the method laboonOutput outputs the correct output for 3 or more visits, which is an edge case
	 * Should print out special output saying that the driver really needed help
	 */
	@Test
	public void testLaboonOutputMany(){
		Driver d = Mockito.mock(Driver.class);
		Mockito.when(d.getTimesVisitedLaboon()).thenReturn(3);
		Mockito.when(d.getDriverNumber()).thenReturn(1);
		assertEquals("Driver " + d.getDriverNumber() + " visited Professor Laboon " + d.getTimesVisitedLaboon() + " times. That student needed a lot of help!",CitySim9004.laboonOutput(d));
	}
	
	/**
	 * Tests to see if the method laboonOutput outputs the correct output for a normal amount of visits
	 * Should print out how many times they visited Laboon
	 */
	@Test
	public void testRegularLaboonOutput(){
		Driver d = Mockito.mock(Driver.class);
		Mockito.when(d.getTimesVisitedLaboon()).thenReturn(2);
		Mockito.when(d.getDriverNumber()).thenReturn(1);
		assertEquals("Driver " + d.getDriverNumber() + " visited Professor Laboon " + d.getTimesVisitedLaboon() + " times.",CitySim9004.laboonOutput(d));
	}
	
	/**
	 * Tests to see if getArgs can properly parse an int as an argument for the program seed
	 * Should return 3 as an int
	 */
	@Test
	public void testGetArg(){
		String arg[] = {"3"};
		assertEquals(CitySim9004.getSeed(arg),3);
	}
	
	/**
	 * Tests to see if getArgs can handle an error when more than 1 argument is passed
	 * should return a -1 
	 */
	@Test
	public void testGetMultipleArgFail(){
		String arg[] = {"3","Hundred"};
		assertEquals(CitySim9004.getSeed(arg),-1);
	}
	
	/**
	 * tests to see if it can handle an error when the only argument is not an int
	 * should return -1
	 */
	@Test
	public void testGetArgFail(){
		String arg[] = {"test"};
		assertEquals(CitySim9004.getSeed(arg),-1);
	}
	
	/**
	 * Tests to see if the print program successfully printed to the command line.
	 * Should return true
	 */
	@Test
	public void testPrint(){
		Location l = new Location("Presby",false);
		Location l2 = new Location("Sennott",false);
		int num = 1;
		Street s = new Street("Bill");
		assertEquals(CitySim9004.printWords(num, l, l2, s),true);
	}
	
	public static void main(String args[]){
		
	}
}

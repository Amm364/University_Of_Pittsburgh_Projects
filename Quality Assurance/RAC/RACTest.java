import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;

public class RACTest {
	
	@Test
	public void testRentCat(){
		Person p = new Person("Alex",1);
		Cat c = Mockito.mock(Cat.class);
		boolean result=p.rentCat(c);
		assertEquals(true,result);
	}
	
	@Test
	public void testRentCatFail(){
		Person p = new Person("Alex",1);
		Cat c = Mockito.mock(Cat.class);
		Cat c1 = Mockito.mock(Cat.class);
		p.rentCat(c);
		boolean result=p.rentCat(c1);
		assertEquals(true,result);
	}
}

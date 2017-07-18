import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;

public class RACTest {
	
	@test
	public void testRentCat(){
		Person p = Mockito.mock(Person.class);
		Cat c = Mockito.mock(Cat.class);
		boolean result=p.rentCat(c);
		assertEquals(true,result);
	}
	
	@test
	public void testRentCatFail(){
		Person p = Mockito.mock(Person.class);
		Cat c = Mockito.mock(Cat.class);
		Cat c1 = Mockito.mock(Cat.class);
		p.rentCat(c);
		boolean result=p.rentCat(c1);
		assertEquals(false,result);
	}
}

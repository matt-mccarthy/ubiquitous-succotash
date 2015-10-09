import static org.junit.Assert.*;
import org.junit.Test;

public class ReversalTest
{

	@Test
	public void testReverseLineEmpty()
	{
		String actual = Reversal.reverseLine("");
		String expected = "";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testReverseLineSingleton()
	{
		String actual = Reversal.reverseLine("Hello");
		String expected = "Hello";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testReverseLineLong()
	{
		String actual = Reversal.reverseLine("Hello I am Matt");
		String expected = "Matt am I Hello";
		
		assertEquals(actual, expected);
	}
}

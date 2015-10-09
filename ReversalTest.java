import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ReversalTest
{
	private static String fName = "testFile";
	private static File file;
	
	@Before
	public static void createFile()
	{
		file = new File(fName);
		try {
			if (! file.createNewFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setUpTestFile()
	{
		
	}

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
	
	@Test
	public void testReverseFile()
	{
		
		
		fail();
	}
}

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReversalTest
{
	private static String inName = "input";
	private static String outName = "output";
	private static Scanner readFile;
	private static PrintWriter writeFile;
	private static File inFile;
	private static File outFile;
	private static File nonexistant = new File("im_not_here");

	@BeforeClass
	public static void ensureNotThere()
	{
		inFile = new File(inName);
		outFile = new File(outName);
		
		try
		{
			if (inFile.exists())
			{
				Files.delete(Paths.get(inName));
			}
			if (outFile.exists())
			{
				Files.delete(Paths.get(outName));
			}
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void createFiles()
	{
		try
		{
			inFile.createNewFile();
			outFile.createNewFile();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@After
	public void deleteFiles()
	{
		try
		{
			Files.delete(Paths.get(inName));
			Files.delete(Paths.get(outName));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testCTor()
	{
		Reversal reverse = new Reversal();
		
		assertNotNull(reverse);
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
	public void testReverseFileNoBlank()
	{
		try
		{
			writeFile = new PrintWriter(inFile);
			writeFile.println("Hello I am Matt");
			writeFile.println("Hello I am not Matt");
			writeFile.println("Goodbye I am indubitably Matt");
			writeFile.close();
			
			Reversal.reverseFile(inFile, outFile);
			
			String actual	= fileToString();
			String expected	= "Matt indubitably am I Goodbye\n"
							+ "Matt not am I Hello\n"
							+ "Matt am I Hello";
			
			assertEquals(actual, expected);
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReverseFileStartBlank()
	{
		try
		{
			writeFile = new PrintWriter(inFile);
			writeFile.println();
			writeFile.println("Hello I am Matt");
			writeFile.println("Hello I am not Matt");
			writeFile.println("Goodbye I am indubitably Matt");
			writeFile.close();
			
			Reversal.reverseFile(inFile, outFile);
			
			String actual	= fileToString();
			String expected	= "Matt indubitably am I Goodbye\n"
							+ "Matt not am I Hello\n"
							+ "Matt am I Hello\n";
			
			assertEquals(actual, expected);
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReverseFileEndBlank()
	{
		try
		{
			writeFile = new PrintWriter(inFile);
			writeFile.println("Hello I am Matt");
			writeFile.println("Hello I am not Matt");
			writeFile.println("Goodbye I am indubitably Matt");
			writeFile.println();
			writeFile.close();
			
			Reversal.reverseFile(inFile, outFile);
			
			String actual	= fileToString();
			String expected	= "\nMatt indubitably am I Goodbye\n"
							+ "Matt not am I Hello\n"
							+ "Matt am I Hello";
			
			assertEquals(actual, expected);
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNoInput()
	{
		boolean didThrow = false;
		
		try
		{
			Reversal.reverseFile(nonexistant, outFile);
		}
		catch (FileNotFoundException e)
		{
			didThrow = true;
		}
		
		assertTrue(didThrow);
	}
	
	@Test
	public void testNoOutput()
	{
		boolean didThrow = false;
		
		try
		{
			Reversal.reverseFile(inFile, nonexistant);
		}
		catch (FileNotFoundException e)
		{
			didThrow = true;
		}
		
		assertTrue(didThrow);
	}
	
	@Test
	public void testReverseFileMiddleBlank()
	{
		try
		{
			writeFile = new PrintWriter(inFile);
			writeFile.println("Hello I am Matt");
			writeFile.println();
			writeFile.println("Hello I am not Matt");
			writeFile.println("Goodbye I am indubitably Matt");
			writeFile.close();
			
			Reversal.reverseFile(inFile, outFile);
			
			String actual	= fileToString();
			String expected	= "Matt indubitably am I Goodbye\n"
							+ "Matt not am I Hello\n\n"
							+ "Matt am I Hello";
			
			assertEquals(actual, expected);
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String fileToString()
	{
		String fOutput = "";
		
		try
		{
			readFile = new Scanner(outFile);
			
			if (readFile.hasNextLine())
				fOutput = readFile.nextLine();
			
			while (readFile.hasNextLine())
			{
				fOutput += "\n" + readFile.nextLine();
			}
			
			readFile.close();
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fOutput;
	}
}

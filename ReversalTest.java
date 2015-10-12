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
	private static String 		excptTxt	= "Threw an exception";
	private static String		inName		= "input";
	private static String		outName		= "output";
	private static String		notAName	= "im_not_here";
	private static File			inFile;
	private static File			outFile;
	private static File			nonexistant;
	private static Scanner		readFile;
	private static PrintWriter	writeFile;

	// If temporary I/O files are still there for some reason, delete them
	// Initializes the File objects
	@BeforeClass
	public static void ensureNotThere()
	{
		inFile		= new File(inName);
		outFile		= new File(outName);
		nonexistant	= new File(notAName);
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
			if (nonexistant.exists())
			{
				Files.delete(Paths.get("im_not_here"));
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// Create temporary I/O files before each test
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
	
	// Get rid of temporary I/O files after each test
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
		String actual	= Reversal.reverseLine("");
		String expected	= "";

		assertEquals(actual, expected);
	}

	@Test
	public void testReverseLineSingleton()
	{
		String actual	= Reversal.reverseLine("Hello");
		String expected	= "Hello";

		assertEquals(actual, expected);
	}

	@Test
	public void testReverseLineLong()
	{
		String actual	= Reversal.reverseLine("Hello I am Matt");
		String expected	= "Matt am I Hello";

		assertEquals(actual, expected);
	}

	@Test
	public void testReverseFileNoBlank()
	{
		String[] writeMe = {
				"Hello I am Matt",
				"Hello I am not Matt",
				"Goodbye I am indubitably Matt"
		};
		
		stringsToFile(writeMe);
		
		try
		{			
			Reversal.reverseFile(inFile, outFile);
		} 
		catch (FileNotFoundException e)
		{
			fail(excptTxt);
		}
		
		String actual	= fileToString();
		String expected	= "Matt indubitably am I Goodbye\n"
						+ "Matt not am I Hello\n"
						+ "Matt am I Hello";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testReverseFileStartBlank()
	{
		String[] writeMe = {
				"",
				"Hello I am Matt",
				"Hello I am not Matt",
				"Goodbye I am indubitably Matt"
		};
		
		stringsToFile(writeMe);
		
		try
		{		
			Reversal.reverseFile(inFile, outFile);
		} 
		catch (FileNotFoundException e)
		{
			fail(excptTxt);
		}
		
		String actual	= fileToString();
		String expected	= "Matt indubitably am I Goodbye\n"
						+ "Matt not am I Hello\n"
						+ "Matt am I Hello\n";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testReverseFileEndBlank()
	{
		String[] writeMe = {
				"Hello I am Matt",
				"Hello I am not Matt",
				"Goodbye I am indubitably Matt",
				""
		};
		
		stringsToFile(writeMe);
		
		try
		{		
			Reversal.reverseFile(inFile, outFile);			
		} 
		catch (FileNotFoundException e)
		{
			fail(excptTxt);
		}
		
		String actual	= fileToString();
		String expected	= "\nMatt indubitably am I Goodbye\n"
						+ "Matt not am I Hello\n"
						+ "Matt am I Hello";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testReverseFileMiddleBlank()
	{
		String[] writeMe = {
				"Hello I am Matt",
				"",
				"Hello I am not Matt",
				"Goodbye I am indubitably Matt"
		};
		
		stringsToFile(writeMe);
		
		try
		{
			Reversal.reverseFile(inFile, outFile);
		} 
		catch (FileNotFoundException e)
		{
			fail(excptTxt);
		}
		
		String actual	= fileToString();
		String expected	= "Matt indubitably am I Goodbye\n"
						+ "Matt not am I Hello\n\n"
						+ "Matt am I Hello";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void testReverseFileEmptyFile()
	{
		try
		{		
			Reversal.reverseFile(inFile, outFile);
		} 
		catch (FileNotFoundException e)
		{
			fail(excptTxt);
		}
		
		String actual	= fileToString();
		String expected	= "";
		
		assertEquals(actual, expected);
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
	
	// Takes an array of Strings and writes each one as a line in inFile
	public void stringsToFile(String[] in)
	{
		try
		{
			writeFile = new PrintWriter(inFile);
			
			for (String str : in)
				writeFile.println(str);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			writeFile.close();
		}
	}
	
	// Takes all of outFile's contents and puts it in a string
	public String fileToString()
	{
		String fOutput = "";
		
		try
		{
			readFile = new Scanner(outFile);
			
			if (readFile.hasNextLine())
			{
				fOutput = readFile.nextLine();
			
				while (readFile.hasNextLine())
				{
					fOutput += "\n" + readFile.nextLine();
				}
			}
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			readFile.close();
		}
		
		return fOutput;
	}
}

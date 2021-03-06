import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Reversal.java
 * @author Matt McCarthy
 *
 */
public class Reversal
{	
	/**
	 * Reads in an ASCII text file and reverses both the line order and
	 * each line in the file.
	 * @param input File containing the input.
	 * @param output File to which this method will write.
	 * @throws FileNotFoundException Thrown when a file does not exist.
	 */
	public static void reverseFile(File input, File output) 
			throws FileNotFoundException
	{
		// Make sure both files exist
		if ( !(input.exists() && output.exists()) )
			throw new FileNotFoundException();
		
		int					threads		= 7;
		ExecutorService		tPool		= Executors.newFixedThreadPool(threads);
		
		Scanner				readFile	= new Scanner(input);
		PrintWriter			writeFile	= new PrintWriter(output);
		
		Stack<StringBuffer>	outputStack	= new Stack<StringBuffer>();
		
		// Read lines from input and reverse them
		while (readFile.hasNextLine())
		{
			// Get the next line from the file
			final StringBuffer	strBuff = new StringBuffer();
			final String 		curLine	= readFile.nextLine();
			
			outputStack.push(strBuff);
			
			// Launch a thread to reverse that line
			tPool.execute(
						new Thread()
						{
							public void run()
							{
								reverseLine(strBuff, curLine);
							}
						}
					);
			
			Thread.yield();
		}
		
		readFile.close();
		
		tPool.shutdown();
		
		while(!tPool.isTerminated());
		
		// Read out the reversed lines in the reversed order
		while (!outputStack.isEmpty())
			writeFile.println(outputStack.pop().toString());
		
		writeFile.close();
	}
	
	/**
	 * Reverses an input line.
	 * @param inBuffer String buffer to which we write our reversed string.
	 * @param inStr The line to reverse.
	 */
	public static void reverseLine(StringBuffer inBuffer, String inStr)
	{
		// Initialize
		Stack<String>	reversedLine	= new Stack<String>();
		StringReader	stringStream	= new StringReader(inStr);
		Scanner			readStrings		= new Scanner(stringStream);
		
		String			output			= new String("");
		
		// Push strings into the stack
		while (readStrings.hasNext())
			reversedLine.push(readStrings.next());
		
		readStrings.close();
		
		// Empty the stack
		if (!reversedLine.isEmpty())
		{
			output = reversedLine.pop();
			
			while (!reversedLine.isEmpty())
				output += " " + reversedLine.pop();
		}
		
		inBuffer.append(output);
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Scanner;
import java.util.Stack;

public class Reversal
{	
	public static void reverseFile(File input, File output) 
			throws FileNotFoundException
	{
		// Make sure both files exist
		if ( !(input.exists() && output.exists()) )
			throw new FileNotFoundException();
		
		Scanner readFile = new Scanner(input);
		PrintWriter writeFile = new PrintWriter(output);
		Stack<String> outputMatrix = new Stack<String>();
		
		while (readFile.hasNextLine())
			outputMatrix.push(reverseLine(readFile.nextLine()));
		
		readFile.close();
		
		while (!outputMatrix.isEmpty())
			writeFile.println(outputMatrix.pop());
		
		writeFile.close();
	}
	
	public static String reverseLine(String in)
	{
		// Initialize
		Stack<String>	reversedLine	= new Stack<String>();
		StringReader	stringStream	= new StringReader(in);
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
		
		return output;
	}
}

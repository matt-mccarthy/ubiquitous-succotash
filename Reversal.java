import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Stack;

public class Reversal
{	
	public static void reverseFile(File input, File output) 
			throws FileNotFoundException
	{
		Stack< Stack<String> > outputMatrix = new Stack< Stack<String> >();
	}
	
	public static Stack<String> reverseLine(String in)
	{
		Stack<String> reversedLine = new Stack<String>();
		
		InputStream stringStream = new ByteArrayInputStream( 
				in.getBytes(Charset.forName("UTF-8")) );
		
		Scanner readStrings = new Scanner(stringStream);
		
		while (readStrings.hasNext())
		{
			String next = readStrings.next();
			System.out.println(next);
			reversedLine.push(next);
		}
		
		readStrings.close();
		
		return reversedLine;
	}
}

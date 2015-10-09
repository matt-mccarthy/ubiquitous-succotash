import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class ReversalTest
{

	@Test
	public void test()
	{
		Stack<String> out = Reversal.reverseLine("Yo momma's so fat");
		
		while(!out.isEmpty())
			System.out.println(out.pop());
		
		fail();
	}

}

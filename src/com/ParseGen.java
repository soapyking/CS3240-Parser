import java.lang.ArrayIndexOutOfBoundsException;


public class ParseGen
{
	private static Lexer lex;
	private static Grammar grammar;
	private static LinkedList<Token> terminals;
	private static LinkedList<Token> nonTerminals;


		

	public void makeGrammar()
	{
		lex.getToken();

	}
	/**
	 * Going to try and do this as eloquantly as possible
	 * This program will be command line only and will consist of java 
	 * (file that contains the grammar definition) (where to save) (if last
	 * request was a file, this will have the name of the file in the current directory)
	 */
	public static void main(String [] args)
	{
		try
		{
			lex = new Lexer(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		lex.readFile();
		lex.getToken();
	}
}

import java.lang.ArrayIndexOutOfBoundsException;
import java.util.LinkedList;

public class ParseGen
{
	private static Lexer lex;
	private static Grammar grammar;
	private static LinkedList<Token> terminals;
	private static LinkedList<Token> nonTerminals;

		
	public ParseGen()
	{
		terminals = new LinkedList<Token>();
		nonTerminals = new LinkedList<Token>();
		grammar = new Grammar();
	}


	/**
	 * Creates a grammar from the lexer's tokens.
	 * Will make sure that the lexer has tokens before beginning.
	 */
	public void makeGrammar()
	{
		while(lex.hasTokens())
		{
			Token token=lex.getToken();
			if(token.getType() ==TokenType.TERMINAL)
			{
				terminals.add(token);
			}
			else if(token.getType() ==TokenType.NON_TERMINAL)
			{
				if(lex.nextToken().getType()==TokenType.ASSIGN)
				{
					Rule nextRule=new Rule(token);
					LinkedList<Token> right = new LinkedList<Token>();
					while((token=lex.getToken()).getType()!=TokenType.END_OF_RULE)
					{
						right.add(token);
					}
					nextRule.addRight_hs(right);
					grammar.add(nextRule);
				}
			}
		}
	}

	/**
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
		ParseGen parse = new ParseGen();
		parse.makeGrammar();
		grammar.separate();
		System.out.println(grammar);
		grammar.makeFirstSet();
	}
}

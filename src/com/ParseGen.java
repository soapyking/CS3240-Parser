import java.lang.ArrayIndexOutOfBoundsException;
import java.util.LinkedList;

public class ParseGen
{
	private static Lexer lex;
	private static Grammar grammars;
	private static LinkedList<Token> terminals;
	private static ParseTable parseTable;
	private static ParseTableWriter parseTableWriter;
	//private static LinkedList<Token> nonTerminals;
	//private static ParseTable parseTable;

		
	public ParseGen()
	{
		parseTable = new ParseTable();
		terminals = new LinkedList<Token>();
		//nonTerminals = new LinkedList<Token>();
		grammars = new Grammar();
	}


	/**
	 * Creates a grammar from the lexer's tokens.
	 * Will make sure that the lexer has tokens before beginning.
	 */
	public static Grammar makeGrammar()
	{
		Grammar grammar = new Grammar();
		boolean pastMeta=false;
		while(lex.hasTokens())
		{
			Token token=lex.getToken();
//			if(token.getTypeString().compareToIgnoreCase("terminal")==0)
//			{
//				terminals.add(token);
//			}
			try
			{
				if(token.getName().compareToIgnoreCase("%Rules")==0)
				{
					pastMeta=true;
					token=lex.getToken();
					token=lex.getToken();
				}
				if(pastMeta&&token.getTypeString().compareToIgnoreCase("nonterminal")==0)
				{
					//System.out.println(lex.nextToken().getName());
					if(lex.nextToken().getTypeString().compareToIgnoreCase("assign")==0)
					{
						Rule nextRule=new Rule(token);
						LinkedList<Token> right = new LinkedList<Token>();
						while((token=lex.getToken()).getType()!=TokenType.END_OF_RULE)
						{
							if(token.getTypeString().compareToIgnoreCase("assign")!=0)
							{
								right.add(token);
							}
						}
						nextRule.addRight_hs(right);
						grammar.add(nextRule);
					}
				}
			}
			catch(NullPointerException e)
			{
				
			}
		}
		return grammar;
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
			parseTableWriter = new ParseTableWriter(args[1]);
			lex = new Lexer(args[0]);
			System.out.println(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		lex.readFile();
		@SuppressWarnings("unused")
		ParseGen parserGenerator = new ParseGen();
		grammars=makeGrammar();
		grammars.removeRecursion();
		grammars.separate();
		//System.out.println(grammar);
		
		grammars.makeFirstSet();
		for(int i=0;i<grammars.countRules();i++)
		{
			System.out.println("What's going on?");
			System.out.println("and i step outside" + grammars.getRule(i).getLeftHS().getName());
			System.out.println(grammars.getRule(i).getLeftHS().getFirstSet()+"\n");
		}
		grammars.makeFollowSet();
		System.out.println("\n\n\n\n");
		for(int i=0;i<grammars.countRules();i++)
		{
			System.out.println("What's going on?");
			System.out.println("and i step outside" + grammars.getRule(i).getLeftHS().getName());
			System.out.println(grammars.getRule(i).getLeftHS().getFirstSet()+"\n");
		}
		parseTable.generateParseTable(grammars);
		System.out.println("\n\n\n\n");
		for(int i=0;i<grammars.countRules();i++)
		{
			System.out.println("What's going on?");
			System.out.println("and i step outside" + grammars.getRule(i).getLeftHS().getName());
			System.out.println(grammars.getRule(i).getLeftHS().getFirstSet()+"\n");
		}
		parseTableWriter.createFile(parseTable);
		//lex.getTokenWriter().makeFirstSet();
		//token.createFile(null);

	}
}

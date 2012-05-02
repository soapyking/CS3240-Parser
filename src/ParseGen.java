import java.lang.ArrayIndexOutOfBoundsException;
import java.util.LinkedList;
import java.io.*;

public class ParseGen
{
    private static Lexer lex;
    private static Grammar grammar;
    private static LinkedList<Token> terminals;
    private static ParseTable parseTable;
    private static ParseTableWriter parseTableWriter;

    public enum grammar_sm {
	init, err, token, start, rules_m, rules_lhs, rules_rhs;
    }


    public ParseGen()
    {
	parseTable = new ParseTable();
	terminals = new LinkedList<Token>();
	grammar = new Grammar();
    }


    /**
     * Creates a grammar from the lexer's tokens.
     * Will make sure that the lexer has tokens before beginning.
     */
    public static void makeGrammar(LinkedList<Token> llist)
    {
	boolean pastMeta=false;
	grammar_sm state = grammar_sm.init;

	LinkedList<Token> parseStack = new LinkedList<Token>();
	Token lhs = null, prev = null;

	for ( Token token : llist)
	    {
		if(token.type == TokenType.DOLLAR){
		    break;
		}

		if(token.type == TokenType.TERMINAL){
		    terminals.add(token);
		}
		try
		    {
			if(token.token_string.compareToIgnoreCase("%Rules")==0)
			    {
				state = grammar_sm.rules_m;
				continue;
			    }
			else if(state == grammar_sm.rules_m && token.type == TokenType.LINE_END)
			    {
				state = grammar_sm.rules_lhs;
				continue;
			    }
			else if(state == grammar_sm.rules_lhs || state == grammar_sm.rules_rhs )
			    {
				if(state == grammar_sm.rules_lhs)
				    {
					if( token.type == TokenType.ASSIGN)
					    {
						state = grammar_sm.rules_rhs;
						continue;
					    }
					else if( token.type == TokenType.NON_TERMINAL){
					    lhs = token;
					    continue;
					}
					else
					    {
						System.out.println("SHOULD NOT BE HERE... LHS not nonterminal. LHS -> " + lhs);
						break;
					    }
				    }
			    	else
				    if(token.type == TokenType.LINE_END)
					{
					    state = grammar_sm.rules_lhs;
					    lhs = grammar.searchGrammar(lhs);
					    Rule nextRule = new Rule(lhs);
					    for(int i=0;i<parseStack.size();i++)
						{
						    if(parseStack.get(i).getName().compareToIgnoreCase(lhs.getName())==0)
							{
							    parseStack.set(i,lhs);
							}
						}
					    nextRule.addRight_hs(parseStack);
					    grammar.add(nextRule);
					    parseStack = new LinkedList<Token>();
					    continue;
					}
				    else if (token.type == TokenType.RULE_SEP)
					{
					    if(lhs == null) System.out.println("prev" + prev);
					    lhs = grammar.searchGrammar(lhs);
					    Rule nextRule = new Rule(lhs);
					    for(int i=0;i<parseStack.size();i++)
						{
						    if(parseStack.get(i).getName().compareToIgnoreCase(lhs.getName())==0)
							{
							    parseStack.set(i,lhs);
							}
						}
					    nextRule.addRight_hs(parseStack);
					    grammar.add(nextRule);
					    parseStack = new LinkedList<Token>();
					    continue;
					}
				    else if(state == grammar_sm.rules_rhs)
					{
					    token = grammar.searchGrammar(token);
					    parseStack.add(token);
					}
			    }

			prev = token;
		    }
		catch(NullPointerException e){
		    System.out.println("NULL PTR");
		}
	    }

	LinkedList<Rule> temp = new LinkedList();
	for (Rule t: grammar.rules){
	    if (t.getRightHS() == null)
		temp.add(t);
	}
	for (Rule t: temp){
	    if (temp != null) grammar.rules.remove(t);
	}
    }


    /**
	Helper file, provides usage information to users about main method arguments.
    */
    public static void usage(){
	String str = "Nothing here now.";
	System.out.println(str);
    }


    /**
     * This program will be command line only and will consist of java
     * (file that contains the grammar definition) (where to save) (if last
     * request was a file, this will have the name of the file in the current directory)
     */
    public static void main(String [] args)
    {
	boolean verbose = false;
	// Lexer pulls double-duty as the lexical analyzer for the grammar file.
	lex = new Lexer(Lexer.LexerType.GRAMMAR, false);

	// Load the grammar file (first argument)
	try{
	    lex.tokenize(lex.readFile(new BufferedReader(new FileReader(args[0]))));
	}catch(IndexOutOfBoundsException e){
	    System.out.println("Missing file arguments.");
	    usage();
	    System.exit(1);
	}catch(Exception e){
	    System.out.println("Unexpected error. Perhaps check your call? Exiting.");
	    usage();

	    System.exit(1);
	}

	@SuppressWarnings("unused")
	    ParseGen parserGenerator = new ParseGen();
	// Generate the grammer from the source file/input
	makeGrammar(lex.ll_token_list);

	// Remove left recursion from the grammar.
	grammar.removeRecursion();

	// Left-factorize the grammar.
	grammar.leftFactor();

	// Make the first and follow sets for the grammar
	grammar.makeFirstSet();
	grammar.makeFollowSet();

	if (verbose){
	    for ( Rule r : grammar.rules )
		System.out.println(r);
	}

	// Generate the parsing table
	parseTable = new ParseTable();
	parseTable.generateParseTable(grammar);

	// Dump the table to disk.
	try{
	    parseTableWriter = new ParseTableWriter(args[1]);
	    parseTableWriter.createFile(parseTable);
	}catch(IndexOutOfBoundsException e){
	    System.out.println("Missing output file arguments.");
	    usage();
	    System.exit(1);
	}
    }
}

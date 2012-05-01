import java.lang.ArrayIndexOutOfBoundsException;
import java.util.LinkedList;
import java.io.*;

public class ParseGen
{
    //private static Lexer lex;
    private static source_lexer lex;
    private static Grammar grammar;
    private static LinkedList<Token> terminals;
    private static ParseTable parseTable;
    private static ParseTableWriter parseTableWriter;
    //private static LinkedList<Token> nonTerminals;
    //private static ParseTable parseTable;

    public enum grammar_sm {
	init, err, token, start, rules_m, rules_lhs, rules_rhs;
    }


    public ParseGen()
    {
	parseTable = new ParseTable();
	terminals = new LinkedList<Token>();
	//nonTerminals = new LinkedList<Token>();
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

	for ( Token token : llist){
	    if(token.type == TokenType.DOLLAR){
		break;
	    }

	    //System.out.println(token);
	    if(token.type == TokenType.TERMINAL){
		terminals.add(token);
	    }
	    try{
		if(token.token_string.compareToIgnoreCase("%Rules")==0){
		    state = grammar_sm.rules_m;
		    continue;
		}else if(state == grammar_sm.rules_m && token.type == TokenType.LINE_END){
		    state = grammar_sm.rules_lhs;
		    continue;
		}else if(state == grammar_sm.rules_lhs || state == grammar_sm.rules_rhs ){
		    if(state == grammar_sm.rules_lhs){

			if( token.type == TokenType.ASSIGN){
			    state = grammar_sm.rules_rhs;
			    continue;
			} else if( token.type == TokenType.NON_TERMINAL){
			    //System.out.println(token);
			    lhs = token;
			    continue;
			} else{
			    System.out.println("WTF? -> " + lhs);
			    break;
			}
		    }else
		    if(token.type == TokenType.LINE_END){
			if(lhs == null) System.out.println("prev" + prev);
			state = grammar_sm.rules_lhs;
			Rule nextRule = new Rule(lhs);
			nextRule.addRight_hs(parseStack);
			grammar.add(nextRule);
			parseStack = new LinkedList<Token>();
			continue;
		    } else if (token.type == TokenType.RULE_SEP){
			if(lhs == null) System.out.println("prev" + prev);
			Rule nextRule = new Rule(lhs);
			nextRule.addRight_hs(parseStack);
			grammar.add(nextRule);
			parseStack = new LinkedList<Token>();
			continue;
		    } else if(state == grammar_sm.rules_rhs){
			parseStack.add(token);
		    }
		}

		prev = token;
		}catch(NullPointerException e){
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
     * This program will be command line only and will consist of java
     * (file that contains the grammar definition) (where to save) (if last
     * request was a file, this will have the name of the file in the current directory)
     */
    public static void main(String [] args)
    {

	// try
	//     {
	// 	parseTableWriter = new ParseTableWriter(args[1]);
	// 	//lex = new Lexer(args[0]);
	// 	System.out.println(args[0]);
	//     }
	// catch(ArrayIndexOutOfBoundsException e)
	//     {
	// 	e.printStackTrace();
	//     }
	//lex.readFile();

	lex = new source_lexer(source_lexer.LexerType.GRAMMAR, false);

	try{
	    lex.tokenize(lex.readFile(new BufferedReader(new FileReader(args[0]))));
	}catch(Exception e){}

	//	System.out.println(lex);
	@SuppressWarnings("unused")
	    ParseGen parserGenerator = new ParseGen();
	makeGrammar(lex.ll_token_list);
	//System.out.println(grammar);
	grammar.separate();
	//System.out.println(grammar + "\n-----------\n");
	grammar.removeRecursion();
	//System.out.println(grammar);
	grammar.leftFactor();
	System.out.println(grammar);
	//grammar.makeFirstSet();
	//grammar.makeFollowSet();
	//parseTable.generateParseTable(grammar);
	//parseTableWriter.createFile(parseTable);
	//lex.getTokenWriter().makeFirstSet();
	//token.createFile(null);

    }
}

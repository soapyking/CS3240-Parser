import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.*;
import java.util.Dictionary;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.regexp.*;

public class source_lexer {

    private boolean verbose = false;

    public enum Token {

	// Terminals

	LEFTPAR     	("LEFTPAR"     , new RE("^" + "\\(")),
	RIGHTPAR    	("RIGHTPAR"    , new RE("^" + "\\)")),
	ASSIGN      	("ASSIGN"      , new RE("^" + ":=")),
	COMMA       	("COMMA"       , new RE("^" + ",")),
	SEMICOLON   	("SEMICOLON"   , new RE("^" + ";")),
	PLUS        	("PLUS"        , new RE("^" + "\\+")),
	MINUS       	("MINUS"       , new RE("^" + "-")),
	MULTIPLY    	("MULTIPLY"    , new RE("^" + "\\*")),
	MODULO      	("MODULO"      , new RE("^" + "%")),

	// Keywords
	BEGIN       	("BEGIN"       , new RE("^" + "BEGIN")),
	END         	("END"         , new RE("^" + "END")),
	PRINT       	("PRINT"       , new RE("^" + "PRINT")),

	// Non-implemented
	READ        	("READ"        , new RE("^" + "READ")),
	WRITE           ("WRITE"       , new RE("^" + "WRITE")),
	IF          	("IF"          , new RE("^" + "IF")),
	THEN        	("THEN"        , new RE("^" + "THEN")),
	UNTIL       	("UNTIL"       , new RE("^" + "UNTIL")),
	LESSTHAN    	("LESSTHAN"    , new RE("^" + "<")),
	GREATERTHAN 	("GREATERTHAN" , new RE("^" + ">")),
	REPEAT      	("REPEAT"      , new RE("^" + "REPEAT")),
	EQUALTO     	("EQUALTO"     , new RE("^" + "=")),


	// Char classes
	INTNUM      	("INTNUM"      , new RE("(^0)|(^[1-9][0-9]*)", RE.REPLACE_BACKREFERENCES)),
	ID          	("ID"          , new RE("^[_A-Za-z][_A-Za-z0-9]{0,9}"));

	private final String token;
	private final RE regex;
	Token(String token, RE regex){
	    this.token = token;
	    this.regex = regex;
	}

    }

    public source_lexer ( ) {
	this.verbose = false;
    }

    public source_lexer ( boolean verbose ) {
	this();
	this.setVerbose(verbose);

    }

    public void setVerbose( boolean verbose) {
	this.verbose = verbose;
    }

    /* Condition the input file for parsing. */
    private String condition( String input_string) {
	RE regex;
	/*
	  Remove any comments -- one of the example files uses comments,
	  and I just wanted to clear them out.
	 */
	regex = new RE("\\{.*?\\}");
	input_string = regex.subst(input_string,"",RE.MATCH_MULTILINE & RE.REPLACE_ALL);

	/*
	  Collapse whitespace.
	*/
	regex = new RE("[:space:]+");
	input_string = regex.subst(input_string," ",RE.MATCH_MULTILINE & RE.REPLACE_ALL);

	/*
	  Also replace newlines with spaces. No need for them.
	 */
	regex = new RE("(\\n)|(\\r\\n)|(\\r)");
	input_string = regex.subst(input_string," ",RE.REPLACE_ALL);

	return input_string;
    }

    private String tokenize( String input_string ) {
	String input = condition(input_string);
	String output_string = new String("");
	String inputln = Integer.toString(input.length());
	String chngeln = Integer.toString(15);
	boolean changed;

	do{
	    changed = false;
	    for (Token t : Token.values()){
		input = input.trim();
		t.regex.setMatchFlags(RE.MATCH_CASEINDEPENDENT);
		if (t.regex.match(input)){
		    if(this.verbose)
			System.out.printf("%" + inputln +"s | %-" + chngeln + "s  ==> ", input, t.token);
		    input = t.regex.subst(input, "", RE.REPLACE_FIRSTONLY & RE.MATCH_CASEINDEPENDENT);
		    output_string = output_string.trim() + " " + t.token.trim();
		    changed = true;
		    if(this.verbose) System.out.println(input);
		    break;
		}

	    }

	}while(changed && input.length() > 0);

	if (input.length() > 0){
	    System.out.println("Syntax error. \n");
	    return output_string + " SYNTAX_ERROR @ \"" + input + "\"";
	}

	if(this.verbose) System.out.println("\n");
	return output_string;

    }



    public static void main(String[] args){
	BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	BufferedReader file;

	boolean verbose_output = false;
	String output_file = null;
	String input_file = null;

	/*
	   Parse input arguments.
	 */

	String usage = "Usage: java -cp .:regexp.jar source_lexer -v(erbose output)" //
	    + " [-i inputfile (default stdin)] [-o outputfile (default stdout)]";


	for(int i = 0; i < args.length; i++){

	    /*
	      Input filename for the untokenized code.
	    */
	    if (args[i].indexOf("-i") == 0){
		if(args[i].length() > 0){
		    String tmp = args[i].substring(2,args[i].length());
		    if (tmp.length() == 0){
			try{
			    input_file = args[++i];
			    continue;
			}catch(ArrayIndexOutOfBoundsException e){
			    System.out.printf("Bad argument for '-i'. \n%s\n",usage);
			    System.exit(1);
			}
		    } else {
			System.out.printf("Unknown argument: %s. \n%s\n", args[i], usage);
			System.exit(1);
		    }
		}
	    }

	    /*
	      Output filename for the tokens.
	    */
	    if (args[i].indexOf("-o") == 0){
		if (args[i].length() > 0){
		    String tmp = args[i].substring(2,args[i].length());
		    if (tmp.length() == 0){
			try{
			    output_file = args[++i];
			    continue;
			}catch(ArrayIndexOutOfBoundsException e){
			    System.out.printf("Bad argument for '-o'. \n%s\n",usage);
			    System.exit(1);
			}
		    } else {
			System.out.printf("Unknown argument: %s. \n%s\n", args[i], usage);
			System.exit(1);
		    }


		}
	    }

	    /*
	      Enable verbose output, for debugging. Doesn't help much if you are
	      piping the output of the lexer around, or using stdout.
	    */
	    if (args[i].indexOf("-v") == 0){
		if (args[i].length() == "-v".length()){
		    verbose_output = true;
		    continue;
		} else {
		    System.out.printf("Unknown argument: %s. \n%s\n", args[i], usage);
		    System.exit(1);
		}
	    }

	    /*
	       Help message.
	    */
	    if (args[i].indexOf("-h") == 0){
		    System.out.printf("%s\n", usage);
		    System.exit(1);
	    }

	    System.out.printf("Unknown argument: %s. \n%s\n", args[i], usage);
	    System.exit(1);
	}

	/*
	  Use the parsed arguments to set up the source_lexer call...
	*/

	source_lexer output = new source_lexer(verbose_output);


	String lexer_input = "";

	if (input_file == null) {
	    String buffer;
	    try{
		while((buffer = console.readLine()) != null && buffer.length() != 0)
		    lexer_input += buffer;
	    } catch(IOException ioe){
		ioe.printStackTrace();
	    }
	} else {
	    String buffer;

	    try{
		file = new BufferedReader(new FileReader(input_file));
		while((buffer = file.readLine()) != null && buffer.length() != 0)
		    lexer_input += buffer;
	    } catch(IOException ioe){
		ioe.printStackTrace();
	    }
	}

	String lexer_output = output.tokenize(lexer_input);

	if(output_file == null){
	    if (verbose_output)
		System.out.println(lexer_input + "\n ==> \n" + lexer_output);
	    else
		System.out.println(lexer_output);
	}else {
	    if (verbose_output)
		System.out.println(lexer_input + "\n ==> \n" + lexer_output);
	    try{
		PrintWriter out = new PrintWriter(new FileWriter(output_file));
		out.println(lexer_output);
		out.close();
	    }catch(IOException ioe){
		ioe.printStackTrace();
	    }
	}




    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.*;
import java.util.Dictionary;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.regexp.*;

public class Lexer {

    private boolean verbose = false;

    public enum Token {

	// Terminals

	LEFTPAR     	("LEFTPAR"     , new RE("^" + "\\(")),
	RIGHTPAR    	("RIGHTPAR"    , new RE("^" + "\\)")),
	ASSIGN      	("ASSIGN"      , new RE("^" + "=")),
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
	LEFTBRA     	("LEFTBRA"     , new RE("^" + "\\{")),
	RIGHTBRA    	("RIGHTBRA"    , new RE("^" + "\\}")),
	READ        	("READ"        , new RE("^" + "READ")),
	IF          	("IF"          , new RE("^" + "IF")),
	THEN        	("THEN"        , new RE("^" + "THEN")),
	UNTIL       	("UNTIL"       , new RE("^" + "UNTIL")),
	LESSTHAN    	("LESSTHAN"    , new RE("^" + "<")),
	GREATERTHAN 	("GREATERTHAN" , new RE("^" + ">")),
	REPEAT      	("REPEAT"      , new RE("^" + "REPEAT")),
	EQUALTO     	("EQUALTO"     , new RE("^" + "=")),


	// Char classes
	INTNUM      	("INTNUM"      , new RE("^0|[1-9][0-9]*")),
	ID          	("ID"          , new RE("^[_A-Za-z][_A-Za-z0-9]{0,9}"));

	private final String token;
	private final RE regex;
	Token(String token, RE regex){
	    this.token = token;
	    this.regex = regex;
	}

    }

    public Lexer ( ) {
	this.verbose = false;
    }

    public Lexer ( boolean verbose ) {
	this();
	this.setVerbose(verbose);

    }

    public void setVerbose( boolean verbose) {
	this.verbose = verbose;
    }

    private String tokenize( String input_string ) {
	String input = new String(input_string);
	String output_string = new String("");
	String inputln = Integer.toString(input.length());
	boolean changed;

	do{
	    changed = false;
	    for (Token t : Token.values()){
		String old_input = new String(input);
		String[] split = t.regex.split(input);

		if(this.verbose)
		    System.out.printf("%" + inputln +"s | %s  ==> ", input, t.token, t.regex.toString());

		if ( split.length >= 0 && split[0].length() >= 0 && input.length() > split[0].length()){
		    input = new String(input.substring(split[0].length()+1,input.length()));
		    if (false == input.equals(old_input)){
			if(this.verbose)
			    System.out.printf("%s became %s\n", old_input, input);
			changed = true;
			output_string = output_string.trim() + " " + t.token.trim();
			break;
		    }
		}else{
		    if(this.verbose)
			System.out.println();
		}
	    }
	    if(this.verbose)
		System.out.println();
	}while(changed && input.length() > 0);



	return output_string;

    }

    public static void main(String[] args){
	System.out.println("\nStarting up the Lexer...");
	BufferedReader console = new BufferedReader(new InputStreamReader(System.in));


	boolean verbose_output = false;
	String output_file = null;
	String input_file = null;

	String usage = "Usage: java -cp .:regexp.jar Lexer -v(erbose output)" //
	    + " [-i inputfile (default stdin)] [-o outputfile (default stdout)]";

	for(int i = 0; i < args.length; i++){
	    if (args[i].indexOf("-i") == 0 && args[i].length() > 0){
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

	    if (args[i].indexOf("-o") == 0 && args[i].length() > 0){
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

	    if (args[i].indexOf("-v") == 0 && args[i].length() == "-v".length()){
		verbose_output = true;
		continue;
	    } else {
		    System.out.printf("Unknown argument: %s. \n%s\n", args[i], usage);
		    System.exit(1);
	    }

	    System.out.printf("Unknown argument: %s. \n%s\n", args[i], usage);
	    System.exit(1);
	}

	if (output_file != null){
	    verbose_output = false;
	}
	System.out.printf("verbose: %b, output: %s, input: %s\n", verbose_output, output_file, input_file);

	Lexer output = new Lexer(verbose_output);


	String tmp = "(3*n+xy)";

	System.out.println(tmp + " ==> " + output.tokenize(tmp));



    }
}

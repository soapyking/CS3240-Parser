import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.*;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.apache.regexp.*;

public class source_lexer {

    private boolean verbose = false;

    public LinkedList<Token> ll_token_list;

    public LexerType lextype;

    private TreeMap<Token,Rule> rules;

    public enum LexerType { SOURCE, GRAMMAR };

    public enum grammar_sm {
	init, err, token, start, rules_m, rules_lhs, rules_rhs;
    }

    public source_lexer ( LexerType type ) {
	this.lextype = type;
	this.ll_token_list = new LinkedList<Token>();
	this.verbose = false;
    }

    public source_lexer ( LexerType type, boolean verbose ) {
	this(type);
	this.setVerbose(verbose);
    }

    public void setVerbose( boolean verbose) {
	this.verbose = verbose;
    }

    public String tokenize( String input_string ) {
	String input = new String(input_string);
	String output_string = new String("");
	String inputln = Integer.toString(input.length());
	String chngeln = Integer.toString(15);
	boolean changed;
	RE regex;



	do{
	    changed = false;
	    if (this.lextype == LexerType.SOURCE){
		/*
		  Collapse whitespace.
		*/
		regex = new RE("[:space:]+");
		input = regex.subst(input_string," ",RE.MATCH_MULTILINE & RE.REPLACE_ALL);

		/*
		  Also replace newlines with spaces. No need for them.
		*/
		regex = new RE("(\\n)|(\\r\\n)|(\\r)");
		input = regex.subst(input," ",RE.REPLACE_ALL);

		/*
		    Extract the Source tokens, and the match strings.
		 */
		for (SourceToken t : SourceToken.values()){
		    input = input.trim();
		    t.regex.setMatchFlags(RE.MATCH_CASEINDEPENDENT);
		    if (t.regex.match(input)){
			String match_string = t.regex.getParen(0);
			 if(this.verbose)
			 	System.out.printf("%" + inputln +"s | %-" + chngeln + "s  ==> ", input, t.token);
			input = t.regex.subst(input, "", RE.REPLACE_FIRSTONLY & RE.MATCH_CASEINDEPENDENT);
			output_string = output_string.trim() + " " + t.token.trim();

			ll_token_list.add(new Token(t.token, match_string, t.type));
			changed = true;
			 if(this.verbose) System.out.println(t.regex.getParen(0));
			break;
		    }
		}

	    } else{
		/*
		  Grammar input file.
		 */
		for (GrammarToken t : GrammarToken.values()){
		    t.regex.setMatchFlags(RE.MATCH_CASEINDEPENDENT);
		    if (t.regex.match(input)){
			String match_string = t.regex.getParen(0);
			 if(this.verbose)
			 	System.out.printf("%" + inputln +"s | %-" + chngeln + "s  ==> ", input, t.token);
			input = t.regex.subst(input, "", RE.REPLACE_FIRSTONLY & RE.MATCH_CASEINDEPENDENT);
			output_string = output_string.trim() + " " + t.token.trim();

			ll_token_list.add(new Token(match_string.trim(), match_string.trim(), t.type));
			changed = true;
			 if(this.verbose) System.out.println(t.regex.getParen(0));
			break;
		    }
		}

	    }

	}while(changed && input.length() > 0);

	if (input.length() > 0){
	    String error_string = "";
	    error_string += "Syntax error. \n";
	    for ( Token t : ll_token_list ){
		error_string += t + "\n";
	    }
	    return error_string + "\nSYNTAX_ERROR @ \"" + input + "\"";
	}

	ll_token_list.add(new Token("$", "", TokenType.DOLLAR));

	if(this.verbose){
	    for( Token g : ll_token_list){
		System.out.println(g);
	    }
	}
	return output_string;

    }


    public static String readFile(BufferedReader file){

	String outfile = "", buffer;
	try{
	    while((buffer = file.readLine()) != null && buffer.length() != 0)
		    outfile += buffer + "\n";
	} catch(IOException ioe){
	    ioe.printStackTrace();
	}


	return outfile;


    }

    public String toString(){
	String str = "";
	for (Token t : ll_token_list){
	    str += t + "\n";
	}
	return str;
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

	source_lexer output = new source_lexer(LexerType.GRAMMAR, verbose_output);



	file = console;

	try{
	    if (input_file != null){
		file = new BufferedReader(new FileReader(input_file));
	    }
	}catch(Exception e){};

	String lexer_input = source_lexer.readFile(file);


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

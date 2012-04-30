import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.File;
import java.util.LinkedList;

public class Lexer {
	private File file;
	private TokenWriter tokenWriter;
	public LinkedList<Token> ll_token_list;

	public Lexer()
	{
		ll_token_list = new LinkedList<Token>();
		tokenWriter = new TokenWriter("/tmp/output.tik");
	}

	public Lexer(String filepath)
	{
		try
		{
			ll_token_list = new LinkedList<Token>();
			file = new File(filepath);
			tokenWriter = new TokenWriter("/tmp/output.tik");
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Reads in a file and then calls the getTokens method to add all of the tokens
	 * into the token queue.
	 */
	public void readFile()
	{
		try
		{
			FileReader tempread = new FileReader(file);
			BufferedReader bufread = new BufferedReader(tempread);
			while(bufread.ready())
			{
				String line = bufread.readLine();
				getTokens(line);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("fuck you!!!!!!!!\n Enter in a godddamned real file");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * This method will return the "tokens" from the string tokenized that are space delimited.
	 *
	 * @param toToken the string that is to be tokenized
	 */
	public void getTokens(String toToken)
	{
		StringTokenizer tokenizer = new StringTokenizer(toToken);
		int numtokens = tokenizer.countTokens();
		String tokens[]=toToken.split(" ");
		boolean token=true;
		//boolean hitRules=false;
		for(int i=0;i<numtokens;i++)
		{
			if(tokens[i].compareToIgnoreCase("%tokens")==0)
			{
				stringToToken(tokens[i],"meta");
			}
			else if(tokens[i].compareToIgnoreCase("%non-terminals")==0)
			{
				stringToToken(tokens[i],"meta");
			}
			else if(tokens[i].compareToIgnoreCase("%start")==0)
			{
				stringToToken(tokens[i],"meta");
			}
			else if(tokens[i].compareToIgnoreCase("%rules")==0)
			{
				stringToToken(tokens[i],"meta");
			}
			else if(tokens[i].charAt(0)=='<')
			{
				stringToToken(tokens[i],"nonterminal");
			}
			else if(tokens[i].compareToIgnoreCase(":")==0)
			{
				stringToToken(tokens[i],"assign");
			}
			else
			{
				stringToToken(tokens[i],"terminal");
			}

			/*String strToken=tokenizer.nextToken();


			else if(strToken.compareToIgnoreCase("%Start")==0)
			{
				stringToToken(strToken,"meta");
			}
			else if(strToken.compareToIgnoreCase("%rules")==0)
			{
				stringToToken(strToken,"meta");
				//hitRules=true;
			}
			else if(strToken.charAt(0)=='<')
			{
				stringToToken(strToken,"nonterminal");
			}
			else if(strToken.compareTo(":")==0)
			{
				stringToToken(strToken,"assign");
			}
			else if(token==true)
			{
				stringToToken(strToken,"terminal");
			}
			else if(token==false)
			{
				stringToToken(strToken,"nonterminal");
			}
			if(strToken.compareToIgnoreCase("%tokens")==0)
			{
				stringToToken(strToken,"meta");
			}
			else if(strToken.compareToIgnoreCase("%non-terminals")==0)
			{
				stringToToken(strToken,"meta");
				token=false;
			}*/
		}
		stringToToken("end","endofrule");

	}


	/**
	 * Changes the string "token" that is passed in and wraps it into the class Token.
	 * This method will add the token to the ll_token_list automatically.
	 *
	 * @param input 		The string that will be the token's description
	 * @param type			The string that is the type of the token
	 */
	private void stringToToken(String input, String type)
	{
		Token token=new Token(input,type);
		for( Token t : ll_token_list){
		    if ( t.equals(token) )
			return;
		}
		ll_token_list.add(token);

	}



	/**
	 * Returns the next token that is available.
	 *
	 * @return the next token that will be available to the parser
	 */
	public Token getToken()
	{
		if(ll_token_list.peek()!=null)
		{
			return ll_token_list.poll();
		}
		else
		{
			return null;
		}
	}


	/**
	 * Returns whether or not the Lexer has any tokens
	 *
	 * @return true if the lexer has more tokens
	 * 		   false if the lexer doesn't
	 */
	public boolean hasTokens()
	{
		if(ll_token_list.peek()!=null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public TokenWriter getTokenWriter()
	{
		return tokenWriter;
	}


	/**
	 * Returns the next token in the linked list without removing it from the linked list.
	 *
	 * @return the token that is next in the linked list.
	 */
	public Token nextToken()
	{
		if(hasTokens())
		{
			return ll_token_list.peek();
		}
		return null;
	}


	public String toString()
	{
		String returned = new String();
		for(int i=0;i<ll_token_list.size();i++)
		{
			returned+=ll_token_list.get(i);
			returned+="\n";
		}
		return returned;
	}

	public static void main(String [] args)
	{
		Lexer lex = new Lexer("../res/input.txt");
		lex.readFile();
		System.out.println(lex);
	}
}

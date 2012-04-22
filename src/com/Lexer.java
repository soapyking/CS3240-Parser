import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.File;
import java.util.LinkedList;

public class Lexer {
	File file;
	LinkedList<Token> ll_token_list;

	public Lexer()
	{
		ll_token_list = new LinkedList<Token>();
	}

	public Lexer(String filepath)
	{
		try
		{
			ll_token_list = new LinkedList<Token>();
			file = new File(filepath);
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
				System.out.println("line is: " + line);
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
		boolean token=true;
		for(int i=0;i<numtokens;i++)
		{
			String strToken=tokenizer.nextToken();
			System.out.println("i is: " + i + "\nstrToken is: " + strToken + ".\n");
			if(strToken.compareToIgnoreCase("%tokens")==0)
			{
				stringToToken(strToken,"meta");
			}
			else if(strToken.compareToIgnoreCase("%non-terminals")==0)
			{
				stringToToken(strToken,"meta");
				token=false;
			}
			else if(strToken.compareToIgnoreCase("%Start")==0)
			{
				stringToToken(strToken,"meta");
			}
			else if(strToken.compareToIgnoreCase("%rules")==0)
			{
				stringToToken(strToken,"meta");
			}
			else if(token==true)
			{
				stringToToken(strToken,"terminal");
			}
			else if(token==false)
			{
				stringToToken(strToken,"nonterminal");
			}
		}
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
		Token token=new Token(input,null,type);
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

	public static void main(String [] args)
	{
		Lexer lex = new Lexer("../../res/input.txt");
		lex.readFile();
		while(lex.hasTokens())
		{
			Token temp = lex.getToken();
			System.out.println(temp);
		}
	}
}

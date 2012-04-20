import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.nio.charset.Charset;
import java.io.File;
import java.util.LinkedList;

public class Lexer {
	FileReader tempread;
	BufferedReader bufread;
	Charset chars;
	File file;
	Token[] token_list;
	int token_list_index;
	LinkedList<Token> ll_token_list;

	public Lexer(String filepath)
	{
		try
		{
			ll_token_list = new LinkedList<Token>();
			file = new File(filepath);
			tempread = new FileReader(file);
			bufread = new BufferedReader(tempread);
			token_list = new Token[500];
			token_list_index=0;
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("fuck you!!!!!!!!\n Enter in a godddamned real file");
		}
	}

	public String readFile()
	{
		try
		{
			int i=0;
			while(bufread.ready())
			{
				String line = bufread.readLine();
				getTokens(line);
				System.out.println(line);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * This method will return the "tokens" from the string tokenized that are space delimited.
	 *
	 * @param tokenized the string that is to be tokenized
	 */
	public void getTokens(String tokenized)
	{
		StringTokenizer tokenizer = new StringTokenizer(tokenized);
		int numtokens = tokenizer.countTokens();
		String stringTokenArray[] = new String[numtokens];
		for(int i=0;i<numtokens;i++)
		{
			stringTokenArray[i]=tokenizer.nextToken();
		}
	}


	/**
	 * Changes the string "token" that is passed in and wraps it into the class Token.
	 * This method will add the token to the ll_token_list automatically.
	 *
	 * @param input 		The string that will be the token's description
	 * @param isTerminal	A boolean describing if the token is a terminal
	 */
	private void stringToToken(String input, boolean isTerminal)
	{
		Token token;
		if(isTerminal)
		{
			token = new Token(input,null,"terminal");
		}
		else
		{
			token = new Token(input,null,"nonterminal");
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

	public static void main(String [] args)
	{
		Lexer lex = new Lexer("../../res/input.txt");
		lex.readFile();
		Token temp=lex.getToken();
		System.out.println(temp);
		temp=lex.getToken();
		System.out.println(temp);
	}
}

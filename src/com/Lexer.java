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
	StringTokenizer tokenizer;
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

	public String readFile()//String filepath)
	{
		try
		{
			int i=0;
			while(bufread.ready())
			{
				String line = bufread.readLine();
				Token tokenizer[];
				tokenizer=getTokens(line);
				System.out.println(line);

				for(int j=0;j<tokenizer.length;j++)
				{
					//token_list[token_list_index]=tokenizer[j];
					ll_token_list.add(tokenizer[j]);
					token_list_index++;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private Token[] getNonTerminals(String line)
	{
		
		return null;
	}
	public Token[] getTokens(String tokenized)
	{
		tokenizer = new StringTokenizer(tokenized);
		int numtokens = tokenizer.countTokens();
		String stringTokenArray[] = new String[numtokens];
		for(int i=0;i<numtokens;i++)
		{
			stringTokenArray[i]=tokenizer.nextToken();
		}
		return stringToToken(stringTokenArray,false);
	}
	
	
	public Token[] stringToToken(String[] input, boolean isTerminal)
	{
		Token returnArray[] = new Token[input.length];
		for(int i=0;i<input.length;i++)
		{
			if(isTerminal)
			{
				returnArray[i] = new Token(input[i],null,"terminal");
			}
			else
			{
				returnArray[i] = new Token(input[i],null,"nonterminal");
			}
			
		}
		return returnArray;
	}
	
	public Token getToken()
	{
		return ll_token_list.poll();
	}
	public static void main(String [] args)
	{
		Lexer lex = new Lexer("../../input.txt");
		lex.readFile();
		lex.getToken()
		//lex.getTokens();
	}
}

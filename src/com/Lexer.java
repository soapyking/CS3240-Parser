import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.nio.charset.Charset;
import java.io.File;

public class Lexer {
  FileReader tempread;
  BufferedReader bufread;
  Charset chars;
  File file;
  Token[] token_list;
  StringTokenizer tokenizer;
  public Lexer(String filepath)
  {
    try
    {
      file = new File(filepath);
      tempread = new FileReader(file);
      bufread = new BufferedReader(tempread);
      token_list = new Token[500];
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
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings("unused")
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
  public static void main(String [] args)
  {
    Lexer lex = new Lexer("../..//input.txt");
    lex.readFile();
    //lex.getToken();
  }
}

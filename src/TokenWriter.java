import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class TokenWriter
{
	FileWriter tokenWrite;
	public TokenWriter(String file)
	{
		try
		{
			tokenWrite = new FileWriter(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void createFile(LinkedList<Rule> allTheRules)
	{
		String tokens = "%Tokens part";
		String nonTerm = "%Non-terminals part";
		String start = "%Start part";
		String rules = "%Rules part";
		for(int i=0;i<allTheRules.size();i++)
		{
			Token toAdd = allTheRules.get(i).getLeftHS().clone();
			if(toAdd.getTypeString().compareToIgnoreCase("terminal")==0)
			{
				tokens+=toAdd.getName();
				tokens+="\n";
			}
			else if(toAdd.getTypeString().compareToIgnoreCase("nonterminal")==0)
			{
				nonTerm += toAdd.getName();
				nonTerm += "\n";
			}
		}
		try
		{
			tokenWrite.write(tokens,0,12);
			tokenWrite.flush();
			tokenWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		TokenWriter token = new TokenWriter("/home/alex/output.tik");
		token.createFile(null);
	}
}

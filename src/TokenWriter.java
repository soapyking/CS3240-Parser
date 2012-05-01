import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class TokenWriter
{
	File file;
	FileWriter writer;
	BufferedWriter buff;
	public TokenWriter(String fileName)
	{
		file = new File(fileName);
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
			writer = new FileWriter(file);
			buff = new BufferedWriter(writer);
			buff.write(tokens);
			buff.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		TokenWriter token = new TokenWriter("output.tik");
		token.createFile(null);
	}
}

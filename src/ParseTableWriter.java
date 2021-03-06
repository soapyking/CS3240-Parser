import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ParseTableWriter {

	FileWriter parseTableWrite;
	
	public ParseTableWriter(String file)
	{
		try
		{
			parseTableWrite = new FileWriter(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void createFile(ParseTable parse)
	{
		String firstRow = new String();
		for(int i=0;i<parse.getNumRows()+1;i++)
		{
			firstRow += "row ";
			firstRow += i;
			firstRow += "\n";
			LinkedList<Rule> rules = parse.getTable().get(i);
			for(int j=0;j<rules.size();j++)
			{
				if(rules.get(j).getLeftHS()!=null)
				{
					firstRow+=rules.get(j).getLeftHS().getName();
					firstRow+=",";
				}
				if(rules.get(j).getRightHS()!=null)
				{
					firstRow+="->,";
					for(int k=0;k<rules.get(j).getRightHS().size();k++)
					{
						firstRow+=rules.get(j).getRightHS().get(k).getName();
					}
					firstRow+=",";
				}
			}
			firstRow+="\n";
		}
		try
		{
			System.out.println("firstRow: " + firstRow + "length" + firstRow.length());
			parseTableWrite.write(firstRow, 0,firstRow.length());
			parseTableWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

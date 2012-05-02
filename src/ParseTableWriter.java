import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ParseTableWriter {
    /**
       Writes the ParseTable to a file.
     */
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
	    String firstRow = parse.toString();
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

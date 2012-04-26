import java.util.LinkedList;

public class FirstSet
{
	private LinkedList<Token> set;
	public FirstSet()
	{
		set = new LinkedList<Token>();
	}

	public void add(Token toAdd)
	{
		if(toAdd.getTypeString().compareToIgnoreCase("terminal")==0)
		{
			this.set.add(toAdd);
		}
	}

	public LinkedList<Token> getSet()
	{
		return set;
	}

	public String toString()
	{
		String output=new String();
		for(int i=0;i<set.size();i++)
		{
			output+=set.get(i).toString();
		}
		return output;
	}
}

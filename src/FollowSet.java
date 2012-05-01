import java.util.LinkedList;

public class FollowSet
{
	private LinkedList<Token> set;
	public FollowSet()
	{
		set = new LinkedList<Token>();
	}

	/**
	 * Adds a token to the follow set.
	 * It checks to make sure that another token with the same name is not in
	 * this list before it adds.
	 * @param toAdd The token that is to be added
	 */
	public void add(Token toAdd)
	{
		boolean safeToAdd = true;
		for(int i=0;i<set.size();i++)
		{
			if(set.get(i).getName().compareToIgnoreCase(toAdd.getName())==0)
			{
				safeToAdd=false;
			}
		}
		if(safeToAdd)
		{
			this.set.add(toAdd);
		}
	}

	/**
	 * Wrapper of the last method in order to have an efficient and clean
	 * way to add an entire linked list of tokens to the follow set.
	 * @param toAdd The linked list of tokens that is to be added to the follow set.
	 */
	public void add(LinkedList<Token> toAdd)
	{
		for(int i=0;i<toAdd.size();i++)
		{
			boolean safeToAdd=true;
			for(int j=0;j<set.size();j++)
			{
				if(set.get(j).getName().compareToIgnoreCase(toAdd.get(i).getName())==0)
				{
					safeToAdd=false;
				}
			}
			if(safeToAdd)
			{
				set.add(toAdd.get(i));
			}
		}
	}

	public LinkedList<Token> getSet()
	{
		return set;
	}

	public Token get(int index)
	{
		if(index<set.size())
		{
			return set.get(index);
		}
		else
		{
			return set.get(set.size()-1);
		}
	}

	public String toString()
	{
		String output=new String();
		for(int i=0;i<set.size();i++)
		{
			output+=set.get(i).getName() + " ";
		}
		return output;
	}
}

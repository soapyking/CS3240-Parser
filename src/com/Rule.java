import java.util.LinkedList;

public class Rule
{
	private Token left_hs;
	private LinkedList<Token> right_hs;
	private boolean hasOr;

	public Rule(Token left_hs)
	{
		hasOr=false;
		this.left_hs=left_hs;
		right_hs=new LinkedList<Token>();
	}

	public boolean searchRules(String search)
	{
		for(int i=0;i<right_hs.size();i++)
		{
			if(right_hs.get(i).getAttribute().compareTo(search)==0)
			{
				return true;
			}
		}
		return false;
	}

	public Token getLeftHS()
	{
		return left_hs;
	}

	public LinkedList<Token> getRightHS()
	{
		return right_hs;
	}
	
	public void addRight_hs(LinkedList<Token> right)
	{
		right_hs=right;
	}

	public String toString()
	{
		String returned = new String();
		returned += left_hs;
		returned += " -> \n";
		for(int i=0;i<right_hs.size();i++)
		{
			returned+="          ";
			returned+= right_hs.get(i);
			returned+="\n";
		}
		returned += "\n";
		return returned;
	}
	
}

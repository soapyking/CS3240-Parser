import java.util.LinkedList;

public class Rule
{
	private Token left_hs;
	private LinkedList<Token> right_hs;

	public Rule(Token left_hs)
	{
		this.left_hs=left_hs;
		right_hs=null;
	}
	
	public Rule(Token left_hs,LinkedList<Token> right_hs)
	{
		this.left_hs=left_hs;
		this.right_hs=right_hs;
	}
	
	public boolean searchRightForName(String search)
	{
		for(int i=0;i<right_hs.size();i++)
		{
			if(right_hs.get(i).getName().compareTo(search)==0)
			{
				return true;
			}
		}
		return false;
	}

	public LinkedList<Token> chopOffAt(int index)
	{
		LinkedList<Token> chopped = new LinkedList<Token>();
		for(int i=index;i<right_hs.size();i++)
		{
			chopped.add(right_hs.get(index));
		}
		return chopped;
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
		this.right_hs=right;
	}
	
	public Rule clone()
	{
		Token left = new Token(this.left_hs.getName(),this.left_hs.getTypeString());
		LinkedList<Token> right  = new LinkedList<Token>();
		for(int i=0;i<this.right_hs.size();i++)
		{
			right.add(this.right_hs.get(i));
		}
		Rule returning = new Rule(left);
		returning.addRight_hs(right);
		return returning;
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

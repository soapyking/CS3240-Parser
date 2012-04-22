import java.util.LinkedList;

public class Rule
{
	Token left_hs;
	LinkedList<Token> right_hs;

	public Rule(Token left_hs)
	{
		this.left_hs=left_hs;
		right_hs=new LinkedList<Token>();
	}

	public Token getLeft_hs()
	{
		return left_hs;
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

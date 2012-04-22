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

	public getLeft_hs()
	{
		return left_hs;
	}
	
}

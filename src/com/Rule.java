import java.util.LinkedList;

public class Rule
{
	Token left_hs;
	LinkedList<Token> link;

	public Rule(Token left_hs)
	{
		this.left_hs=left_hs;
		link=new LinkedList<Token>();
	}
	
}

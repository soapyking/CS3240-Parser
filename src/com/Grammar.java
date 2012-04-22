import java.util.LinkedList;

public class Grammar
{
	
	private LinkedList<Rule> rules;



	public Grammar(LinkedList<Rule> rules)
	{
		this.rules = rules;
	}
	/**
	 * This method removes all immediate left recursion.
	 * Will check every rule in the grammar to be sure.
	 */
	public void RemoveRecur()
	{
		
	}

}

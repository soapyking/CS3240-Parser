import java.util.LinkedList;

public class Grammar
{
	
	private LinkedList<Rule> rules;

	public Grammar()
	{
		rules = new LinkedList<Rule>();
	}

	public Grammar(LinkedList<Rule> rules)
	{
		this.rules = rules;
	}

	/**
	 * Adds a rule to the grammar.
	 *
	 * @param rule The rule to add to the grammar.
	 */
	public void add(Rule rule)
	{
		rules.add(rule);
	}
	
	/**
	 * Returns how many rules there are in the grammar
	 *
	 * @return the number of rules there are in the grammar
	 */
	public int countRules()
	{
		return rules.size();
	}

	/**
	 * This method removes all immediate left recursion.
	 * Will check every rule in the grammar to be sure.
	 * Call this method before the seperate method.
	 */
	public void removeRecursion()
	{
		for(int i=0;i<countRules();i++)
		{
			Rule thisRule = rules.get(i);
			Token leftHS = thisRule.getLeftHS();
			LinkedList<Token> rightHS = thisRule.getRightHS();
			for(Token t: rightHS) {
				//TODO: This
			}
		}
	}


	public String toString()
	{
		String returned=new String();
		for(int i=0;i<countRules();i++)
		{
			returned+=rules.get(i);
		}
		return returned;
	}
}

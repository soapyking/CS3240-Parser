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


	public FollowSet makeFollowSet(Token nonterminal)
	{
		return null;	
	}

	public void makeFirstSet()
	{
		for(int i=0;i<countRules();i++)
		{
			Rule rule = rules.get(i);
			Token left = rule.getLeftHS();
			LinkedList<Token> rightHS = rule.getRightHS();
			for(int j=0;j<rightHS.size();j++)
			{
				Token Xi = rightHS.get(j);
				if(Xi.getTypeString().compareToIgnoreCase("terminal")==0)
				{
					left.getFirstSet().getSet().add(Xi.clone());
					System.out.println("Added token " + Xi + " to the first set of " + left);
				}
			}
			System.out.println(left.getFirstSet().getSet().toString());
		}
	}

	public String toString()
	{
		String returned=new String();
		for(int i=0;i<countRules();i++)
		{
			returned+=rules.get(i).toString();
		}
		return returned;
	}

	public Rule getRule(int index)
	{
		return rules.get(index);
	}


	public void separate()
	{
		LinkedList<Rule> newRules = new LinkedList<Rule>();
		while(!(rules.isEmpty()))
		{
			Rule removed = rules.poll().clone();
			Token leftHS = removed.getLeftHS().clone();
			LinkedList<Token> rightHS = removed.getRightHS();
			LinkedList<Token> newRight = new LinkedList<Token>();
			while(!(rightHS.isEmpty()))
			{
				Token removeToken = rightHS.poll().clone();
				if(removeToken.getName().compareTo("|")==0)
				{
					Rule toAdd = new Rule(leftHS,newRight);
					newRules.add(toAdd);
					newRight = new LinkedList<Token>();
				}
				else
				{
					newRight.add(removeToken);
					if(rightHS.isEmpty())
					{
						Rule toAdd = new Rule(leftHS,newRight);
						newRules.add(toAdd);
					}
				}
			}
		}
		rules = null;
		rules = newRules;
	}
}

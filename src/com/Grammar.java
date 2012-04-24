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
				if (t.compareTo(rightHS.getFirst())==0);
				//TODO: This
			}
		}
	}


	public FollowSet makeFollowSet(Token nonterminal)
	{
		return null;	
	}

	public FirstSet makeFirstSet(Token nonterminal)
	{
		for(int i=0;i<countRules();i++)
		{
			Rule rule = rules.get(i);
			Token token = rule.getLeftHS();
			//assert token.type==TokenType.NONTERMINAL; 
		}
		return null;
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
		for(int i=0;i<countRules();i++)
		{
			Rule rule = rules.get(i);
			LinkedList<Token> rightHS = rule.getRightHS();
			int size = rule.getRightHS().size();
			for(int j=0;j<size;j++)
			{
				LinkedList<Token> allTheTokens = new LinkedList<Token>();
				Token compare = rightHS.get(j);
				allTheTokens.add(compare);
				System.out.println("\n\n\n" + allTheTokens + " = all the tokens");
				if(compare.getName()=="|")
				{
					allTheTokens.removeLast();
					Rule newRule = new Rule(rule.getLeftHS());
					newRule.addRight_hs(allTheTokens);
					rules.add(i,newRule);
					i++;
					size++;
					allTheTokens = new LinkedList<Token>();
				}
			}
		}
	}
}

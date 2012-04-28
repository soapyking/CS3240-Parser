import java.util.LinkedList;

public class Grammar
{
	private LinkedList<Rule> rules;
	private LinkedList<Token> nonTerminals;
	//Used for determining unique nonterminals for use in the
	//first and follow set methods

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
	 * Call this method before the separate method.
	 */
	public void removeRecursion()
	{
		LinkedList<Rule> rulesCleaned = new LinkedList<Rule>();
		boolean recursionEncounter = false;
		String curName = null;
		String prevName = null;
		for(int i=0;i<countRules();i++)
		{
			Rule thisRule = rules.get(i);
			Token left = thisRule.getLeftHS();
			curName = left.getName();
			if(curName != prevName) {
				recursionEncounter = false;
				prevName = curName;
			}
			LinkedList<Token> right = thisRule.getRightHS();
			if (left.compareTo(right.getFirst()) == 0) {
				recursionEncounter = true;
				String leftRemName = left.getName() + "_rem";
				Token leftRem = new Token(leftRemName, left.getTypeString());
				LinkedList<Token> rightRem = new LinkedList<Token>();
				for(int j = 1; j < right.size(); j++) { //Deep copy Linked List, less the first element which is causing recursion
					rightRem.add(right.get(j));
				}
				rightRem.add(new Token(leftRem.getName(), left.getTypeString()));
				Rule ruleRem = new Rule(leftRem, rightRem);
				rulesCleaned.add(ruleRem);
				if(recursionEncounter) {
					for(Rule r: this.rules) {
						if(r.getLeftHS().compareTo(thisRule.getLeftHS()) == 0) {
							if(r.getRightHS().getLast().getName() != leftRemName) {
								r.getRightHS().add(new Token(leftRemName, left.getTypeString()));
							}
						}
					}
					for(Rule r: rulesCleaned) {
						if(r.getLeftHS().compareTo(thisRule.getLeftHS()) == 0) {
							if(r.getRightHS().getLast().getName() != leftRemName) {
								r.getRightHS().add(new Token(leftRemName, left.getTypeString()));
							}
						}
						if(r.getLeftHS().compareTo(thisRule.getLeftHS()) == 0) {
							if(r.getRightHS().getLast().getName() != leftRemName) {
								r.getRightHS().add(new Token(leftRemName, left.getTypeString()));
							}
						}
					}
				}
			}
			else {
				rulesCleaned.add(thisRule);
			}
		}
		rules = rulesCleaned;
	}


	/**
	 * This method populates the follow set of every nonterminal token in the 
	 * grammar.
	 */
	public void makeFollowSet()
	{
		for(int l=0;l<countRules();l++)
		{
			for(int i=0;i<countRules();i++)
			{
				Rule rule = rules.get(i);
				Token left = rule.getLeftHS();
				LinkedList<Token> rightHS = rule.getRightHS();
				if(i==0)
				{
					left.getFollowSet().add(new Token("dollar","dollar"));
				}
				for(int j=0;j<rightHS.size();j++)
				{
					Token Xi = rightHS.get(j);
					if(j==rightHS.size()-1)
					{
						if(left.getFollowSet()!=null)
						{
							Xi.getFollowSet().add(left.getFollowSet().getSet());
						}
					}
					else
					{
						for(int k=(j+1);k<(j+2);k++)
						{
							Token Xiplus1 = rightHS.get(k);
							if(Xiplus1.getTypeString().compareToIgnoreCase("nonterminal")== 0 &&
									Xiplus1.getTypeString()!="terminal")
							{
								Xi.getFollowSet().add(Xiplus1.getFirstSet().getSet());
							}
							else if(Xiplus1.getTypeString().compareToIgnoreCase("terminal")== 0)
							{
								Xi.getFollowSet().add(Xiplus1);
							}
						}
						
	//					if(Xi.getTypeString().compareToIgnoreCase("nonterminal")== 0)
	//					{
	//						if(Xi.getFirstSet()!=null)
	//						{
	//							left.getFollowSet().add(Xi.getFirstSet().getSet());
	//						}
	//					}
	//					if(Xi.getTypeString().compareToIgnoreCase("terminal")==0)
	//					{
	//						
	//						left.getFollowSet().add(Xi);
	//					}
					}
				}
			}
		}
	}

	/**
	 * This method populates the first set of every non terminal token in
	 * the grammar.
	 */
	public void makeFirstSet()
	{
		for(int k=0;k<countRules();k++)
		{
			for(int i=0;i<countRules();i++)
			{
				Rule rule = rules.get(i);
				Token left = rule.getLeftHS();
				LinkedList<Token> rightHS = rule.getRightHS();
				Token Xi = rightHS.get(0);
				if(Xi.getTypeString().compareToIgnoreCase("terminal")==0)
				{
					if(left.getFirstSet()!=null)
					{
						left.getFirstSet().add(Xi.clone());
					}
				}
				else if(Xi.getTypeString().compareToIgnoreCase("nonterminal")==0)
				{
					if(left.getFirstSet()!=null)
					{
						left.getFirstSet().add(Xi.getFirstSet().getSet());
					}
				}
			}
		}
	}
	
	public void printFirstSet()
	{
		for(int i=0;i<rules.size();i++)
		{
			if(rules.get(i).getLeftHS()!=null)
			{
				System.out.println(rules.get(i).getLeftHS().toString() + " = { \n");
			}
			if(rules.get(i).getLeftHS().getFirstSet()!=null)
			{
				System.out.println(rules.get(i).getLeftHS().getFirstSet().toString());
			}

			System.out.println("}\n");
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
		Rule returned = rules.get(index);
		return returned;
	}

	/**
	 * It separates every grammar rule that has a right hand side with an | in
	 * it.  It makes a separate rule for every | that it encounters.
	 */
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

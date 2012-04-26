package com;
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
							}						if(r.getLeftHS().compareTo(thisRule.getLeftHS()) == 0) {
								if(r.getRightHS().getLast().getName() != leftRemName) {
									r.getRightHS().add(new Token(leftRemName, left.getTypeString()));
								}
							}
						}
					}
				}
			}
		}
		rules = rulesCleaned;
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

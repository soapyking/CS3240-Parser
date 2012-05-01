import java.util.LinkedList;
import java.util.HashMap;
import java.lang.Math.*;

public class Grammar
{
    public LinkedList<Rule> rules;
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
	 * Call this method after the separate method.
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
			//System.out.printf("%20s :%s: %-20s\n", left.getName(), left.compareTo(right.getFirst()), right.getFirst().getName());

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

    private class Pair {

	Rule r1;
	Rule r2;

	public Pair(Rule r1, Rule r2){
	    this.r1 = r1;
	    this.r2 = r2;
	}

	// public String llstr(Rule ll){
	//     String rstr = "";
	//     for(Token t : ll)
	// 	rstr += t.getName().trim() + " ";
	//     return rstr.trim();
	// }

	public LinkedList<Token> getPrefix(Rule a, Rule b){
		return Grammar.getPrefix(a.getRightHS(), b.getRightHS());
	}

	public LinkedList<Token> getPrefix(){
	    if (r1 == null || r2 == null)
		return null;
	    try{
	    return this.getPrefix(r1,r2);
	    } catch(NullPointerException e)
		{ return null; }
	}

	public String toString(){
	    String r1str = "", r2str = "";
		r1str = r1.toString().trim() + " ";
		r2str = r2.toString().trim() + " ";

	    return String.format("A> %s\nB> %s", r1str.trim(), r2str.trim());
	}

    }

    public static LinkedList<Token> getPrefix(LinkedList<Token> A, LinkedList<Token> B){
	LinkedList<Token> prefix = new LinkedList<Token>();

	for(int i = 0; i < Math.min(A.size(), B.size()); i++){
	    if(A.get(i).equals(B.get(i))){
		prefix.add(A.get(i));
		continue;
	    }
	    break;
	}
	return prefix;
    }


    public static LinkedList<Token> getPostfix(LinkedList<Token> alpha,  LinkedList<Token> A){
	LinkedList<Token> out = new LinkedList<Token>();
	for( Token t : A)
	    out.add(t);

	for ( int i = 0; i < Math.min(alpha.size(),out.size()); i++){
	    //	    System.out.println(alpha + "\n < ALPHA OUT > " +  out);
	    if (alpha.get(i).getName().equals(out.get(i).getName())){
		out.remove(i);
	    }
	    else{
		break;
	    }
	}
	//	System.out.println(out.size());

	return out;
    }

    public void leftFactor() {
	boolean changed;

	do{
	LinkedList<LinkedList<Rule>> categorized = categorizeRules();
	LinkedList<Rule> conflictProduction = new LinkedList<Rule>();
	LinkedList<Token> alpha = new LinkedList<Token>();
	LinkedList<Token> beta;

	//	System.out.println("TOP_LOOP");
	changed = false;


	    ntA_production_check:
	    for(LinkedList<Rule> ntA_productions: categorized) {
		for(int i = 0; i < ntA_productions.size(); i++){
		    for( int j = i; j < ntA_productions.size(); j++ ){
			Pair test = new Pair(ntA_productions.get(i), ntA_productions.get(j));
			LinkedList<Token> tmp = test.getPrefix();
			if (tmp != null && tmp.size() < alpha.size() || test.r1.equals(test.r2)){
			    continue;
			}else{
			    alpha = test.getPrefix();
			    conflictProduction = ntA_productions;
			}
		    }
		}
	    }

	    if (alpha.size() > 0 ){

		LinkedList<Rule> new_rules = new LinkedList<Rule>();
		//System.out.println("\nAlpha: " + listToStr(alpha));
		Token A_prime = null;


		for ( Rule production : conflictProduction ){
		    if( production == null ) {
			break;
		    }
		    //System.out.println(production);
		    beta = getPostfix(alpha, production.getRightHS());
		    if ( beta.size() < production.getRightHS().size() ){

			//System.out.println("Beta: " + listToStr(beta));
			if( beta.size() <= 1 ){
			    continue;
			}

			String AstrName = conflictProduction.get(0).getLeftHS().getName() + "_lf";

			A_prime = new Token(AstrName, AstrName,conflictProduction.get(0).getLeftHS().type);

			LinkedList<Token> alpha_prime = new LinkedList<Token>();
			for ( Token a : alpha ){
			    alpha_prime.add(a.clone());
			}

			alpha_prime.add(A_prime);
			new_rules.add(new Rule(A_prime,beta));

			if(changed == false){
			    production.getRightHS().clear();
			    production.getRightHS().addAll(alpha_prime);
			}else{
			    this.rules.remove(production);
			}
			changed = true;

		    }

		}
		if ( changed ) {
		    for ( Rule r : new_rules){
			this.rules.add(r);

		    }
		}

		//System.out.println("\n" + new_rules);
	    }
	}while(changed);
    }

    public String listToStr(LinkedList<Token> l){
	String str = "";
	for( Token i : l ){
	    if( i == null || i.getName() == null)
		continue;
	    str += i.getName().toString() + " ";
	}
	return str;
    }
	/**
	 * Divides rules into a linked list of linked lists by their left tokens
	 * This assumes the original list of rules are ordered
	 * If I had thought of it, I would have used this for removeRecursion()
	 *
	 * @return
	 */
	public LinkedList<LinkedList<Rule>> categorizeRules() {
		LinkedList<LinkedList<Rule>> separated = new LinkedList<LinkedList<Rule>>();
		LinkedList<Rule> curList = new LinkedList<Rule>();
		String curName = null;
		String prevName = null;
		curName = rules.get(0).getLeftHS().getName();
		prevName = curName;
		for(Rule r: rules) {
			curName = r.getLeftHS().getName();
			if(prevName.compareTo(curName) == 0) {
			    //				System.out.println("Match on " + curName);
				curList.add(r);
			}
			else {
			    //				System.out.println("Mismatch, now categorizing " + curName);
				separated.add(curList);
				curList = new LinkedList<Rule>();
				curList.add(r);
			}
			prevName = curName;
		}
		return separated;
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

	public void printFollowSet()
	{
		for(int i=0;i<rules.size();i++)
		{
			if(rules.get(i).getLeftHS()!=null)
			{
				System.out.println(rules.get(i).getLeftHS().toString() + " = { \n");
			}
			if(rules.get(i).getLeftHS().getFollowSet()!=null)
			{
				System.out.println(rules.get(i).getLeftHS().getFollowSet().toString());
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
	    return;
	    /*
		LinkedList<Rule> newRules = new LinkedList<Rule>();
		while(!(rules.isEmpty()))
		{
		    System.out.println("Separating...");
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
	    */
	}
}

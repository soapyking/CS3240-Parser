import java.util.*;
import org.apache.commons.collections.map.MultiKeyMap;

public class ParseTable {

	/**
	 * This class handles the parse table for the program.
	 * It is implemented with a 2d linked list where the
	 * "outer" linked list holds a linked list which represents a row in
	 * the parse table.  The first slot in the outer linked list will hold the
	 * top of the linked list, which will have rules for the columns.
	 * To signify which terminal and non terminal label will be applied on each
	 * column and row, the rule will only have a left hand side that is the
	 * token for which a usual row or column will be labeled.
	 */
	private LinkedList<LinkedList<Rule>> table;
	private int numColumns;
	private int numRows;

	public ParseTable()
	{
		table = new LinkedList<LinkedList<Rule>>();
		numColumns = 0;
		numRows=0;
	}

	private void setupParseTable(Grammar grammar)
	{
		table.add(new LinkedList<Rule>());
		table.get(0).add(new Rule(null));
		//initialize the first row
		for(int i=0;i<grammar.countRules();i++)
		{
			Rule rule = grammar.getRule(i);
			for(int j=0;j<rule.getRightHS().size();j++)
			{
				Token holder = rule.getRightHS().get(j);
				if(isUnique(holder)&&(holder.getTypeString().compareToIgnoreCase("terminal")==0))
				{
					table.get(0).add(new Rule(holder));
					numColumns++;
				}
			}
		}

		for(int i=0;i<grammar.countRules();i++)
		{
			Rule rule = grammar.getRule(i);
			if(isUnique(rule))
			{
				table.add(new LinkedList<Rule>());
				table.getLast().add(new Rule(rule.getLeftHS()));
				numRows++;
			}
			for(int j=0;j<rule.getRightHS().size();j++)
			{
				Token temp = rule.getRightHS().get(j);
				if((temp.getTypeString().compareToIgnoreCase("nonterminal"))==0&&
						(isUniqueNonTerminal(temp)))
				{
					table.add(new LinkedList<Rule>());
					table.getLast().add(new Rule(temp));
					numRows++;
				}
			}
		}

		for(int i=1;i<numRows-1;i++)
		{
			for(int j=0;j<numColumns-1;j++)
			{
				table.get(i).add(new Rule(null));
			}
		}
	}

	private boolean isUnique(Rule toTest)
	{
		boolean add = true;
		for(int i=0;i<table.size();i++)
		{
			if(table.get(i)!=null&&table.get(i).get(0).getLeftHS()!=null)
			{
				String compareTo = table.get(i).get(0).getLeftHS().getName();
				if(compareTo.compareToIgnoreCase(toTest.getLeftHS().getName())==0)
				{
					return false;
				}
			}
		}
		return add;
	}
	private boolean isUniqueNonTerminal(Token toTest)
	{
		boolean isUnique = true;
		for(int i=0;i<table.size();i++)
		{
			if(table.get(i)!=null && (table.get(i).get(0).getLeftHS()!=null))
			{
				String compareTo = table.get(i).get(0).getLeftHS().getName();
				if(compareTo.compareToIgnoreCase(toTest.getName())==0)
				{
					return false;
				}
			}
		}
		return isUnique;
	}
	private boolean isUnique(Token toTest)
	{
		boolean add = true;
		for(int i=0;i<table.get(0).size();i++)
		{
			if(table.get(0).get(i).getLeftHS()!=null)
			{
				if(table.get(0).get(i).getLeftHS().getName().compareToIgnoreCase(toTest.getName())==0)
				{
					add=false;
					break;
				}
			}
		}
		return add;
	}
	// public void generateParseTable(Grammar grammar)
	// {
	// 	setupParseTable(grammar);
	// 	System.out.println("\n\n\n\n");
	// 	for(int i=0;i<grammar.countRules();i++)
	// 	{
	// 		Rule rule = grammar.getRule(i);
	// 		for(int j=0;j<rule.getRightHS().size();j++)
	// 		{
	// 			System.out.println("What's going on? im in the method");
	// 			System.out.println("and i step outside" + grammar.getRule(i).getLeftHS().getName());
	// 			System.out.println(grammar.getRule(i).getLeftHS().getFirstSet()+"\n");
	// 		}
	// 	}
	// 	for(int i=0;i<grammar.countRules();i++)
	// 	{
	// 		Rule rule = grammar.getRule(i);
	// 		for(int j=0;j<rule.getRightHS().size();j++)
	// 		{
	// 			Token tempToken = rule.getRightHS().get(j);
	// 			if(tempToken.getTypeString().compareToIgnoreCase("nonterminal")==0)
	// 			{
	// 				FirstSet checkFirst = tempToken.getFirstSet();
	// 				for(int k=0;k<checkFirst.getSet().size();k++)
	// 				{
	// 					Token tokeninFirstSet = checkFirst.get(k);
	// 					add(new Rule(rule.getLeftHS().clone()),new Rule(tokeninFirstSet),rule);
	// 				}
	// 			}
	// 		}
	// 	}
	// }

    private class Pair < K extends Comparable<K> > implements Comparable< Pair<K> > {
	K M;
	K N;

	public Pair ( K M, K N) {
	    this.M = M;
	    this.N = N;
	}

	public int compareTo( Pair<K> j) {
	    if(M.compareTo(j.M) == 0 && N.compareTo(j.N) == 0)
		return 0;
	    else if(M.compareTo(j.M) == 0)
		return N.compareTo(j.N);
	    else
		return M.compareTo(j.M);
	}
    }
    public void generateParseTable(Grammar grammar){ //, LinkedList<Token> terminals, LinkedList<Token> nonterminals){
	MultiKeyMap parseTable = new MultiKeyMap();
	//TreeMap<Pair<String>, LinkedList<Rule>> parseTable = new TreeMap<Pair<String>, LinkedList<Rule>>();
	//TreeMap<String, Pair<String>> tkeys = new TreeMap<String, Pair<String>>();
	// TreeMap<String, Pair<String>> nkeys = new TreeMap<String, Pair<String>>();
	TreeMap<String, LinkedList<Rule>> productions = new TreeMap<String, LinkedList<Rule>>();

	LinkedList<Token> terminals = new LinkedList<Token>(), nonterminals = new LinkedList<Token>();

	for ( Rule r : grammar.rules ) {
	    nonterminals.add(r.getLeftHS());
	    for ( Token t : r.getRightHS() ){
		    if (t.getType() == TokenType.TERMINAL)
			terminals.add(t);
		    else
			nonterminals.add(t);
	    }
	}

	terminals.add(new Token("dollar", TokenType.DOLLAR.toString()));

	LinkedList<String> nonterminals_k = deduplicate_key(nonterminals);
	LinkedList<String> terminals_k = deduplicate_key(terminals);

	// Create empty table containing all M,N
	for ( String T : terminals_k ){
	    for ( String N : nonterminals_k ){
		//Pair<String> key = new Pair<String>(T,N);
		//tkeys.put(T, key);
		//nkeys.put(N, key);
		//parseTable.put(key, new LinkedList<Rule>());
		parseTable.put(N,T, new LinkedList<Rule>());
		//System.out.println("" + N + " " + key.equals(nkeys.get(N)) + " " + key.equals(nkeys.get(new String(N) + "")));
	    }
	}

	// Create second HashMap that contains all the rules for a given non-terminal
	for( String key : nonterminals_k ) {
	    for( Rule nt : grammar.rules){
		if ( key.equals(nt.getLeftHS().getName())){
		    if(productions.containsKey(key)){
			productions.get(key).add(nt);
		    }else{
			LinkedList<Rule> r = new LinkedList<Rule>();
			r.add(nt);
			productions.put(key,r);
		    }
		}
	    }
	}

	// Begin Alg on Louden 178 for generating parsing table
	for( String key : nonterminals_k ) {
	    System.out.println(key.trim());
	    for ( Rule p : productions.get(key)) {

		System.out.println("###############\n###############\n" + p.toString().trim());
		for ( Token alpha : p.getRightHS() ) {
		    //alpha.getFirstSet().add(new Token("EPSILON", TokenType.TERMINAL.toString()));
		    System.out.println("-----------\nAlpha: " + alpha.getName() + "\nAlpha.first: " + alpha.getFirstSet() + "\nAlpha.follow: " + alpha.getFollowSet());

		    // For every production A-> a_1 | a_2 | ... | a_n, add First(a_i) to Map[A,a].
		    for (Token a : alpha.getFirstSet().getSet()){
			Pair<String> cell;
			if (a.type == TokenType.NON_TERMINAL){
			    continue;
			}
			if (!a.getName().equals("EPSILON")){


			    // if(a.type == TokenType.TERMINAL){
			    // 	cell = tkeys.get(a.getName());
			    // } else if (a.type == TokenType.NON_TERMINAL){
			    // 	cell = nkeys.get(a.getName());
			    // } else {
			    // 	System.out.println("################################################################################ WTF??");
			    // 	continue;
			    // }
			    // parseTable.get(cell).add(p);
			    ((LinkedList<Rule>)parseTable.get(p.getLeftHS().getName(),a.getName())).add(p);
			    System.out.println("Current Cell -> M[" + p.getLeftHS().getName() + "," + a.getName() + "] := " + parseTable.get(p.getLeftHS().getName(), a.getName()).toString().trim());
			    //System.out.println("Current Cell -> M[" + cell.M + "," + cell.N + "] := " + parseTable.get(cell));

			}
			// If epsilon is in First(alpha), for each element a of Follow(A) (a token or $), add A -> alpha to M[A,alpha]
			if(a.getName().equals("EPSILON")){
			    System.out.println("<< IN LL(1) Rule 2");
			    for ( Token f : alpha.getFollowSet().getSet()){
			    // 	if( f.type == TokenType.TERMINAL){
			    // 	    cell = tkeys.get(f.getName());
			    // 	} else if (f.type == TokenType.NON_TERMINAL){
			    // 	    cell = nkeys.get(f.getName());
			    // 	} else {
			    // 	    continue;
			    // 	}
			    // 	parseTable.get(cell).add(p);
				((LinkedList<Rule>)parseTable.get(p.getLeftHS().getName(),f.getName())).add(p);
				System.out.println("Current Cell -> M[" + p.getLeftHS().getName() + "," + f.getName() + "] := " + parseTable.get(p.getLeftHS().getName(), f.getName()).toString().trim());
				//System.out.println("\tCurrent Cell -> M[" + cell.M + "," + cell.N + "] := " + parseTable.get(cell));
			    }
			}
		    }
		}
	    }
	}

	this.table = new LinkedList();
	this.table.add(new LinkedList());

    }

    public LinkedList<String> deduplicate_key(LinkedList<Token> tokens){
	LinkedList<String> deduplicated = new LinkedList<String>();
	outloop:
	for (Token t : tokens ) {
	    for (String s : deduplicated) {
		if (s.equals(t.getName().trim()))
		    continue outloop;
	    }
	    deduplicated.add(t.getName().trim());


	}

	return deduplicated;
    }

	public void add(Rule row, Rule column, Rule toAdd)
	{
		String nonterminal = row.getLeftHS().getName();
		String terminal = column.getLeftHS().getName();
		int cordCol=0;
		int cordRow=0;
		for(int i=1;i<numColumns;i++)
		{
			LinkedList<Rule> list = table.get(0);
			if(list.get(i).getLeftHS().getName().compareToIgnoreCase(terminal)==0)
			{
				cordCol = i;
				break;
			}
		}
		for(int i=1;i<numRows;i++)
		{
			Rule rule = table.get(i).get(0);
			if(rule.getLeftHS().getName().compareToIgnoreCase(nonterminal)==0)
			{
				cordRow = i;
			}
		}
		table.get(cordRow).set(cordCol,toAdd);
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getNumRows() {
		return numRows;
	}

	public LinkedList<LinkedList<Rule>> getTable() {
		return table;
	}



}

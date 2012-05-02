import java.util.*;
import org.apache.commons.collections.map.MultiKeyMap;

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
public class ParseTable {

	/** NOT USED **/
	private LinkedList<LinkedList<Rule>> table;
	
    private MultiKeyMap parseTable = null;
    private Set<String> nonterm_keys = null;
    private Set<String> term_keys = null;
	private int numColumns;
	private int numRows;

    public ParseTable()
    {
	table = new LinkedList<LinkedList<Rule>>();
	numColumns = 0;
	numRows=0;
    }

    public ParseTable(String csv){
	this();
	this.parseTableFromCSV(csv);
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

    /**
       Generates a parsing table based on the grammar.
     */
    public void generateParseTable(Grammar grammar){ //, LinkedList<Token> terminals, LinkedList<Token> nonterminals){
	// A multiple key map, just how it sounds.
	MultiKeyMap parseTable = new MultiKeyMap();

	// Workaround for lack of comparison-based MultiKeyMap. Use a
	// comparison-based map for the keys, and index it on the non-
	// key objects (different-instance strings that are 'equal' according to String.compareTo(String)).

	TreeMap<String, String> tkeys = new TreeMap<String, String>();
	TreeMap<String, String> nkeys = new TreeMap<String, String>();

	// List of all the productions, indexed by non-terminal.
	TreeMap<String, LinkedList<Rule>> productions = new TreeMap<String, LinkedList<Rule>>();

	// Two linked lists of tokens and nonterminals
	LinkedList<Token> terminals = new LinkedList<Token>(), nonterminals = new LinkedList<Token>();

	// Popluate the token/nonterminal lists
	for ( Rule r : grammar.rules ) {
	    nonterminals.add(r.getLeftHS());
	    for ( Token t : r.getRightHS() ){
		    if (t.getType() == TokenType.TERMINAL)
			terminals.add(t);
		    else
			nonterminals.add(t);
	    }
	}

	// Implicit termination of input
	terminals.add(new Token("dollar", TokenType.DOLLAR.toString()));

	// Deduplicate the linked lists.
	LinkedList<String> nonterminals_k = deduplicate_key(nonterminals);
	LinkedList<String> terminals_k = deduplicate_key(terminals);

	// Create empty table containing all M,N
	for ( String T : terminals_k ){
	    for ( String N : nonterminals_k ){
		nkeys.put(N, N);
		tkeys.put(T, T);
		parseTable.put(N,T, new LinkedList<Rule>());
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
	    for ( Rule p : productions.get(key)) {
		for ( Token alpha : p.getRightHS() ) {
		    // For every production A-> a_1 | a_2 | ... | a_n,
		    // add First(a_i) to Map[A,a].
		    for (Token a : alpha.getFirstSet().getSet()){
			if (a.type == TokenType.NON_TERMINAL){
			    continue;
			}
			if (!a.getName().equals("EPSILON")){
			    ((LinkedList<Rule>)parseTable.get(nkeys.get(p.getLeftHS().getName()),tkeys.get(a.getName()))).add(p);
			}
			// If epsilon is in First(alpha), for each
			// element a of Follow(A) (a token or $), add A
			// -> alpha to M[A,alpha]
			if(a.getName().equals("EPSILON")){
			    for ( Token f : alpha.getFollowSet().getSet()){
				((LinkedList<Rule>)parseTable.get(nkeys.get(p.getLeftHS().getName()),tkeys.get(f.getName()))).add(p);
			    }
			}
		    }
		}
	    }
	}

	// Save the parse table as an instance variable, as well as the keySets for the MultiKeyMap.
	this.parseTable = parseTable;
	this.nonterm_keys = nkeys.keySet();
	this.term_keys = tkeys.keySet();
	System.out.println(mapToCsv(parseTable, nkeys.keySet(), tkeys.keySet()));

	// Old parser table data structures. Here because it keeps
	// things from breaking, may be removed later if whatever
	// depends on it stops whining... =D

	this.table = new LinkedList();
	this.table.add(new LinkedList());
    }

    /**
       toString() wrapper of the mapToCsv() private method. Transforms
       the parse table into a CSV file for ease of everything.
    */
    public String toString() {
	if ( this.parseTable == null || this.nonterm_keys == null || this.term_keys == null ){
	    return "";
	}
	return this.mapToCsv(this.parseTable, this.nonterm_keys, this.term_keys);
    }

    /**
       Outputs a CSV-formatted string representation of the parser table.
    */
    private String mapToCsv(MultiKeyMap m , Set<String> nonterm, Set<String> term){
	String csv = ",";

	for( String t : term ) {
	    csv += t + ",";
	}
	csv += "\n";
	for( String nt : nonterm ) {
	    csv += nt + ",";
	    for( String t : term ) {
		String ruleString = "";
		for ( Rule r: ((LinkedList<Rule>) m.get(nt,t))){
		    for (Token tok : r.getRightHS()){
			ruleString += tok.getName().toString() + " ";
		    }
		}
		csv += ruleString.trim() + ",";
	    }

	    csv += "\n";
	}

	return csv.substring(0, csv.length()-2) + "\n";

    }

    /**
	Removes duplicate entries in the LinkedList.
     */
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

    /**
       No longer used.
     */
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

	/**
	 * Takes nonterm and term and returns the rule at the corresponding location in the table
	 * @param nonterm
	 * @param term
	 * @return The rule in the table corresponding to the nonterm and term
	 */
	public Rule getIntersection(String nonterm, String term) {
		return (Rule) parseTable.get(nonterm, term);
	}
	
    /**
       No longer used.
     */
	public int getNumColumns() {
		return numColumns;
	}

    /**
       No longer used.
     */
	public int getNumRows() {
		return numRows;
	}

    /**
       No longer used.
     */
	public LinkedList<LinkedList<Rule>> getTable() {
		return table;
	}

	/**
	 * No longer used
	 * @param csv
	 */
    private void parseTableFromCSV(String csv){
    }


}

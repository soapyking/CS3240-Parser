import java.util.LinkedList;
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
		//initialize the first row
		for(int i=0;i<grammar.countRules();i++)
		{
			Rule rule = grammar.getRule(i);
			
			if(isUnique(rule.getLeftHS()))
			{
				table.get(0).add(new Rule(rule.getLeftHS().clone()));
				numColumns++;
			}
			for(int j=0;j<rule.getRightHS().size();j++)
			{
				Token temp = rule.getRightHS().get(j);
				if(isUnique(temp))
				{
					table.get(0).add(new Rule(rule.getLeftHS().clone()));
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
		}
		for(int i=0;i<numRows;i++)
		{
			for(int j=0;j<numColumns;j++)
			{
				table.get(i).add(new Rule(null));
			}
		}
	}
	
	private boolean isUnique(Rule toTest)
	{
		boolean add = true;
		for(int i=1;i<table.get(0).size();i++)
		{
			if(table.get(i).get(0).getLeftHS().getName().compareToIgnoreCase(toTest.getLeftHS().getName())==0)
			{
				add=false;
				break;
			}
		}
		return add;
	}
	
	private boolean isUnique(Token toTest)
	{
		boolean add = true;
		for(int i=0;i<table.get(0).size();i++)
		{
			if(table.get(0).get(i).getLeftHS().getName().compareToIgnoreCase(toTest.getName())==0)
			{
				add=false;
				break;
			}
		}
		return add;
	}
	
	public void generateParseTable(Grammar grammar)
	{	
		setupParseTable(grammar);
		for(int i=0;i<grammar.countRules();i++)
		{
			Rule rule = grammar.getRule(i);
			for(int j=0;j<rule.getRightHS().size();j++)
			{
				Token tempToken = rule.getRightHS().get(j);
				FirstSet checkFirst = tempToken.getFirstSet();
				for(int k=0;k<checkFirst.getSet().size();k++)
				{
					Token tokeninFirstSet = checkFirst.get(k);
					add(new Rule(rule.getLeftHS().clone()),new Rule(tokeninFirstSet),rule);
				}
			}
		}
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
	
}

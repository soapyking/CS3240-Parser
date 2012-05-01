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
	public void generateParseTable(Grammar grammar)
	{	
		setupParseTable(grammar);
		System.out.println("\n\n\n\n");
		for(int i=0;i<grammar.countRules();i++)
		{
			Rule rule = grammar.getRule(i);
			for(int j=0;j<rule.getRightHS().size();j++)
			{
				System.out.println("What's going on? im in the method");
				System.out.println("and i step outside" + grammar.getRule(i).getLeftHS().getName());
				System.out.println(grammar.getRule(i).getLeftHS().getFirstSet()+"\n");
			}
		}
		for(int i=0;i<grammar.countRules();i++)
		{
			Rule rule = grammar.getRule(i);
			for(int j=0;j<rule.getRightHS().size();j++)
			{
				Token tempToken = rule.getRightHS().get(j);
				if(tempToken.getTypeString().compareToIgnoreCase("nonterminal")==0)
				{
					FirstSet checkFirst = tempToken.getFirstSet();
					for(int k=0;k<checkFirst.getSet().size();k++)
					{
						Token tokeninFirstSet = checkFirst.get(k);
						add(new Rule(rule.getLeftHS().clone()),new Rule(tokeninFirstSet),rule);
					}
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

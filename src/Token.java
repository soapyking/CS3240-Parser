public class Token implements Comparable
{
	public FirstSet firstSet;
	public FollowSet followSet;
	private String name;
	private TokenType type;
	

	public Token()
	{
		this.type=TokenType.NON_TERMINAL;
		firstSet = new FirstSet();
		followSet = new FollowSet();
	}
	public Token(String name, String stringType) 
	{
		this.name=name;
		if(stringType.equals("keyword"))
		{
			this.type=TokenType.KEYWORD;
		}
		else if(stringType.compareToIgnoreCase("terminal")==0)
		{
			this.type=TokenType.TERMINAL;
		}
		else if(stringType.compareToIgnoreCase("nonterminal")==0)
		{
			this.type=TokenType.NON_TERMINAL;
		}
		else if(stringType.compareToIgnoreCase("meta")==0)
		{
			this.type=TokenType.META;
		}
		else if(stringType.compareToIgnoreCase("assign")==0)
		{
			this.type=TokenType.ASSIGN;
		}
		else if(stringType.compareToIgnoreCase("endofrule")==0)
		{
			this.type=TokenType.END_OF_RULE;
		}
		else if(stringType.compareToIgnoreCase("dollar")==0)
		{
			this.type=TokenType.DOLLAR;
		}
		firstSet = new FirstSet();
		followSet = new FollowSet();
	}

	

	public String toString()
	{
		return "Token name " + this.name + " Token type: " + this.type;
	}
	
	
	public String getName() {
		return name;
	}
	public TokenType getType() {
		return type;
	}
	
	public String getTypeString()
	{
		if(type==TokenType.KEYWORD)
		{
			return "keyword";
		}
		else if(type==TokenType.TERMINAL)
		{
			return "terminal";
		}
		else if(type==TokenType.NON_TERMINAL)
		{
			return "nonterminal";
		}
		else if(type==TokenType.ASSIGN)
		{
			return "assign";
		}
		else if(type==TokenType.DOLLAR)
		{
			return "dollar";
		}
		else if(type==TokenType.META)
		{
			return "meta";
		}
		return null;
	}
	public FollowSet getFollowSet()
	{
		return followSet;
	}
	public FirstSet getFirstSet()
	{
		return firstSet;
	}

		

	/**
	 * Comparator for two Tokens. Simply uses a string compareTo() on the Tokens' name
	 */
	@Override
	public int compareTo(Object inOther) {
		if (inOther == this) return 0;
		Token other = (Token)inOther;
		return this.name.compareTo(other.getName());
	}

	public boolean equals(Token obj)
	{
		Token compared = obj;
		if(this.name.compareTo(compared.getName())==0)
		{
			if(this.type==obj.getType())
			{
				return true;
			}
		}
		return false;
	}

	public Token clone()
	{
		Token cloned = new Token();
		cloned.name = this.name;
		cloned.type = this.type;
		FollowSet follow = new FollowSet();
		FirstSet first = new FirstSet();
		for(int i=0;i<firstSet.getSet().size();i++)
		{
			Token firstClone = firstSet.getSet().get(i).clone();
			first.add(firstClone);
		}
		for(int i=0;i<followSet.getSet().size();i++)
		{
			Token followClone = followSet.getSet().get(i).clone();
			first.add(followClone);
		}
		cloned.firstSet = first;
		cloned.followSet = follow;
		return cloned;
	}
}

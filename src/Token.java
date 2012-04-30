public class Token implements Comparable
{
	public FirstSet firstSet;
	public FollowSet followSet;
	private String name;
        private String token_string;
	private TokenType type;


	public Token()
	{
		this.type=TokenType.NON_TERMINAL;
		this.token_string = "";
		firstSet = new FirstSet();
		followSet = new FollowSet();
	}
	public Token(String name, String stringType)
	{
		this.name=name;
		this.token_string = "";
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

    public Token(String name, String token_string, TokenType type){
	this.name = name;
	this.token_string = token_string;
	this.type = type;
	firstSet = new FirstSet();
	followSet = new FollowSet();
    }

	public String toString()
	{
	    return String.format("Token name: %15s | Type: %15s | String: %s", this.name, this.type, this.token_string);
	}


	public String getName() {
		return name;
	}
	public TokenType getType() {
		return type;
	}

	public String getTypeString()
	{
	    if(this.type == null)
		return null;
	    return this.type.toString();
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
		cloned.token_string = this.token_string;
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

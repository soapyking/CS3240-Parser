public class Token {

	public String attribute;// = new String();
	public String name;
	public TokenType type;
	
	public Token(String attribute, String name, String stringType)
	{
		this.attribute=attribute;
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
	}

	private enum TokenType {KEYWORD,TERMINAL,NON_TERMINAL,END_OF_TOKENS,META,ASSIGN}
	
	public String toString()
	{
		return "Token name " + this.name + " Token attribute: " + this.attribute +
		" Token type: " + this.type;
	}
}

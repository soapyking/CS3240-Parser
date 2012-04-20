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
	}

	private enum TokenType {KEYWORD,TERMINAL,NON_TERMINAL,END_OF_TOKENS}
	
	public String toString()
	{
		return "Token name " + this.name + " Token type: " + this.type;
	}
}

package com;
public class Token {

        //Fix this scope, it's only public for chris's shitty testing
	public String attribute = new String(); //Is this instantiation necessary?
    public String name;
	public TokenType type;
	
	public Token(String attribute, String name, String stringType)
	{
		this.attribute=attribute;
		this.name=name;
		if(stringType.compareToIgnoreCase("keyword") == 0)
		{
			this.type=TokenType.KEYWORD;
		}
		else if(stringType.compareToIgnoreCase("terminal") == 0)
		{
			this.type=TokenType.TERMINAL;
		}
	}

	private enum TokenType {KEYWORD,TERMINAL,NON_TERMINAL,END_OF_TOKENS}
	
	/*
	@SuppressWarnings("unused")
	private TokenType getTokenType()
	{
		return type;
	}*/
}

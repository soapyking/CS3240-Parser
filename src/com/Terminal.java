package com;
public class Terminal {

	private static Lexer lex;
	
	public static void main(String[] args)
	{
		lex  = new Lexer(args[0]);
		
	}
}

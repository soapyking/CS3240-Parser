package com;
import java.util.LinkedList;

public class Rule
{
	private Token left_hs;
	private LinkedList<Token> right_hs;
	private boolean hasOr;

	public Rule(Token left_hs)
	{
		this.left_hs=left_hs;
		right_hs=new LinkedList<Token>();
	}
	
	public Rule(Token left_hs, LinkedList<Token> right_hs)
	{
		this.left_hs=left_hs;
		this.right_hs=right_hs;
	}

	public boolean searchRightForName(String search)
	{
		for(int i=0;i<right_hs.size();i++)
		{
			if(right_hs.get(i).getName().compareTo(search)==0)
			{
				return true;
			}
		}
		return false;
	}

	public Token getLeftHS()
	{
		return left_hs;
	}

	public LinkedList<Token> getRightHS()
	{
		return right_hs;
	}
	
	public void addRight_hs(LinkedList<Token> right)
	{
		right_hs=right;
	}

//	public Rule clone()
//	{
//		//TODO finish this section.  It doesn't compile at the moment but I wanted to push what I had.
//		//Token LHS = new Token(this.left_hs.getName(),this.left_hs.getTypeString());
//		//LinkedList<Token> right  = new LinkedList<Token>();
//		//right.add(
//		//return null;
//	}

	public String toString()
	{
		String returned = new String();
		returned += left_hs;
		returned += " -> \n";
		for(int i=0;i<right_hs.size();i++)
		{
			returned+="          ";
			returned+= right_hs.get(i);
			returned+="\n";
		}
		returned += "\n";
		return returned;
	}
	
}

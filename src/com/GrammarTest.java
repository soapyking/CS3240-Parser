package com;
import junit.framework.TestCase;
import org.junit.*;
import java.util.LinkedList;

public class GrammarTest extends TestCase {

	@Test
	public void testMakeFirstSet()
	{
		Grammar gram;
		Token exp = new Token("exp","nonterminal");
		Token addop = new Token("addop","nonterminal");
		Token term = new Token("term","nonterminal");
		Token plus = new Token("+","terminal");
		Token minus = new Token("-","terminal");
		Token mulop = new Token("mulop","nonterminal");
		Token factor = new Token("factor","nonterminal");
		Token multiply = new Token("*","terminal");
		Token leftpar = new Token("(","terminal");
		Token rightpar = new Token(")","terminal");
		Token number = new Token("number","terminal");
		
		LinkedList<Token> right1 = new LinkedList<Token>();
		LinkedList<Token> right2 = new LinkedList<Token>();
		LinkedList<Token> right3 = new LinkedList<Token>();
		LinkedList<Token> right4 = new LinkedList<Token>();
		LinkedList<Token> right5 = new LinkedList<Token>();
		LinkedList<Token> right6 = new LinkedList<Token>();
		LinkedList<Token> right7 = new LinkedList<Token>();
		LinkedList<Token> right8 = new LinkedList<Token>();
		LinkedList<Token> right9 = new LinkedList<Token>();
		
		right1.add(exp);
		right1.add(addop);
		right1.add(term);
		right2.add(term);
		right3.add(plus);
		right4.add(minus);
		right5.add(term);
		right5.add(mulop);
		right5.add(factor);
		right6.add(factor);
		right7.add(multiply);
		right8.add(leftpar);
		right8.add(exp);
		right8.add(rightpar);
		right9.add(number);
		
		
		
		Rule rule1 = new Rule(exp,right1);
		Rule rule2 = new Rule(exp,right2);
		Rule rule3 = new Rule(addop,right3);
		Rule rule4 = new Rule(addop,right4);
		Rule rule5 = new Rule(term,right5);
		Rule rule6 = new Rule(term,right6);
		Rule rule7 = new Rule(mulop,right7);
		Rule rule8 = new Rule(factor,right8);
		Rule rule9 = new Rule(factor,right9);
		
		gram = new Grammar();
		gram.add(rule1);
		gram.add(rule2);
		gram.add(rule3);
		gram.add(rule4);
		gram.add(rule5);
		gram.add(rule6);
		gram.add(rule7);
		gram.add(rule8);
		gram.add(rule9);
		
		System.out.println(gram.toString());
		System.out.println("----------------------------------");
		gram.removeRecursion();
		System.out.println(gram.toString());
		System.out.println("----------------------------------");
		
//		gram.makeFirstSet();
//		LinkedList<Token> expFirstSet = new LinkedList<Token>();
//		expFirstSet.add(leftpar);
//		expFirstSet.add(number);
//		assertEquals(exp.getFirstSet().getSet(),expFirstSet);
		
	}
}

import junit.framework.TestCase;
import org.junit.*;
import java.util.LinkedList;

public class GrammarTest extends TestCase {

	Grammar gram;
	Token leftpar;
	Token number;
	Token exp;
	Token factor;
	Token term;
	Token addop;
	Token mulop;
	@Before
	public void setUp()
	{
		
		exp = new Token("exp","nonterminal");
		addop = new Token("addop","nonterminal");
		term = new Token("term","nonterminal");
		Token plus = new Token("+","terminal");
		Token minus = new Token("-","terminal");
		mulop = new Token("mulop","nonterminal");
		factor = new Token("factor","nonterminal");
		Token multiply = new Token("*","terminal");
		leftpar = new Token("(","terminal");
		Token rightpar = new Token(")","terminal");
		number = new Token("number","terminal");
		
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
	}
	@Test
	public void testMakeFirstSetNumber()
	{
		gram.makeFirstSet();
		assertEquals(exp.getFirstSet().getSet().size(),2);
		assertEquals(term.getFirstSet().getSet().size(),2);
		assertEquals(factor.getFirstSet().getSet().size(),2);
		assertEquals(addop.getFirstSet().getSet().size(),2);
	}
	
	@Test
	public void testMakeFirstSetContents()
	{
		gram.makeFirstSet();
		assertTrue(exp.getFirstSet().get(0).getName().compareToIgnoreCase("(")==0);
		assertTrue(exp.getFirstSet().get(1).getName().compareToIgnoreCase("number")==0);
		assertTrue(term.getFirstSet().get(0).getName().compareToIgnoreCase("(")==0);
		assertTrue(term.getFirstSet().get(1).getName().compareToIgnoreCase("number")==0);
		assertTrue(factor.getFirstSet().get(0).getName().compareToIgnoreCase("(")==0);
		assertTrue(factor.getFirstSet().get(1).getName().compareToIgnoreCase("number")==0);
		assertTrue(addop.getFirstSet().get(0).getName().compareToIgnoreCase("+")==0);
		assertTrue(addop.getFirstSet().get(1).getName().compareToIgnoreCase("-")==0);
		assertTrue(mulop.getFirstSet().get(0).getName().compareToIgnoreCase("*")==0);
	}

	@Test
	public void testMakeFollowSetSize()
	{
		gram.makeFirstSet();
		gram.makeFollowSet();
		assertEquals(exp.getFollowSet().getSet().size(),4);
		assertEquals(addop.getFollowSet().getSet().size(),2);
		assertEquals(term.getFollowSet().getSet().size(),5);
		assertEquals(mulop.getFollowSet().getSet().size(),2);
		assertEquals(factor.getFollowSet().getSet().size(),5);
	}
	
	@Test
	public void testMakeFollowSet()
	{
		gram.makeFirstSet();
		gram.makeFollowSet();
		gram.countRules();
		assertTrue(exp.getFollowSet().get(0).getTypeString().compareToIgnoreCase("dollar")==0);
		assertTrue(exp.getFollowSet().get(1).getName().compareToIgnoreCase("+")==0);
		assertTrue(exp.getFollowSet().get(2).getName().compareToIgnoreCase("-")==0);
		assertTrue(exp.getFollowSet().get(3).getName().compareToIgnoreCase(")")==0);
		assertTrue(addop.getFollowSet().get(0).getName().compareToIgnoreCase("(")==0);
		assertTrue(addop.getFollowSet().get(1).getName().compareToIgnoreCase("number")==0);
		assertTrue(term.getFollowSet().get(0).getName().compareToIgnoreCase("dollar")==0);
		assertTrue(term.getFollowSet().get(1).getName().compareToIgnoreCase("+")==0);
		assertTrue(term.getFollowSet().get(2).getName().compareToIgnoreCase("-")==0);
		assertTrue(term.getFollowSet().get(3).getName().compareToIgnoreCase("*")==0);
		assertTrue(term.getFollowSet().get(4).getName().compareToIgnoreCase(")")==0);
		assertTrue(mulop.getFollowSet().get(0).getName().compareToIgnoreCase("(")==0);
		assertTrue(mulop.getFollowSet().get(1).getName().compareToIgnoreCase("number")==0);
		assertTrue(factor.getFollowSet().get(0).getName().compareToIgnoreCase("dollar")==0);
		assertTrue(factor.getFollowSet().get(1).getName().compareToIgnoreCase("+")==0);
		assertTrue(factor.getFollowSet().get(2).getName().compareToIgnoreCase("-")==0);
		assertTrue(factor.getFollowSet().get(3).getName().compareToIgnoreCase("*")==0);
		assertTrue(factor.getFollowSet().get(4).getName().compareToIgnoreCase(")")==0);
	}
	
	@Test
	public void testRemoveRecursion()
	{
		//System.out.println(gram.toString());
		//System.out.println("----------------------------------");
		gram.removeRecursion();
		//System.out.println(gram.toString());
		//System.out.println("----------------------------------");
	}
	
	@Test
	public void testCategorize() {
		System.out.println("Testing categorizorizerism");
		System.out.println(gram);
		System.out.println("--------------------------");
		LinkedList<LinkedList<Rule>> categorized = gram.categorizeRules();
		System.out.println(categorized);
	}
}

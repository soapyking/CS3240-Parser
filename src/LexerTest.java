import junit.framework.TestCase;
import org.junit.*;
/*
 * In order to compile this on commandline you need to type 
 * javac -cp (junit jar file) and then (space) then  LexerTest.java
 */
public class LexerTest extends TestCase {

	@Test
	public void testGetToken()
	{
		Token a = new Token("a","terminal");
		Token b = new Token("b","terminal");
		Token c = new Token("c","terminal");
		Token d = new Token("d","terminal");
		Token e = new Token("e","terminal");
		Lexer lexa = new Lexer();
		Lexer lexb = new Lexer();
		lexa.ll_token_list.add(a);
		lexa.ll_token_list.add(b);
		lexa.ll_token_list.add(c);
		assertEquals(a,lexa.getToken());
		assertEquals(b,lexa.getToken());
		assertEquals(c.getType(),lexa.getToken().getType());
		assertEquals(lexa.getToken(),null);
		lexb.ll_token_list.add(e);
		lexb.ll_token_list.add(d);
		Token f = new Token("e","terminal");
		assertEquals(lexb.getToken().getName(),f.getName());
	}

	@Test
	public void testHasTokens()
	{
		Lexer lex = new Lexer();
		Token a = new Token("a","terminal");
		Token b = new Token("b","nonterminal");
		Token c = new Token("c","nonterminal");
		Token d = new Token("d","terminal");
		Token e = new Token("d","terminal");
		lex.ll_token_list.add(a);
		assertEquals(lex.hasTokens(),true);
		lex.getToken();
		assertEquals(lex.hasTokens(),false);
		lex.ll_token_list.add(b);
		lex.ll_token_list.add(c);
		lex.ll_token_list.add(d);
		lex.ll_token_list.add(e);
		assertEquals(lex.hasTokens(),true);
		lex.getToken();
		lex.getToken();
		lex.getToken();
		assertEquals(lex.hasTokens(),true);
		lex.getToken();
		assertEquals(lex.hasTokens(),false);
	}
	
	@Test
	public void testGetTokens()
	{
		
	}
	
	
}

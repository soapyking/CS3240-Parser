import java.util.LinkedList;

public class ParseStack {

	private LinkedList<String> stack;
	private ParseTable table;
	
	public ParseStack(ParseTable p) {
		this.stack = new LinkedList<String>();
		this.table = p;
	}
	
	public void push(String token) {
		stack.push(token);
		//Lookup the token vs what's top on the stack
			//If no rule there, parse error, unexpected symbol encountered
		//Put right side of rule onto stack
		//Repeat until nonterminal encountered
		//Pop nonterminal off stack
		//Put right on stack
		//Pop the nonterminal off
		//Terminate when encounter dollar sign, check if stack is empty
			//If not empty, end of file reached too soon
	}
	
	private void pop() {
		stack.pop();
	}
}

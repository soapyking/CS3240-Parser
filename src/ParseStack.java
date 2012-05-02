import java.util.LinkedList;

public class ParseStack {

	private LinkedList<String> stack;
	
	public void push(String token) {
		stack.push(token);
		
	}
	
	private void pop() {
		stack.pop();
	}
}

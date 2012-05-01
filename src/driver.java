import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList; 

public class driver {

	HashSet<Token> tokens;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File tokenFile = new File("java_lexer/test_output.tok");
		File tableFile = new File("java_lexer/parsetable.csv");
		String line = null;
		try {
			FileReader reader = new FileReader(tokenFile);
			BufferedReader buff = new BufferedReader(reader);
			//This assumes there's only 1 line of tokens
			//Glob help us all if there's more
			line = buff.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		String[] splat = line.split(" ");
		for(String s: splat) {
			System.out.println(s);
		}
	}
}

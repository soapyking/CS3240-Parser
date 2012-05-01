import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList; 

public class driver {

	LinkedList<Token> tokens;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File tokenFile = new File("tokens.tok");
		File tableFile = new File("table.tok");
		try {
			FileReader reader = new FileReader(tokenFile);
			BufferedReader buff = new BufferedReader(reader);
			String line;
			while((line = buff.readLine()) != null) {
				Token token = new Token(line, null);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}

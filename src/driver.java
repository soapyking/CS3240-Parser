import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList; 
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;

public class driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File tokenFile = new File("java_lexer/test_output.tok");
		File tableFile = new File("java_lexer/parsetable.csv");
		String line = null;
		MultiKeyMap mkp = new MultiKeyMap();
		String[] tokens = null;
		try {
			FileReader reader = new FileReader(tokenFile);
			BufferedReader buff = new BufferedReader(reader);
			//This assumes there's only 1 line of tokens
			//Glob help us all if there's more
			line = buff.readLine();
			tokens = line.split(" ");
			buff.close();
			reader.close();
			reader = new FileReader(tableFile);
			buff = new BufferedReader(reader);
			line = buff.readLine();
			String tempTermTokens[] = line.split(" ");
			LinkedList<String> termTokens = new LinkedList<String>();
			for(int i = 1; i < tempTermTokens.length; i++) {
				termTokens.add(tempTermTokens[i]);
			}
			while((line = buff.readLine()) != null) {
				String[] rules = line.split(",");
				String nonTerm = rules[0];
				LinkedList<String> entries = new LinkedList<String>();
				for(int i = 1; i < rules.length; i++) {
					entries.add(rules[i]);
				}
				for(int i = 0; i < entries.size(); i++) {
					mkp.put(nonTerm, termTokens.get(i), entries.get(i));
				}
			}
			buff.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		for(String s: tokens) {
			
		}
	}
}

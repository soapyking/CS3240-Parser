import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.MultiKeyMap;

public class driver {

    /**
     * @param args
     */
    public static void main(String[] args) {
	File tokenFile = new File("test_output.tok");
	File tableFile = new File("parsetable.csv");
	String line = null;
	//HashSet probably unnecessary if using MultiKeyMaps
	//HashSet<LinkedList<String>> table = new HashSet<LinkedList<String>>();

	ParseTable table;
	try {
	    FileReader reader = new FileReader(tokenFile);
	    BufferedReader buff = new BufferedReader(reader);
	    //This assumes there's only 1 line of tokens
	    //Glob help us all if there's more
	    line = buff.readLine();
	    String[] tokens = line.split(" ");
	    buff.close();
	    reader = new FileReader(tableFile);
	    buff = new BufferedReader(reader);
	    String csv = "";
	    while((line = buff.readLine()) != null) {
		// //Remove the syso's after debugging
		// String[] rules = line.split(",");
		// for(String s: rules) {
		//     System.out.print(s + " ");
		// }
		// System.out.println();
		csv += line + "\n";
	    }
	    table = new ParseTable(csv);

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.exit(1);
	} catch (IOException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }
}

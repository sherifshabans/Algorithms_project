import java.awt.*;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 *
 * @author CS Staff
 *
 * @version 2021-08-23
 */
public class CommandProcessor {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     *
     *
     *            the database object to manipulate
     */
    public CommandProcessor() {
        data = new Database();
    }


    public int ToDecimal(String s){

        int res = 0;
        boolean negative = false;
        for(int i = 0; i < s.length(); ++i){
            if(i == 0 && s.charAt(i) == '-'){
                negative = true;
                continue;
            }
            res = res * 10 + s.charAt(i) - '0';
        }
        if(negative)res *= -1;
        return res;
    }

    /**
     * This method identifies keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions within the database required. These
     * actions are performed on specified objects and include insert, remove,
     * regionsearch, search, intersections, and dump. If the command in the file line is not
     * one of these, an appropriate message will be written in the console. This
     * processor method is called for each line in the file. Note that the
     * methods called will themselves write to the console, this method does
     * not, only calling methods that do.
     *
     * @param line
     *            a single line from the text file
     */

    public void processor(String line) {
        String[] tokens = line.split("\\s+");
        for(int i = 0; i < tokens.length; ++i){
            tokens[i] = tokens[i].trim();
        }

        if (tokens[0].equals("intersections")) {
            data.intersections();
        } else if (tokens[0].equals("dump")) {
            data.dump();
        } else if (tokens[0].equals("search")) {

            data.search(tokens[1]);
        } else if (tokens[0].equals("remove")) {
            if (tokens.length == 2) data.remove(tokens[1]);
            else
                data.remove(ToDecimal(tokens[1]), ToDecimal(tokens[2]), ToDecimal(tokens[3]), ToDecimal(tokens[4]));
        } else if (tokens[0].equals("insert")) {
            data.insert(new KVPair<>(tokens[1], new Rectangle(ToDecimal(tokens[2]), ToDecimal(tokens[3]), ToDecimal(tokens[4]), ToDecimal(tokens[5]))));
        } else if (tokens[0].equals("regionsearch")) {
            data.regionsearch(ToDecimal(tokens[1]), ToDecimal(tokens[2]), ToDecimal(tokens[3]), ToDecimal(tokens[4]));
        } else {
            System.out.println("Please enter a valid command");
        }
    }

}

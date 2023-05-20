import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * The class containing the main method, the entry point of the application. It
 * will take a command line file argument which include the commands to be read
 * and creates the appropriate SkipList object and outputs the correct results
 * to the console as specified in the file.
 *
 * @author (Zombies developers)
 *
1- (section 2)حسين عبدالقادر حسين شحاته
2- (section 1)أحمد هشام فتح الله فراج
3- (section 1)الشريف شعبان عبدالمجيد همام
4- (section 6)مريم طارق سعد محمد
5- (section 2)جهاد عمر عبدالعال
6- (section 4)شهد محمد مصطفي
 *
 * @version (March 2023)
 */
public class Rectangle1 {

    /**
     * The entry point of the application.
     *
     * @param args
     *            The name of the command file passed in as a command line
     *            argument.
     */
    public static void main(String[] args) {

        // the file object with path
        File file = new File("Data\\P1test1.txt");
        //File file = new File("Data\\P1test2.txt");

        // Attempts to open the file and scan through it
        try {

            // creates a scanner object
            Scanner scanner = new Scanner(file);

            // creates a command processor object
            CommandProcessor cmdProc = new CommandProcessor();
            // reads the entire file and processes the commands
            // line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // determines if the file has more lines to read
                if (!line.trim().isEmpty()) {
                    cmdProc.processor(line.trim());
                }
            }
            // closes the scanner
            scanner.close();
        }
        // catches the exception if the file cannot be found
        // and outputs the correct information to the console
        catch (FileNotFoundException e) {
            System.out.println("Invalid file");
            e.printStackTrace();
        }
    }
}
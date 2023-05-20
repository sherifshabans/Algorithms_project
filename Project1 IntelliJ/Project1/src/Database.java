import java.awt.Rectangle;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import static java.lang.Character.*;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 *
 * @author CS Staff
 *
 * @version 2021-08-23
 */

public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }

    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * insert the KVPair specified into the sorted SkipList appropriately
     *
     * @param pair
     *            the KVPair to be inserted
     */
    public String insert(KVPair<String, Rectangle> pair) {
        //checks if the key is begin with a letter, and may contain letters, digits, and underscore characters
        boolean validName=validName(pair.getKey());
        // checks if the value is fit within the “world box”
        boolean validValue=validValue(pair.getValue().x,pair.getValue().y,pair.getValue().width,pair.getValue().height);
        // checks if the key and value are valid
        if(validName && validValue){
            list.insert(pair);
            System.out.println("Rectangle inserted: ("+ pair.getKey()+", "+
                    pair.getValue().x+", "+pair.getValue().y+", "+
                    pair.getValue().width+", "+pair.getValue().height+")");
            return "Rectangle inserted: ("+ pair.getKey()+", "+
                    pair.getValue().x+", "+pair.getValue().y+", "+
                    pair.getValue().width+", "+pair.getValue().height+")";

        }else {// if the key or value are invalid
            System.out.println("Rectangle rejected: ("+ pair.getKey()+", "+
                    pair.getValue().x+", "+pair.getValue().y+", "+
                    pair.getValue().width+", "+pair.getValue().height+")");
            return "Rectangle rejected: ("+ pair.getKey()+", "+
                    pair.getValue().x+", "+pair.getValue().y+", "+
                    pair.getValue().width+", "+pair.getValue().height+")";
        }
    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     *
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        if(!validName(name)) {
            System.out.printf("Rectangle rejected: (%d)",name);
            return;
        }

        KVPair<String, Rectangle> result = list.remove(name);

        if(result != null)
            System.out.printf("Rectangle removed: (%s, %d, %d, %d, %d)\n",result.getKey(), result.getValue().x,
                    result.getValue().y, result.getValue().width, result.getValue().height);
        else
            System.out.printf("Rectangle not removed: (%s)\n", name);
    }

    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     *
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        if(!validValue(x,y,w,h)) {   //check the values first
            System.out.printf("Rectangle rejected: (%d, %d, %d, %d)\n", x, y, w, h);
            return;
        }
        KVPair<String, Rectangle> result = list.removeByValue(new Rectangle(x,y,w,h));
        if(result != null)
            System.out.printf("Rectangle removed: (%s, %d, %d, %d, %d)\n",result.getKey(), x, y, w, h);

        else
            System.out.printf("Rectangle rejected: (%d, %d, %d, %d)\n", x, y, w, h);
    }

    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region. You will need a SkipList Iterator for this
     *
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        // If the width or the height of the region is not positive then it's not a valid region
        if(w <= 0 || h <= 0){
            System.out.printf("Rectangle rejected: (%d, %d, %d, %d)\n", x, y, w, h);
            return;
        }

        System.out.printf("Rectangles intersecting region (%d, %d, %d, %d):\n", x, y, w, h);

        // Create a skip list iterator to iterate over the skip list
        Iterator<KVPair<String, Rectangle>> it = list.iterator();

        // while it's not the last node in the skip list
        while(it.hasNext()){
            KVPair<String, Rectangle> kv = it.next();

            // Check if this rectangle intersects with the region
            if(((kv.getValue().x >= x && kv.getValue().x < x + w) || (x >= kv.getValue().x && x < kv.getValue().x + kv.getValue().width))
                    && ((kv.getValue().y >= y && kv.getValue().y < y + h) || (y >= kv.getValue().y && y < kv.getValue().y + kv.getValue().height))){
                System.out.printf("(%s, %d, %d, %d, %d)\n", kv.getKey(), kv.getValue().x, kv.getValue().y, kv.getValue().width, kv.getValue().height);
            }
        }
    }

    /**
     * Prints out all the rectangles that Intersect each other by calling the
     * SkipList method for intersections. You will need to use two SkipList Iterators for this
     */
    /*
     The time complexity of the intersections method is O(n^2), where n is the number of rectangles in the list.
     This is because there are two nested loops that iterate through the list, comparing each pair of rectangles.
     */
    public void intersections() {
        System.out.print("Intersections pairs:\n");
        Iterator<KVPair<String, Rectangle>> Iterator1 = list.iterator();
        while(Iterator1.hasNext()){
            KVPair<String, Rectangle> Rectangle1 = Iterator1.next();
            Iterator<KVPair<String, Rectangle>> Iterator2 = list.iterator();
            while(Iterator2.hasNext()){
                KVPair<String, Rectangle> Rectangle2 = Iterator2.next();
                // Skip rectangles that are the same
                if (Rectangle1 == Rectangle2) {
                    continue;
                }
                // Check if the two rectangles intersect
                if (intersects(Rectangle1,Rectangle2)) {
                    // If they do, print the intersection pairs
                    printIntersects(Rectangle1,Rectangle2);
                }
            }
        }
    }
    // This method checks if two rectangles intersect with each other
    public boolean intersects(KVPair<String, Rectangle> Rectangle1 , KVPair<String, Rectangle> Rectangle2) {
        // Check if the x, y coordinates of one rectangle are within the boundaries of the other rectangle
        // Return true if the rectangles intersect, if not return false
        return (
                Rectangle1.getValue().x < (Rectangle2.getValue().x + Rectangle2.getValue().width) &&
                        Rectangle1.getValue().x + Rectangle1.getValue().width  > (Rectangle2.getValue().x) &&
                        Rectangle2.getValue().y < (Rectangle1.getValue().y + Rectangle1.getValue().height) &&
                        Rectangle2.getValue().y + Rectangle2.getValue().height > (Rectangle1.getValue().y)
        );
    }
    // This method prints out the intersection pairs of two rectangles
    public void printIntersects(KVPair<String, Rectangle> Rectangle1 , KVPair<String, Rectangle> Rectangle2) {
        // Print the keys and coordinates of both rectangles
        System.out.printf("(%s, %d, %d, %d, %d | %s, %d, %d, %d, %d)\n",
                Rectangle1.getKey(), Rectangle1.getValue().x, Rectangle1.getValue().y,
                Rectangle1.getValue().width, Rectangle1.getValue().height,
                Rectangle2.getKey(), Rectangle2.getValue().x, Rectangle2.getValue().y,
                Rectangle2.getValue().width, Rectangle2.getValue().height
        );
    }

    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     *
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        // Create a new KVPair with the given name and null Rectangle
        KVPair<String, Rectangle> pair = new KVPair<>(name, null);

        // Check if the given name is valid
        if (validName(name)) {
            // Search for the KVPair in the SkipList and print the resulting Rectangles
            printRectangle(list.search(pair.getKey()), name);
        } else {
            // The name is not valid, so print an error message
            System.out.printf("Rectangle not found: %s\n", name);
        }
    }

    /**
     * Prints the Rectangles in the given list.
     *
     * @param list
     *            the list of KVPair objects to print Rectangles from
     * @param name
     *            the name of the Rectangles being printed
     */
    public void printRectangle(ArrayList<KVPair<String, Rectangle>> list, String name) {
        // Get the size of the list
        int size = list.size();

        // If the list is empty, print an error message and return
        if (size == 0) {
            System.out.printf("Rectangle not found: %s\n", name);
            return;
        } else {
            // Otherwise, print a message saying Rectangles were found
            System.out.println("Rectangles found:");
        }

        // Loop through the list and print each Rectangle
        for (int j = 0; j < size; j++) {
            // Get the KVPair at the current index
            KVPair<String, Rectangle> pair = new KVPair<String, Rectangle>(null, null);
            pair = list.get(j);

            // Get the Rectangle from the KVPair
            Rectangle rectangle = (Rectangle) pair.getValue();

            // Print the Rectangle's information in a specific format
            System.out.println(MessageFormat.format("({0}, {1}, {2}, {3}, {4})",
                    pair.getKey(), rectangle.x, rectangle.y, rectangle.width, rectangle.height));
        }
    }

    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        this.list.dump();
    }

    /**
     *  The validName method takes a string input called 'name' and checks if it starts with an alphabetic character.
     If the first character is an alphabetic character, it loops through the remaining characters and checks
     if each character is alphabetic, numeric, or an underscore.
     If any character is found that is not alphabetic, numeric, or an underscore, the method returns false.
     Otherwise, it returns true.
     * @param name
     * @return
     */

    // This method checks if the given name string is a valid variable name
    public boolean validName(String name){
        boolean isValid = true;
        // Check if the first character is alphabetical
        if(isAlphabetic(name.charAt(0))){
            // Check if all characters are either alphabetical, numerical, or underscore
            for (int i = 0; i < name.length(); i++) {
                if (!(isAlphabetic(name.charAt(i))||isDigit(name.charAt(i))||name.charAt(i) == '_')) {
                    isValid = false;
                    break;
                }
            }
        }
        else {
            isValid = false;
        }
        return isValid;
    }

    /**
     *    The validValue method takes four integer inputs - x, y, width, and height - and checks if the values are valid.
     It first checks if the width and height values are greater than zero.
     If so, it checks if the x and y values are non-negative and if the sum of the height and y value is less than
     or equal to 1024 and the sum of the width and x value is less than or equal to 1024.
     If all conditions are met, the method returns true. Otherwise, it returns false.
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    // This method checks if the given (x, y) coordinates, width and height values are valid for a rectangle
    public boolean validValue(int x,int y, int width,int height ) {
        // Check if the width and height are positive
        if (width > 0 && height > 0) {
            // Check if the given (x, y) coordinates are within the limits of the “world box” and the rectangle is within“world box” bounds
            if( (x>=0)&&(y>=0)&&(height+y<=1024)&&(width+x<=1024)) {
                return true;
            }
        }
        return false;
    }
}
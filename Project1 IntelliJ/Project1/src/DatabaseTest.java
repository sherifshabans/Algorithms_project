import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    // intersections test

    @Test
    public void testTwoِRectangles() {

        Rectangle rectangle3 = new Rectangle(15,15,5,5);
        KVPair<String,Rectangle> x3 = new KVPair<>("x3",rectangle3);
        database.insert(x3);

        Rectangle rectangle4 = new Rectangle(7,7,10,10);
        KVPair<String,Rectangle> x4 = new KVPair<>("x4",rectangle4);
        database.insert(x4);


        boolean expextedValue =  database.intersects(x3,x4);
        assertEquals(expextedValue, true);

        Assertions.assertDoesNotThrow(() -> {
            database.intersections();
        });
    }

    @Test
    public void testTwoِAdjacentRectangles() {

        Rectangle rectangle5 = new Rectangle(0,0,5,5);
        KVPair<String,Rectangle> x5 = new KVPair<>("x5",rectangle5);
        database.insert(x5);

        Rectangle rectangle6 = new Rectangle(0,5,5,5);
        KVPair<String,Rectangle> x6 = new KVPair<>("x6",rectangle6);
        database.insert(x6);

        boolean expextedValue =  database.intersects(x5,x6);
        assertEquals(expextedValue, false);

        Assertions.assertDoesNotThrow(() -> {
            database.intersections();
        });
    }

    @Test
    public void testSomeIntersection() {

        // Insert three Rectangles into the SkipList using KVPair objects
        Rectangle rectangle3 = new Rectangle(0, 0, 2, 2);
        KVPair<String, Rectangle> x3 = new KVPair<>("x3", rectangle3);
        database.insert(x3);

        Rectangle rectangle4 = new Rectangle(1, 1, 2, 2);
        KVPair<String, Rectangle> x4 = new KVPair<>("x4", rectangle4);
        database.insert(x4);

        Rectangle rectangle5 = new Rectangle(3, 3, 2, 2);
        KVPair<String, Rectangle> x5 = new KVPair<>("x5", rectangle5);
        database.insert(x5);

        // Test the intersection between x3 and x4
        boolean expectedValue1 = database.intersects(x3, x4);
        assertEquals(expectedValue1, true);

        // Test the intersection between x4 and x5
        boolean expectedValue2 = database.intersects(x4, x5);
        assertEquals(expectedValue2, false);

        // Test the intersection between x3 and x5
        boolean expectedValue3 = database.intersects(x3, x5);
        assertEquals(expectedValue3, false);

        // Test that the intersections method does not throw an exception
        Assertions.assertDoesNotThrow(() -> {
            database.intersections();
        });
    }

    @Test
    public void testOnedatabase() {

        // Insert a single Rectangle into the SkipList using a KVPair object
        Rectangle rectangle3 = new Rectangle(0, 0, 2, 2);
        KVPair<String, Rectangle> x3 = new KVPair<>("x3", rectangle3);
        database.insert(x3);

        // Test that the intersections method does not throw an exception
        Assertions.assertDoesNotThrow(() -> {
            database.intersections();
        });
    }

    @Test
    public void testEmptyList() {

        // Test that the intersections method does not throw an exception when called on an empty list
        Assertions.assertDoesNotThrow(() -> {
            database.intersections();
        });
    }

    @Test
    public void testIdenticaldatabases() {

        // Insert two identical Rectangles into the SkipList using KVPair objects
        Rectangle rectangle3 = new Rectangle(0, 0, 2, 2);
        KVPair<String, Rectangle> x3 = new KVPair<>("x3", rectangle3);
        database.insert(x3);

        Rectangle rectangle4 = new Rectangle(0, 0, 2, 2);
        KVPair<String, Rectangle> x4 = new KVPair<>("x4", rectangle4);
        database.insert(x4);

        // Test that the intersects method correctly identifies the overlap between the identical Rectangles
        boolean expextedValue1 =  database.intersects(x3, x4);
        assertEquals(expextedValue1, true);

        // Test that the intersections method does not throw an exception
        Assertions.assertDoesNotThrow(() -> {
            database.intersections();
        });
    }

    // insert test


    @org.junit.jupiter.api.Test
    void testInsert() {
        KVPair<String , Rectangle> test1 = new KVPair<>("a" , new Rectangle(1,2,3,4));
        KVPair<String , Rectangle> test2 = new KVPair<>("b" , new Rectangle(10,20,30,40));
        KVPair<String , Rectangle> test3 = new KVPair<>("a" , new Rectangle(11,12,13,14));
        KVPair<String , Rectangle> test4 = new KVPair<>("a" , new Rectangle(1,12,30,14000));
        //to make sure that the code  does not throw any exceptions.
        String message1 =  assertDoesNotThrow(() -> {
            String insert = (String) database.insert(test1);
            return insert; } );
        String message2 =  assertDoesNotThrow(() -> {
            String insert = (String) database.insert(test2);
            return insert; } );
        String message3 =  assertDoesNotThrow(() -> {
            String insert = (String) database.insert(test3);
            return insert; } );
        String message4 =  assertDoesNotThrow(() -> {
            String insert = (String) database.insert(test4);
            return insert; } );
        // Asserts that the message string is equal to the expected insert statement for the rectangle
        assertEquals("Rectangle inserted: (a, 1, 2, 3, 4)", message1);
        assertEquals("Rectangle inserted: (b, 10, 20, 30, 40)", message2);
        assertEquals("Rectangle inserted: (a, 11, 12, 13, 14)", message3);
        assertEquals("Rectangle rejected: (a, 1, 12, 30, 14000)", message4);
    }

    // remove by value test

    @Test
    public void testRemoveByValue() {
        SkipList<String, Rectangle> skipList = new SkipList<>();
        KVPair<String , Rectangle> test1 = new KVPair<>("a" , new Rectangle(1,2,3,4));
        KVPair<String , Rectangle> test2 = new KVPair<>("b" , new Rectangle(10,20,30,40));
        KVPair<String , Rectangle> test3 = new KVPair<>("a" , new Rectangle(11,12,13,14));
        skipList.insert(test1);skipList.insert(test2);skipList.insert(test3);skipList.insert(test2);

        //test removing a value that exists in the skip list
        KVPair<String, Rectangle> removedPair = skipList.removeByValue(new Rectangle(11,12,13,14));
        assertNotNull(removedPair);
        assertEquals("a", removedPair.getKey());
        assertEquals(new Rectangle(11,12,13,14), removedPair.getValue());
        //check that the key has been removed
        assertNull(skipList.removeByValue(new Rectangle(11,12,13,14)));

        //test removing a value that does not exist in the skip list
        assertNull(skipList.removeByValue(new Rectangle(20,5,8,9)));

        //test if there are 2 keys with the same value only one of them is removed
        skipList.remove("b");
        assertNotNull(skipList.remove("b"));
    }

    // remove by key test

    @Test
    public void testRemoveByKey(){
        SkipList<String, Rectangle> skipList = new SkipList<>();
        KVPair<String , Rectangle> test1 = new KVPair<>("a" , new Rectangle(1,2,3,4));
        KVPair<String , Rectangle> test2 = new KVPair<>("b" , new Rectangle(10,20,30,40));
        KVPair<String , Rectangle> test3 = new KVPair<>("c" , new Rectangle(11,12,13,14));
        skipList.insert(test1);skipList.insert(test2);skipList.insert(test3);skipList.insert(test3);

        //test removing a value that exists in the skip list
        KVPair<String, Rectangle> removedPair = skipList.remove("a");
        assertNotNull(removedPair);
        assertEquals("a", removedPair.getKey());
        assertEquals(new Rectangle(1,2,3,4), removedPair.getValue());

        //check that the key has been removed
        assertNull(skipList.remove("a"));

        //test removing a value that does not exist in the skip list
        assertNull(skipList.remove("a"));

        //test if there are 2 keys with the same name only one of them is removed
        skipList.remove("c");
        assertNotNull(skipList.remove("c"));

    }

    // search test

    /*
    The test case aims to search for a specific Rectangle in the database by providing its name.
    Rectangles are inserted into the database using KVPair objects.
    The test ensures that the code does not throw an exception when attempting to search for an existing Rectangle.
    */
    @Test
    public void searchInDatabase() {

        Rectangle rectangle3=new Rectangle(15,15,5,5);
        KVPair<String,Rectangle>r3=new KVPair<>("r3",rectangle3);
        database.insert(r3);

        Rectangle rectangle4=new Rectangle(7,7,10,10);
        KVPair<String,Rectangle>r4=new KVPair<>("r4",rectangle4);
        database.insert(r4);

        Rectangle rectangle5=new Rectangle(20,12,3,3);
        KVPair<String,Rectangle>r5=new KVPair<>("r4",rectangle5);
        database.insert(r5);

        Rectangle rectangle6=new Rectangle(6,7,11,9);
        KVPair<String,Rectangle>r6=new KVPair<>("r5",rectangle6);
        database.insert(r6);

        Assertions.assertDoesNotThrow(() -> {
            database.search("r4");
        });
    }

    @Test
    public void searchInSkipList() {

        SkipList<String,Rectangle> skipList=new SkipList<>();
        Rectangle rectangle3=new Rectangle(15,15,5,5);
        KVPair<String,Rectangle>r3=new KVPair<>("r3",rectangle3);
        skipList.insert(r3);

        Rectangle rectangle4=new Rectangle(7,7,10,10);
        KVPair<String,Rectangle>r4=new KVPair<>("r4",rectangle4);
        skipList.insert(r4);

        Rectangle rectangle5=new Rectangle(20,12,3,3);
        KVPair<String,Rectangle>r5=new KVPair<>("r4",rectangle5);
        skipList.insert(r5);

        Rectangle rectangle6=new Rectangle(6,7,11,9);
        KVPair<String,Rectangle>r6=new KVPair<>("r5",rectangle6);
        skipList.insert(r6);

        // Create a new ArrayList to store the search results for key "r4"
        ArrayList<KVPair<String, Rectangle>> funList = new ArrayList<>();

        // Perform the search and store the results in the ArrayList
        funList =  skipList.search("r4");

        // Create an expected KVPair for key "r4" with a null value
        KVPair<String,Rectangle>exp4=new KVPair<>("r4",null);

        // Create a new ArrayList to store the expected search results
        ArrayList<KVPair<String, Rectangle>> expList = new ArrayList<>();

        // Add the expected KVPair to the expected search results ArrayList
        expList.add(exp4);

        // Compare the expected and actual search results
        for(int i=0;i<funList.size();i++) {
            assertEquals(expList.get(0).getKey() ,funList.get(i).getKey());
        }
    }
}
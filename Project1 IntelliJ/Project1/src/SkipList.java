import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.*;


/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 *
 * @author CS Staff
 *
 * @version 2021-08-23
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
        implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element of the top level
    private int size; // number of entries in the Skip List

    public SkipNode getHead() {
        return head;
    }

    public void setHead(SkipNode head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     *
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }



    /**
     * Searches for the KVPair using the key which is a Comparable object.
     *
     * @param key
     *            key to be searched for
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        // Create a new ArrayList to store the KVPair with the given key
        ArrayList<KVPair<K, V>> list = new ArrayList<>();

        // Set the current level to the highest level of the SkipList
        int currentLevel = head.level;

        // Start searching from the head node
        SkipNode current = head;

        // Traverse the SkipList from the highest level to the lowest level
        for (int i = currentLevel; i >= 0; i--) {
            // Move forward while the next node's key is less than the given key
            while (current.forward[i] != null && current.forward[i].pair.getKey().compareTo(key) < 0) {
                current = current.forward[i];
            }
        }
        // Move to the next node with the given key in the "lowest" level of the SkipList
        current = current.forward[0];

        // Add all the KVPair with the given key to the ArrayList
        while (current != null && current.pair.getKey().compareTo(key) == 0) {
            list.add(current.pair);
            current = current.forward[0];
        }
        // Return the ArrayList of KVPair with the given key
        return list;
    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }

    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     *
     * @param it the KVPair to be inserted
     */

    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        // generates a random level for the new node to be inserted
        int newLevel = randomLevel();
        // checks if the random level is greater than the level of the head node
        if (head.level < newLevel) {
            // adjusts the level of the head node to the random level
            adjustHead(newLevel);
        }
        // initializes the current level of the search to be the level of the head node
        int currentLevel = head.level;
        // creates an array of nodes to update during the insertion
        SkipList.SkipNode[] update = new SkipList.SkipNode[currentLevel + 1];

        // creates an array of nodes to update during the insertion
        SkipNode current = head;
        // traverses the skip list from the head node to the insert position
        for (int i = currentLevel; i >= 0; i--) {
            // moves forward until it finds the correct position for insertion
            while (current.forward[i] != null && current.forward[i].pair.getKey().compareTo(it.getKey()) < 0) {
                current = current.forward[i];
            }
            update[i] = current; // keep track of nodes to update
        }
        // sets the current node to the next node in the "lowest" level
        current = current.forward[0];
        // creates a new node to be inserted
        SkipNode newNode = new SkipNode(it, newLevel);
        // updates the pointers of the nodes in the current level to include the new node
        for (int i = 0; i <= newLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }
        // increase the size of the skip list
        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     *
     * @param newLevel
     *            the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
        // create new array to store the forward
        SkipNode[] newForward = head.forward;
        //check if new level is greater than the current level of the head
        if (newLevel > head.level) {
            //resize the array of the new forward pointers to the  new level
            newForward = Arrays.copyOf(newForward, newLevel + 1);
            //set all new forward pointers to null
            for (int i = head.level + 1; i <= newLevel; i++) {
                newForward[i] = null;
            }
        }
        // update the head node's forward and level to the new value
        head.forward = newForward;
        head.level = newLevel;
    }


    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     *
     * @param pair
     *            the KVPair to be removed
     * @return returns the removed pair if the pair was valid and null if not
     */

    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        int currLevel = head.level;
        SkipList.SkipNode[] updateSkipList = new SkipList.SkipNode[currLevel +1];

        SkipNode currNode = head;        //start search from head

        //start searching through levels from highest level to lowest level.
        for (int i = currLevel ; i >= 0 ; i--) {

            //move forward if next node's key is less than the search key
            while (currNode.forward[i] != null && currNode.forward[i].pair.getKey().compareTo(key) < 0) {
                currNode = currNode.forward[i];
            }

            updateSkipList[i] = currNode; //track of nodes to update
        }

        currNode = currNode.forward[0];  //move to next node with lowest level

        if (currNode == null || currNode.pair.getKey().compareTo(key) != 0) {
            return null;
        }

        //remove node and update pointers
        for (int i = 0; i <= currLevel; i++) {
            if (updateSkipList[i].forward[i] == currNode) {
                updateSkipList[i].forward[i] = currNode.forward[i];   //map previous node of which has been removed to the following node
            } else {
                break;   //stop itreation if node is not in the level
            }
        }
        size--;         //resize the list
        return currNode.pair;
    }

    /**
     * Removes a KVPair with the specified value.
     *
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    public KVPair<K, V> removeByValue(V val) {
        Iterator<KVPair<K, V>> pairIterator = iterator();

        while(pairIterator.hasNext()) {
            KVPair<K, V> pair = pairIterator.next();
            if (pair.getValue().equals(val)) {    //map the value to its key to be able to remove it
                remove(pair.getKey());           //call function Remove above to delete the node that has this key
                return pair;
            }
        }
        return null;
    }

    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
        //starting from the head
        SkipList<K,V>.SkipNode HeadOfSL = head;
        System.out.println("SkipList dump:");
        //print information about Head Node
        System.out.println("Node has depth "+ (HeadOfSL.level+1) + ", Value (null)");
        SkipList.SkipNode CurrentNode = head.forward[0];
        while (CurrentNode != null)
        {
            //get rectangle that related to the current node's key
            Rectangle Rectang = (Rectangle) CurrentNode.pair.getValue();

            System.out.printf("Node has depth %d, Value (%s, %d, %d, %d, %d)",
                    CurrentNode.level, CurrentNode.pair.getKey(),
                    Rectang.x, Rectang.y, Rectang.width, Rectang.height);

            //set current node to next node
            SkipList<K,V>.SkipNode NextNode = CurrentNode.forward[0];
            CurrentNode = NextNode ;
            System.out.println();
        }
        //print the size of skip list
        System.out.printf("SkipList size is: %d\n", size);
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     *
     * @author CS Staff
     *
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // what is this
        private SkipNode [] forward;
        // the number of levels
        private int level;

        public KVPair<K, V> getPair() {
            return pair;
        }

        public void setPair(KVPair<K, V> pair) {
            this.pair = pair;
        }

        public SkipNode[] getForward() {
            return forward;
        }

        public void setForward(SkipNode[] forward) {
            this.forward = forward;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         *
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                    level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         *
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }

    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipList<K, V>.SkipNode current;

        public SkipListIterator() {
            this.current = SkipList.this.head;
        }

        @Override
        public boolean hasNext() {
            return current.forward[0] != null;
        }

        @Override
        public KVPair<K, V> next() {
            current = current.forward[0];
            return current.pair;
        }
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new SkipListIterator();
    }
}
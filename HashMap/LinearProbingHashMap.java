import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        nullCheckBoth(key, value);
        V result = null;
        int returnNum = -1;
        int index;
        int finalIndex;

        size++;
        if (((float) size / table.length) >= MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        index = Math.abs((key.hashCode()) % table.length);
        for (int k = 0; k < table.length; k++) {
            finalIndex = (index + k) % table.length;
            if (table[finalIndex] != null && table[finalIndex].getKey().equals(key)) {
                if (!table[finalIndex].isRemoved()) {
                    size--;
                    result = table[finalIndex].getValue();
                }
                table[finalIndex].setRemoved(false);
                table[finalIndex].setValue(value);
                return result;
            }
            if (table[finalIndex] == null) {
                returnNum = finalIndex;
                break;
            }
            if (returnNum == -1 && table[finalIndex].isRemoved())) {
                returnNum = finalIndex;
            }
            if (returnNum >= 0 && returnNum != finalIndex && table[finalIndex].isRemoved()) {
                break;
            }
        }
        table[returnNum] = new LinearProbingMapEntry<>(key, value);
        return result;
    }

    /**
     * A private method that checks if the key or value are null.
     *
     * @param key the key that we will check if it's null or not
     * @param value the value that we will check if it's null or not
     * @throws java.lang.IllegalArgumentException if key or value are null
     */
    private void nullCheckBoth(K key, V value) {
        if (key == null || value == null) {
            throw new java.lang.IllegalArgumentException("Null data can not be inserted in the table");
        }
    }

    /**
     * A private method that checks if the key is null.
     *
     * @param key the key that we will check if it's null or not
     * @throws java.lang.IllegalArgumentException if key is null
     */
    private void nullKeyCheck(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Key should not be null");
        }
    }

    /**
     * A private method that checks if the size is zero
     *
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    private void sizeCheck() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The table is empty");
        }
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        nullKeyCheck(key);
        sizeCheck();
        int index = Math.abs(key.hashCode() % table.length);
        int finalIndex;
        for (int i = 0; i < table.length; i++) {
            finalIndex = (index + i) % table.length;
            if (table[finalIndex] != null && !table[finalIndex].isRemoved()
                   && table[finalIndex].getKey().equals(key)) {
                table[finalIndex].setRemoved(true);
                size--;
                return table[finalIndex].getValue();
            }
        }
        throw new java.util.NoSuchElementException("The key is not in the map");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        nullKeyCheck(key);
        sizeCheck();
        int index = Math.abs(key.hashCode() % table.length);
        int finalIndex;
        for (int i = 0; i < table.length; i++) {
            finalIndex = (index + i) % table.length;
            if (table[finalIndex] != null && !table[finalIndex].isRemoved()
                    && table[finalIndex].getKey().equals(key)) {
                return table[finalIndex].getValue();
            }
        }
        throw new java.util.NoSuchElementException("The key is not in the map");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        nullKeyCheck(key);
        if (size == 0) {
            return false;
        }
        int index = Math.abs(key.hashCode() % table.length);
        int finalIndex;
        for (int i = 0; i < table.length; i++) {
            finalIndex = (index + i) % table.length;
            if (table[finalIndex] != null && !table[finalIndex].isRemoved()
                    && table[finalIndex].getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> hashSet = new HashSet<>();
        for (LinearProbingMapEntry<K, V> tablePart : table) {
            if (hashSet.size() == size) {
                break;
            }
            if (tablePart != null && !tablePart.isRemoved()) {
                hashSet.add(tablePart.getKey());
            }
        }
        return hashSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> arrayList = new ArrayList<>();
        for (LinearProbingMapEntry<K, V> tablePart : table) {
            if (size == arrayList.size()) {
                break;
            }
            if (tablePart != null && !tablePart.isRemoved()) {
                arrayList.add(tablePart.getValue());
            }
        }
        return arrayList;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed. 
     * You should NOT copy over removed elements to the resized backing table.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (size > length) {
            throw new java.lang.IllegalArgumentException("The length is smaller than the number of items in the table");
        } else {
            LinearProbingMapEntry<K, V>[] newTable =
                    (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[length];
            int index;
            int finalIndex;
            for (LinearProbingMapEntry<K, V> tablePiece : table) {
                if (tablePiece != null && !tablePiece.isRemoved()) {
                    index = Math.abs((tablePiece.getKey().hashCode()) % length);
                    for (int j = 0; j < length; j++) {
                        if (newTable.length == table.length) {
                            break;
                        }
                        finalIndex = (index + j) % length;
                        if (newTable[finalIndex] == null) {
                            newTable[finalIndex] =
                                    new LinearProbingMapEntry<>(tablePiece.getKey(), tablePiece.getValue());
                            break;
                        }
                    }
                }
            }
            table = newTable;
        }
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

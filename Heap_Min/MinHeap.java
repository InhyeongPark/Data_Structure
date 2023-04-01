import java.util.ArrayList;

public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null data should not be inserted.");
        }
        if (data.size() == 0) {
            throw new java.lang.IllegalArgumentException("Inserted array is empty");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            nullCheck(data.get(i));
            backingArray[i + 1] = data.get(i);
            size++;
        }
        for (int i = size / 2; i >= 1; i--) {
            downHeap(backingArray, i);
        }
    }

    /**
     * A private method that checks if the data is null.
     *
     * @param data the data that we will check if it's null or not
     * @throws java.lang.IllegalArgumentException if data is null
     */
    private void nullCheck(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null data in the array");
        }
    }

    /**
     * A private method that checks if the heap is empty.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    private void elementCheck() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("We can't find the element because heap is empty.");
        }
    }

    /**
     * A private method that down-heap the data
     * if the parent node is smaller than child node
     *
     * @param backingArray the array to find the data which is stored in the given index
     * @param index the index that we use to find the data
     * @throws java.lang.IllegalArgumentException if data is null
     */
    private void downHeap(T[] backingArray, int index) {
        if (index > size) {
            return;
        }

        nullCheck(backingArray[index]);

        int left = 2 * index;
        if (backingArray[left] == null || left > size) {
            return;
        }

        int right = 2 * index + 1;
        if (backingArray[right] == null) {
            if (backingArray[index].compareTo(backingArray[left]) > 0) {
                swapHelper(index, left);
                return;
            } else if (backingArray[right] == null || right > size) {
                return;
            }
        }

        if (backingArray[index].compareTo(backingArray[left]) > 0
                || backingArray[index].compareTo(backingArray[right]) > 0) {
            if (backingArray[left].compareTo(backingArray[right]) < 0) {
                swapHelper(index, left);
                downHeap(backingArray, left);
            } else {
                swapHelper(index, right);
                downHeap(backingArray, right);
            }
        }
    }

    /**
     * A private method that up-heap the data
     * if the parent node is smaller than child node
     *
     * @param backingArray the array to find the data which is stored in the given index
     * @param index the index that we use to find the data
     * @throws java.lang.IllegalArgumentException if data is null
     */
    private void upHeap(T[] backingArray, int index) {
        nullCheck(backingArray[index]);

        if (index == 1) {
            return;
        }
        int parent = index / 2;
        if (backingArray[parent].compareTo(backingArray[index]) > 0
             && index > 1) {
            swapHelper(index, parent);
            upHeap(backingArray, parent);
        }
    }

    /**
     * A private helper method that swap two data
     *
     * @param firstIndex the index to change with the second index
     * @param secondIndex the index to change with the first index
     */
    private void swapHelper(int firstIndex, int secondIndex) {
        T dummy = backingArray[firstIndex];
        backingArray[firstIndex] = backingArray[secondIndex];
        backingArray[secondIndex] = dummy;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        nullCheck(data);
        if (size < backingArray.length - 2) {
            size++;
            backingArray[size] = data;
            upHeap(backingArray, size);
        } else {
            T[] newArray;
            newArray = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i <= size; i++) {
                newArray[i] = backingArray[i];
            }
            size++;
            newArray[size] = data;
            backingArray = newArray;
            upHeap(backingArray, size);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        elementCheck();
        T result = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(backingArray, 1);
        return result;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        elementCheck();
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

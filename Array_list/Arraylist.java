package Array_list;

public class ArrayList<T> {
    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        rangeCheck(index);
        nullCheck(data);
        if (size < backingArray.length) {
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
        } else {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int j = 0; j < index; j++) {
                newArray[j] = backingArray[j];
            }
            newArray[index] = data;
            for (int k = index; k < size; k++) {
                newArray[k + 1] = backingArray[k];
            }
            backingArray = newArray;
        }
        size++;
    }

    /**
     * A private method that checks if the index < 0 or index > size.
     *
     * @param index the index that we will check if index > size or index < 0
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     */
    private void rangeCheck(int index) {
        if (index > size || index < 0) {
            throw new java.lang.IndexOutOfBoundsException("The index is bigger than the size or smaller than 0");
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
            throw new java.lang.IllegalArgumentException("Null data can not be inserted in the array");
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        nullCheck(data);
        if (size < backingArray.length) {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
        } else {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            newArray[0] = data;
            for (int k = 0; k < size; k++) {
                newArray[k + 1] = backingArray[k];
            }
            backingArray = newArray;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        nullCheck(data);
        if (size == 0) {
            backingArray[0] = data;
        } else if (size < backingArray.length) {
            backingArray[size] = data;
        } else {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int j = 0; j < size; j++) {
                newArray[j] = backingArray[j];
            }
            newArray[size] = data;
            backingArray = newArray;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index >= size || index < 0) {
            throw new java.lang.IndexOutOfBoundsException("The index is bigger than the size or smaller than 0");
        }
        T result = backingArray[index];
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return result;
    }

    /**
     * A private method that checks if the the list is empty.
     *
     * @throws java.util.NoSuchElementException if the list is empty
     */
    private void elementCheck() {
        if (backingArray.length == 0) {
            throw new java.util.NoSuchElementException("This list has no element to remove.");
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        elementCheck();
        T result = backingArray[0];
        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return result;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        elementCheck();
        T result = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return result;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new java.lang.IndexOutOfBoundsException("The index is bigger than the size or smaller than 0");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (backingArray[0] == null);
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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

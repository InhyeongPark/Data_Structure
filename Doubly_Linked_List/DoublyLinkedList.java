public class DoublyLinkedList<T> {

    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new java.lang.IndexOutOfBoundsException("The index is bigger than the size or smaller than 0");
        }
        nullCheck(data);
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        DoublyLinkedListNode<T> currentNode;
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            if (index <= size / 2) {
                currentNode = head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.getNext();
                }
                newNode.setNext(currentNode.getNext());
                newNode.setPrevious(currentNode);
                currentNode.setNext(newNode);
                newNode.getNext().setPrevious(newNode);
            } else {
                currentNode = tail;
                for (int j = size - 1; j > index; j--) {
                    currentNode = currentNode.getPrevious();
                }
                newNode.setPrevious(currentNode.getPrevious());
                newNode.setNext(currentNode);
                newNode.getPrevious().setNext(newNode);
                currentNode.setPrevious(newNode);
            }
            size++;
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
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        nullCheck(data);
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        nullCheck(data);
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
    }

    /**
     * A private method that checks if the index < 0 or index >= size.
     *
     * @param index the index that we will check if index >= size or index < 0
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    private void rangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new java.lang.IndexOutOfBoundsException("The index is bigger than "
                    + "or same as the size or smaller than 0");
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        rangeCheck(index);
        T currentData;
        if (index == 0) {
            currentData = removeFromFront();
        } else if (index == size - 1) {
            currentData = removeFromBack();
        } else {
            DoublyLinkedListNode<T> currentNode;
            if (index <= size / 2) {
                currentNode = head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.getNext();
                }
                currentData = currentNode.getNext().getData();
                currentNode.setNext(currentNode.getNext().getNext());
                currentNode.getNext().setPrevious(currentNode);
            } else {
                currentNode = tail;
                for (int j = size - 1; j > index + 1; j--) {
                    currentNode = currentNode.getPrevious();
                }
                currentData = currentNode.getPrevious().getData();
                currentNode.setPrevious(currentNode.getPrevious().getPrevious());
                currentNode.getPrevious().setNext(currentNode);
            }
            size--;
        }
        return currentData;
    }

    /**
     * A private method that checks if the the list is empty.
     *
     * @throws java.util.NoSuchElementException if the list is empty
     */
    private void elementCheck() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("This list has no element to remove.");
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        elementCheck();
        T currentData = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return currentData;
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
        T currentData = tail.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return currentData;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        rangeCheck(index);
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> currentNode;
            if (index <= size / 2) {
                currentNode = head;
                for (int i = 0; i < index; i++) {
                    currentNode = currentNode.getNext();
                }
                return currentNode.getData();
            } else {
                currentNode = tail;
                for (int j = size - 1; j > index; j--) {
                    currentNode = currentNode.getPrevious();
                }
                return currentNode.getData();
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        nullCheck(data);
        T resultData = null;
        if (tail.getData().equals(data)) {
            resultData = removeFromBack();
        } else {
            DoublyLinkedListNode<T> currentNode = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (i == 0 && currentNode.getData().equals(data)) {
                    resultData = removeFromFront();
                    break;
                } else if (currentNode.getData().equals(data)) {
                    resultData = currentNode.getData();
                    currentNode.getNext().setPrevious(currentNode.getPrevious());
                    currentNode.getPrevious().setNext(currentNode.getNext());
                    size--;
                    break;
                }
                currentNode = currentNode.getPrevious();
            }
        }
        if (resultData == null) {
            throw new java.util.NoSuchElementException("The data that we want was not found in this list.");
        }
        return resultData;
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] newArray;
        if (size == 0) {
            newArray = new Object[0];
        } else {
            newArray = new Object[size];
            DoublyLinkedListNode<T> currentNode = head;
            for (int i = 0; i < size; i++) {
                newArray[i] = (Object) currentNode.getData();
                currentNode = currentNode.getNext();
            }
        }
        return newArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}

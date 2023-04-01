import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        size = 0;
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null element should not be in the array");
        } else {
            for (T smallData : data) {
                add(smallData);
            }
        }
    }

    /**
     * A private method that checks if the data is null.
     *
     * @param data the data that we will check if it's null or not
     * @throws java.lang.IllegalArgumentException if data or any element is null
     */
    private void nullCheck(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null data should not be in the array");
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        root = addHelper(root, data);
    }

    /**
     * Helper method to add a data to the BST
     *
     * This method will traverse the tree, and unless the data is
     * null or exists in the BST with the same data,
     * the node with given data becomes the leaf node in the tree
     *
     * @param data the data to add
     * @param node the node that we use to get the exact place to add the node
     * @return the child node to parent node
     * @throws java.lang.IllegalArgumentException if data or any element is null
     */
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {
        nullCheck(data);
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        elementCheck();
        nullCheck(data);
        BSTNode<T> resultNode = new BSTNode<>(null);
        root = removeHelper(root, data, resultNode);
        return resultNode.getData();
    }

    /**
     * Helper method to remove a data from the BST
     *
     * This method will traverse the tree, until it gets to the node
     * with the given data. Then, it will return the removed node's data
     *
     * @param data the data to remove
     * @param node the node that we use to get to the exact place to remove the node
     * @param resultNode the node that will contain the data we want to remove
     * @return the child node to parent node
     * @throws java.util.NoSuchElementException   if the data that we want to remove is not in the tree
     */
    private BSTNode<T> removeHelper(BSTNode<T> node, T data, BSTNode<T> resultNode) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data is not in this tree");
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelper(node.getLeft(), data, resultNode));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelper(node.getRight(), data, resultNode));
        } else {
            resultNode.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            } else if (node.getRight() != null && node.getLeft() == null) {
                return node.getRight();
            } else {
                BSTNode<T> helperNode = new BSTNode<>(null);
                node.setRight(removeHelperSuccessor(node.getRight(), helperNode));
                node.setData(helperNode.getData());
            }
        }
        return node;
    }


    /**
     * Helper method to find the successor node from the BST
     *
     * This method will traverse the tree, until it gets to successor node
     *
     * @param currNode the node that we will use to get to the leftmost node
     * @param helperNode the node that contains the successor node's data
     * @return the child node to parent node
     */
    private BSTNode<T> removeHelperSuccessor(BSTNode<T> currNode, BSTNode<T> helperNode) {
        if (currNode.getLeft() == null) {
            helperNode.setData(currNode.getData());
            return currNode.getRight();
        } else {
            currNode.setLeft(removeHelperSuccessor(currNode.getLeft(), helperNode));
        }
        return currNode;
    }

    /**
     * A private method that checks if the the list is empty.
     *
     * @throws java.util.NoSuchElementException if the list is empty
     */
    private void elementCheck() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("There is no node in this tree.");
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        nullCheck(data);
        elementCheck();
        BSTNode<T> resultNode = new BSTNode<>(null);
        root = getHelper(root, data, resultNode);
        if (resultNode.getData() == null) {
            throw new java.util.NoSuchElementException("The data is not in the tree");
        }
        return resultNode.getData();
    }

    /**
     * Helper method to get a data from the BST
     *
     * This method will traverse the tree until it gets
     * the right node that contains the given data
     *
     * @param data the data to search for
     * @param node the node that we use to get the node with given data
     * @param resultNode the node that will contain the data we want to get
     * @return the child node to parent node
     * @throws java.lang.IllegalArgumentException if data is null
     */
    private BSTNode<T> getHelper(BSTNode<T> node, T data, BSTNode<T> resultNode) {
        nullCheck(data);
        if (node != null) {
            if (node.getData().compareTo(data) == 0) {
                resultNode.setData(node.getData());
                return node;
            } else if (node.getData().compareTo(data) > 0) {
                node.setLeft(getHelper(node.getLeft(), data, resultNode));
            } else if (node.getData().compareTo(data) < 0) {
                node.setRight(getHelper(node.getRight(), data, resultNode));
            }
        }
        return node;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        nullCheck(data);
        if (size == 0) {
            return false;
        } else {
            T value;
            try {
                value = get(data);
            } catch (NoSuchElementException e) {
                return false;
            }
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> newList = new ArrayList<T>();
        if (size == 0) {
            return newList; // Return empty list
        }
        helpPreorder(root, newList);
        return newList;
    }

    /**
     * Help to generate a pre-order traversal of the tree.
     *
     * @param root the current node
     * @param list An arraylist that will store the data
     */
    private void helpPreorder(BSTNode<T> root, ArrayList<T> list) {
        if (root != null) {
            list.add(root.getData());
            helpPreorder(root.getLeft(), list);
            helpPreorder(root.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> newList = new ArrayList<T>();
        if (size == 0) {
            return newList; // Return an empty list
        }
        helpInorder(root, newList);
        return newList;
    }

    /**
     * Help to generate a in-order traversal of the tree.
     *
     * @param root the current node
     * @param list An arraylist that will store the data
     */
    private void helpInorder(BSTNode<T> root, ArrayList<T> list) {
        if (root != null) {
            helpInorder(root.getLeft(), list);
            list.add(root.getData());
            helpInorder(root.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> newList = new ArrayList<T>();
        if (size == 0) {
            return newList; // Return an empty list
        }
        helpPostorder(root, newList);
        return newList;
    }

    /**
     * Help to generate a post-order traversal of the tree.
     *
     * @param root the current node
     * @param list An arraylist that will store the data
     */
    private void helpPostorder(BSTNode<T> root, ArrayList<T> list) {
        if (root != null) {
            helpPostorder(root.getLeft(), list);
            helpPostorder(root.getRight(), list);
            list.add(root.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        if (size == 0) {
            return null;
        }
        Queue<BSTNode<T>> queue = new LinkedList<>();
        LinkedList<T> resultList = new LinkedList<>();
        BSTNode<T> currentNode;
        queue.add(root);
        while (queue.size() != 0) {
            currentNode = queue.remove();
            resultList.add(currentNode.getData());
            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
        return resultList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return helpHeight(root);
    }

    /**
     * Helps to return the height of the root of the tree
     *
     * @param node the current node to compare its left and right node's height
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int helpHeight(BSTNode<T> node) {
        if (node != null) {
            int left = helpHeight(node.getLeft());
            int right = helpHeight(node.getRight());
            if (right > left) {
                return (right + 1);
            } else {
                return (left + 1);
            }
        } else {
            return -1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (size == 0) {
            return null;
        }
        if (k > size) {
            throw new IllegalArgumentException("k should not be bigger than the size of the tree");
        }
        LinkedList<T> resultList = new LinkedList<>();
        helpFindingkLargest(root, resultList, k);
        return resultList;
    }

    /**
     *
     * Finds the k-largest elements from the BST in sorted order,
     *
     * @param node the current node
     * @param list the list to store the node
     * @param k the number of largest elements to return
     */
    private void helpFindingkLargest(BSTNode<T> node, LinkedList<T> list, int k) {
        if (node == null) {
            return;
        }
        helpFindingkLargest(node.getRight(), list, k);
        if (k > list.size()) {
            list.addFirst(node.getData());
        }
        if (k <= list.size()) {
            return;
        }
        helpFindingkLargest(node.getLeft(), list, k);
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

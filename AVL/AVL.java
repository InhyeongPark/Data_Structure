import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        size = 0;
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null element cannot be added");
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
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public void add(T data) {
        root = addHelper(root, data);
    }

    /**
     * Helper method to add a data to the AVL
     *
     * @param data the data to add
     * @param node the node that we use to get the exact place to add the node
     * @return the child node to the parent node
     * @throws java.lang.IllegalArgumentException if data or any element is null
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        nullCheck(data);
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        update(node);
        if (node.getBalanceFactor() == 2) {
            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            return rightRotation(node);
        } else if (node.getBalanceFactor() == -2) {
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotation(node.getRight()));
            }
            return leftRotation(node);
        }
        return node;
    }

    /**
     * Make a right rotation and update its height and balance factor
     *
     * @param node the node that you want to make a left rotation
     * @return the parent node after left rotation occurs
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        if (rightNode.getLeft() != null) {
            node.setRight(rightNode.getLeft());
        } else {
            node.setRight(null);
        }
        rightNode.setLeft(node);
        update(node);
        update(rightNode);
        return rightNode;
    }

    /**
     * Make a right rotation and update its height and balance factor
     *
     * @param node the node that you want to make a right rotation
     * @return the parent node after right rotation occurs
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        if (leftNode.getRight() != null) {
            node.setLeft(leftNode.getRight());
        } else {
            node.setLeft(null);
        }
        leftNode.setRight(node);
        update(node);
        update(leftNode);
        return leftNode;
    }

    /**
     * Updates the node's height and balance factor
     *
     * @param node the node that will be updated
     */
    private void update(AVLNode<T> node) {
        int leftHeight = node.getLeft() == null ? -1 : node.getLeft().getHeight();
        int rightHeight = node.getRight() == null ? -1 : node.getRight().getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        nullCheck(data);
        elementCheck();
        AVLNode<T> dummyNode = new AVLNode<>(null);
        root = removeHelper(root, data, dummyNode);
        return dummyNode.getData();
    }

    /**
     * A private method that checks if the the AVL is empty.
     *
     * @throws java.util.NoSuchElementException if the AVL is empty
     */
    private void elementCheck() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("There is no node in this AVL.");
        }
    }

    /**
     * Helper method to remove a data from the AVL
     * <p>
     * This method will traverse the AVL, until it gets to the node
     * with the given data. Then, it will return the removed node's data
     *
     * @param data       the data to remove
     * @param node       the node that we use to get to the exact place to remove the node
     * @param resultNode the node that will contain the data we want to remove
     * @return the child node to parent node
     * @throws java.util.NoSuchElementException if the data that we want to remove is not in the tree
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data, AVLNode<T> resultNode) {
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
                AVLNode<T> helperNode = new AVLNode<>(null);
                node.setRight(removeHelperSuccessor(node.getRight(), helperNode));
                node.setData(helperNode.getData());
            }
        }
        update(node);
        if (node.getBalanceFactor() == 2) {
            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            return rightRotation(node);
        } else if (node.getBalanceFactor() == -2) {
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotation(node.getRight()));
            }
            return leftRotation(node);
        }
        return node;
    }

    /**
     * Helper method to find the successor node from the AVL
     * <p>
     * This method will traverse the tree, until it gets to successor node
     *
     * @param currNode   the node that we will use to get to the leftmost node
     * @param helperNode the node that contains the successor node's data
     * @return the child node to parent node
     */
    private AVLNode<T> removeHelperSuccessor(AVLNode<T> currNode, AVLNode<T> helperNode) {
        if (currNode.getLeft() == null) {
            helperNode.setData(currNode.getData());
            return currNode.getRight();
        } else {
            currNode.setLeft(removeHelperSuccessor(currNode.getLeft(), helperNode));
        }
        update(currNode);
        if (currNode.getBalanceFactor() == 2) {
            if (currNode.getLeft().getBalanceFactor() == -1) {
                currNode.setLeft(leftRotation(currNode.getLeft()));
            }
            return rightRotation(currNode);
        } else if (currNode.getBalanceFactor() == -2) {
            if (currNode.getRight().getBalanceFactor() == 1) {
                currNode.setRight(rightRotation(currNode.getRight()));
            }
            return leftRotation(currNode);
        }
        return currNode;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T get(T data) {
        nullCheck(data);
        elementCheck();
        AVLNode<T> resultNode = new AVLNode<>(null);
        root = getHelper(root, data, resultNode);
        if (resultNode.getData() == null) {
            throw new java.util.NoSuchElementException("The data is not in the AVL");
        }
        return resultNode.getData();
    }


    /**
     * Helper method to get a data from the AVL
     * <p>
     * This method will traverse the tree until it gets
     * the right node that contains the given data
     *
     * @param data       the data to search for
     * @param node       the node that we use to get the node with given data
     * @param resultNode the node that will contain the data we want to get
     * @return the child node to parent node
     * @throws java.lang.IllegalArgumentException if data is null
     */
    private AVLNode<T> getHelper(AVLNode<T> node, T data, AVLNode<T> resultNode) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
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
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     * <p>
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     * <p>
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     * <p>
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        ArrayList<T> returnList = new ArrayList<>();
        if (size == 0) {
            return (List<T>) returnList;
        }
        helpDeepest(root, returnList);
        return (List<T>) returnList;
    }

    /**
     * Find the data on branches of the tree with the maximum depth
     * and store them on the array
     *
     * @param curr the current node
     * @param arr  the array to store the node
     */
    private void helpDeepest(AVLNode<T> curr, ArrayList<T> arr) {
//        if (curr == null) {
//            return;
//        }
//        arr.add(curr.getData());
//        if (curr.getBalanceFactor() > 0) {
//            helpDeepest(curr.getLeft(), arr);
//        } else if (curr.getBalanceFactor() < 0) {
//            helpDeepest(curr.getRight(), arr);
//        } else {
//            helpDeepest(curr.getLeft(), arr);
//            helpDeepest(curr.getRight(), arr);
//        }

        //Deepest
            if (curr == null) {
                return;
            }
            if (curr.getHeight() == 0) {
                arr.add(curr.getData());
            }
            if (curr.getBalanceFactor() >= 0) {
                helpDeepest(curr.getLeft(), arr);
            } else {
                helpDeepest(curr.getRight(), arr);
            }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     * <p>
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     *              or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new java.lang.IllegalArgumentException("Can't sort null values");
        }
        if (data1.compareTo(data2) > 0) {
            throw new java.lang.IllegalArgumentException("The second data should be bigger than the first one");
        }
        ArrayList<T> returnList = new ArrayList<>();
        if (size == 0 || data1.compareTo(data2) == 0) {
            return returnList;
        }
        helpSorting(root, returnList, data1, data2);
        return returnList;
    }

    /**
     * Find the data between data1 and data2 and store it in the list
     *
     * @param curr       the current node
     * @param returnList the list that stores the data
     * @param data1      the smaller data in the threshold
     * @param data2      the larger data in the threshold
     */
    private void helpSorting(AVLNode<T> curr, ArrayList<T> returnList, T data1, T data2) {
        if (curr == null) {
            return;
        }

        if (curr.getData().compareTo(data1) <= 0) {
            helpSorting(curr.getRight(), returnList, data1, data2);
        } else if (curr.getData().compareTo(data2) >= 0) {
            helpSorting(curr.getLeft(), returnList, data1, data2);
        } else if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) < 0) {
            helpSorting(curr.getLeft(), returnList, data1, data2);
            returnList.add(curr.getData());
            helpSorting(curr.getRight(), returnList, data1, data2);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return (size == 0 ? -1 : root.getHeight());
    }

    /**
     * Returns the size of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
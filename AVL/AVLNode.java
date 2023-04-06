public class AVLNode<T extends Comparable<? super T>> {
    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    private int balanceFactor;

    /**
     * Create an AVL node with the specified data.
     *
     * @param data the data to be stored in this node
     */
    public AVLNode(T data) {
        this.data = data;
    }

    /**
     * Get the data in this node.
     *
     * @return data in this node
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data in this node.
     *
     * @param data data to store in this node
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Get the node to the left of this node.
     *
     * @return node to the left of this node.
     */
    public AVLNode<T> getLeft() {
        return left;
    }

    /**
     * Set the node to the left of this node.
     *
     * @param left node to the left of this node
     */
    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    /**
     * Get the node to the right of this node.
     *
     * @return node to the right of this node.
     */
    public AVLNode<T> getRight() {
        return right;
    }

    /**
     * Set the node to the right of this node.
     *
     * @param right node to the right of this node
     */
    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    /**
     * Get the height of this node.
     *
     * @return height of this node
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height of this node.
     *
     * @param height height of this node
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get the balance factor of this node.
     *
     * @return balance factor of this node.
     */
    public int getBalanceFactor() {
        return balanceFactor;
    }

    /**
     * Set the balance factor of this node.
     *
     * @param balanceFactor balance factor of this node
     */
    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    /**
     * DO NOT USE EXCEPT FOR DEBUGGING PURPOSES
     */
    @Override
    public String toString() {
        return String.format("Node containing %s (height %d, bf %d)",
                data.toString(), height, balanceFactor);
    }
}
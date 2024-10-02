import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Nisha Shakya
 * @userid nshakya3
 * @GTID 903673864
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
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
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        for (T item : data) {

            if (item == null) {
                throw new IllegalArgumentException("The data is null.");
            }

            add(item);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        root = rAddHelper(root, data);
    }

    /**
     * Recursive add helper method.
     *
     * @param curr the current node
     * @param data the data to add
     * @return the current node
     */
    private AVLNode<T> rAddHelper(AVLNode<T> curr, T data) {

        if (curr == null) {
            size++;
            return new AVLNode<>(data);

        } else if (curr.getData().compareTo(data) < 0) {

            curr.setRight(rAddHelper(curr.getRight(), data));

        } else if (curr.getData().compareTo(data) > 0) {

            curr.setLeft(rAddHelper(curr.getLeft(), data));
        }

        // updateHeightAndBF(curr);
        return balance(curr);
    }

    /**
     * Balance method that performs rotations according to the balance factor.
     *
     * @param curr the current node
     * @return the current node
     */
    private AVLNode<T> balance(AVLNode<T> curr) {

        updateHeightAndBF(curr);

        if (curr.getBalanceFactor() >= 2) { // left heavy

            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotation(curr.getLeft()));

            }
            curr = rightRotation(curr);

        } else if (curr.getBalanceFactor() <= -2) { // right heavy

            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotation(curr.getRight()));
            }

            curr = leftRotation(curr);
        }

        return curr;

    }

    /**
     * Left rotation method.
     *
     * @param a the current node
     * @return b, the new root
     */
    private AVLNode<T> leftRotation(AVLNode<T> a) {

        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        updateHeightAndBF(a);
        updateHeightAndBF(b);
        return b;

    }

    /**
     * Right rotation method.
     *
     * @param c the current node
     * @return b, the new root
     */
    private AVLNode<T> rightRotation(AVLNode<T> c) {

        AVLNode<T> b = c.getLeft();
        c.setLeft(b.getRight());
        b.setRight(c);
        updateHeightAndBF(c);
        updateHeightAndBF(b);
        return b;
    }

    /**
     * Update method that calculates the height and balance factor.
     *
     * @param curr the current node
     */
    private void updateHeightAndBF(AVLNode<T> curr) {

        int leftHeight = easyHeight(curr.getLeft());
        int rightHeight = easyHeight(curr.getRight());
        curr.setHeight(Math.max(leftHeight, rightHeight) + 1);
        curr.setBalanceFactor(leftHeight - rightHeight);

    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        AVLNode<T> newNode = new AVLNode<>(null);
        root = rRemoveHelper(root, data, newNode);
        return newNode.getData();

    }

    /**
     * Recursive remove helper method.
     *
     * @param curr the current node
     * @param data the data to remove
     * @param dummy the node that stores the data to remove
     * @return the current node
     * @throws java.util.NoSuchElementException if the data is not found
     */
    private AVLNode<T> rRemoveHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {

        if (curr == null) {
            throw new NoSuchElementException("The data is not found.");
        }

        if (curr.getData().compareTo(data) < 0) {

            curr.setRight(rRemoveHelper(curr.getRight(), data, dummy));

        } else if (curr.getData().compareTo(data) > 0) {

            curr.setLeft(rRemoveHelper(curr.getLeft(), data, dummy));

        } else {

            dummy.setData(curr.getData());
            size--;

            if (curr.getLeft() == null && curr.getRight() == null) { // no children

                return null;

            } else if (curr.getRight() != null && curr.getLeft() == null) { // one child

                return curr.getRight();

            } else if (curr.getRight() == null && curr.getLeft() != null) { // one child

                return curr.getLeft();

            } else { // two children

                AVLNode<T> dummy2 = new AVLNode<T>(null);
                curr.setLeft(rPredecessorHelper(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }

        // updateHeightAndBF(curr);
        return balance(curr);
    }

    /**
     * Recursive predecessor method.
     *
     * @param curr the current node
     * @param predecessor the predecessor node
     * @return the current node
     */
    private AVLNode<T> rPredecessorHelper(AVLNode<T> curr, AVLNode<T> predecessor) {

        if (curr.getRight() == null) {

            predecessor.setData(curr.getData());
            return curr.getLeft();

        } else {

            curr.setRight(rPredecessorHelper(curr.getRight(), predecessor));
        }

        // updateHeightAndBF(curr);
        return balance(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        return rGetHelper(root, data);
    }

    /**
     * Recursive get helper method.
     *
     * @param curr the current node
     * @param data the data to search for
     * @return the current node's data
     * @throws java.util.NoSuchElementException if the data is not found
     */
    private T rGetHelper(AVLNode<T> curr, T data) {

        if (curr == null) {
            throw new NoSuchElementException("The data is not found.");
        }

        if (curr.getData().compareTo(data) < 0) {

            return rGetHelper(curr.getRight(), data);

        } else if (curr.getData().compareTo(data) > 0) {

            return rGetHelper(curr.getLeft(), data);

        }
        return curr.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        return rContainsHelper(root, data);

    }

    /**
     * Recursive contains helper method.
     *
     * @param curr the current node
     * @param data the data to search for
     * @return true if found, false if the node is null
     */
    private boolean rContainsHelper(AVLNode<T> curr, T data) {

        if (curr == null) {
            return false;
        }

        if (curr.getData().compareTo(data) < 0) {

            return rContainsHelper(curr.getRight(), data);

        } else if (curr.getData().compareTo(data) > 0) {

            return rContainsHelper(curr.getLeft(), data);
        }

        return true;
    }

    /**
     * Finds and retrieves the median data of the passed in AVL. 
     * 
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     * 
     * findMedian() should return 40
     *
     * @throws NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {

        if (root == null) {
            throw new NoSuchElementException("The tree is empty.");
        }

        if (size % 2 == 0) {
            throw new NoSuchElementException("The tree contains an even number of data.");
        }

        AVLNode<Integer> count = new AVLNode<>(0); // count node that keeps track of the # of nodes traversed
        AVLNode<T> medianNode = new AVLNode<>(null); // dummy node essentially that will store the data
        rMedianHelper(root, count, medianNode);
        return medianNode.getData();
    }

    /**
     * Recursive findMedian helper method that performs an in-order traversal.
     *
     * @param curr the current node
     * @param count the dummy node that keeps track of the number of nodes visited
     * @param medianNode the dummy node that stores the median data
     */
    private void rMedianHelper(AVLNode<T> curr, AVLNode<Integer> count, AVLNode<T> medianNode) {

        if (curr == null) {
            return;
        }

        if (count.getData() < (size / 2) + 1) {
            rMedianHelper(curr.getLeft(), count, medianNode);
            count.setData(count.getData() + 1);
        }

        if (count.getData() == (size / 2) + 1) {
            medianNode.setData(curr.getData());
            return;
        }

        if (count.getData() < (size / 2) + 1) {
            rMedianHelper(curr.getRight(), count, medianNode); // 75 is root
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
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

        return easyHeight(root);
    }

    /**
     * Easy height method.
     *
     * @param curr the current node
     * @return the height of the current node, -1 if the node is null
     */
    private int easyHeight(AVLNode<T> curr) {

        if (curr == null) {
            return -1;

        } else {
            return curr.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
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
     *
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
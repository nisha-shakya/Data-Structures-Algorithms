import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Nisha Shakya
 * @version 1.0
 * @userid nshakya3
 * @GTID 903673864
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * TA OH
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
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
    private BSTNode<T> rAddHelper(BSTNode<T> curr, T data) {

        if (curr == null) {
            size++;
            return new BSTNode<T>(data);

        }

        if (curr.getData().compareTo(data) < 0) {

            curr.setRight(rAddHelper(curr.getRight(), data));

        } else if (curr.getData().compareTo(data) > 0) {

            curr.setLeft(rAddHelper(curr.getLeft(), data));
        }

        return curr;
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

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        BSTNode<T> dummy = new BSTNode<T>(null);
        root = rRemoveHelper(root, data, dummy);
        return dummy.getData(); // data that was removed

    }

    /**
     * Recursive remove helper method.
     *
     * @param curr the current node
     * @param data the data to remove
     * @param dummy the node that stores the data to remove
     * @return the current node
     * @throws NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> rRemoveHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {

        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree.");
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

                BSTNode<T> dummy2 = new BSTNode<T>(null);
                curr.setRight(rRemoveSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }

        return curr;
    }

    /**
     * Recursive successor method.
     *
     * @param curr the current node
     * @param dummy2 the successor node
     * @return the current node
     */
    private BSTNode<T> rRemoveSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {

        if (curr.getLeft() == null) {

            dummy2.setData(curr.getData());
            return curr.getRight();

        } else {

            curr.setLeft(rRemoveSuccessor(curr.getLeft(), dummy2));
        }

        return curr;
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
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private T rGetHelper(BSTNode<T> curr, T data) {

        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        }

        if (curr.getData().compareTo(data) < 0) {

            return rGetHelper(curr.getRight(), data);

        } else if (curr.getData().compareTo(data) > 0) {

            return rGetHelper(curr.getLeft(), data);

        }
        return curr.getData();
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
    private boolean rContainsHelper(BSTNode<T> curr, T data) {

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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {

        LinkedList<T> preorderList = new LinkedList<T>();
        rPreorderHelper(root, preorderList);
        return preorderList;

    }

    /**
     * Recursive pre-order helper method.
     *
     * @param curr the current node
     * @param preorderList the linked list
     */
    private void rPreorderHelper(BSTNode<T> curr, LinkedList<T> preorderList) {

        if (curr == null) {
            return;
        }

        preorderList.add(curr.getData());
        rPreorderHelper(curr.getLeft(), preorderList);
        rPreorderHelper(curr.getRight(), preorderList);
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

        LinkedList<T> inorderList = new LinkedList<T>();
        rInorderHelper(root, inorderList);
        return inorderList;

    }

    /**
     * Recursive in-order helper method.
     *
     * @param curr the current node
     * @param inorderList the linked list
     */
    private void rInorderHelper(BSTNode<T> curr, LinkedList<T> inorderList) {

        if (curr == null) {
            return;
        }

        rInorderHelper(curr.getLeft(), inorderList);
        inorderList.add(curr.getData());
        rInorderHelper(curr.getRight(), inorderList);
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

        LinkedList<T> postorderList = new LinkedList<T>();
        rPostorderHelper(root, postorderList);
        return postorderList;

    }

    /**
     * Recursive post-order helper method.
     *
     * @param curr the current node
     * @param postorderList the linked list
     */
    private void rPostorderHelper(BSTNode<T> curr, LinkedList<T> postorderList) {

        if (curr == null) {
            return;
        }

        rPostorderHelper(curr.getLeft(), postorderList);
        rPostorderHelper(curr.getRight(), postorderList);
        postorderList.add(curr.getData());
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

        Queue<BSTNode<T>> q = new LinkedList<>();

        LinkedList<T> levelorderList = new LinkedList<T>();

        if (root != null) {
            q.add(root);
        }

        while (!q.isEmpty()) {

            BSTNode<T> curr = q.remove();
            levelorderList.add(curr.getData());

            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }

            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }

        return levelorderList;

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

        return rHeightHelper(root);

    }

    /**
     * Recursive height helper method.
     *
     * @param curr the current node
     * @return the height of the tree
     */
    private int rHeightHelper(BSTNode<T> curr) {

        if (curr == null) {
            return -1;
        }

        int leftHeight = rHeightHelper(curr.getLeft());
        int rightHeight = rHeightHelper(curr.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
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
     * Removes all elements strictly greater than the passed in data.
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
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     *             25
     *            /
     *          12
     *         /  \
     *        10  15
     *           /
     *          13
     *
     * Should have a running time of O(n) for a degenerate tree and O(log(n)) for a balanced tree.
     *
     * @throws java.lang.IllegalArgumentException if data is null
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T> the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        return rPruneHelper(tree, data);
        
    }

    /**
     * Recursive pruneGreaterThan helper method.
     *
     * @param curr the current node
     * @param data the threshold data
     * @param <T> the generic data type parameter
     * @return the current node
     */
    private static <T extends Comparable<? super T>> BSTNode<T> rPruneHelper(BSTNode<T> curr, T data) {

        if (curr == null) {
            return null;
        }

        if (data.compareTo(curr.getData()) < 0) {

            return rPruneHelper(curr.getLeft(), data);

        } else if (data.compareTo(curr.getData()) >= 0) {

            curr.setRight(rPruneHelper(curr.getRight(), data));

        }

        return curr;
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
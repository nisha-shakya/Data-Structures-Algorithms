import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Nisha Shakya
 * @version 1.0
 * @userid nshakya3
 * @GTID 903673864
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is less than 0 or index is greater than size.");
        }

        if (data == null) {

            throw new IllegalArgumentException("The data is null.");
        }

        // SinglyLinkedListNode<T> temp;
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data); // new node creation

        if (size == 0) {

            addToFront(data);

        } else if (index == 0) {

            addToFront(data);

        } else if (index == size) {

            addToBack(data);

        } else {

            SinglyLinkedListNode<T> temp = head;

            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }

            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data);

        if (size == 0) {

            head = newNode;
            tail = newNode;

        } else {

            newNode.setNext(head);
            head = newNode;

        }

        size++;

    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");

        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data);

        if (size == 0) {

            head = newNode;

        } else {

            tail.setNext(newNode);

        }

        tail = newNode;
        size++;

    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        T removeDataIndex;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or index is greater than or equal to size.");
        }

        if (index == 0) {

            return removeFromFront();

        } else if (index == size - 1) {

            return removeFromBack();

        } else {

            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }

            removeDataIndex = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            return removeDataIndex;

        }

        // size--;
        // return removeDataIndex;

    }

    /**
     * Removes and returns the first data of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {

        // T removedDataFront;

        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        }

        T removedDataFront = head.getData();

        if (size == 1) {

            head = null;
            tail = null;

        } else {

            head = head.getNext();
        }

        size--;
        return removedDataFront;

    }

    /**
     * Removes and returns the last data of the list.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {

        T removedDataBack;

        if (head == null) {
            throw new NoSuchElementException("The list is empty.");

        }
        removedDataBack = tail.getData();

        if (size == 1) {

            head = null;
            tail = null;

        } else {

            SinglyLinkedListNode<T> secondLast = head;

            while (secondLast.getNext().getNext() != null) {
                secondLast = secondLast.getNext();
            }

            secondLast.setNext(null);
            tail = secondLast;
        }

        size--;
        return removedDataBack;

    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or index is greater than or equal to size.");
        }

        if (index == 0) {

            return head.getData();

        } else if (index == size - 1) {

            return tail.getData();

        } else {

            SinglyLinkedListNode<T> current = head;

            for (int i = 0; i < index; i++) { // index because you want to access THAT index
                current = current.getNext();
            }

            return current.getData();

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

        //return size == 0;
        return head == null;
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
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        if (head == null) {
            throw new NoSuchElementException("The data is not found.");
        }

        SinglyLinkedListNode<T> prev = null;
        SinglyLinkedListNode<T> current = head;
        // T tailData = tail.getData();

        for (int i = 0; i < size - 1; i++) {

            if (current.getNext().getData().equals(data)) {
                prev = current;
            }

            current = current.getNext();

        }

        if (prev == null) {

            if (head.getData().equals(data)) {

                return removeFromFront();
            }

            throw new NoSuchElementException("The data is not found.");

        }

        T tailData = tail.getData();

        if (tail.getData().equals(data)) {

            prev.setNext(null);
            tail = prev;
            size--;
            return tailData;

        }

        T removeLastOccurrence = prev.getNext().getData();
        prev.setNext(prev.getNext().getNext());
        size--;
        return removeLastOccurrence;

    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {

        T[] linkedListArray = (T[]) new Object[size];

        SinglyLinkedListNode<T> current = head;

        for (int i = 0; i < linkedListArray.length; i++) {
            linkedListArray[i] = current.getData();
            current = current.getNext();
        }

        return linkedListArray;

    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
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
    public SinglyLinkedListNode<T> getTail() {
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

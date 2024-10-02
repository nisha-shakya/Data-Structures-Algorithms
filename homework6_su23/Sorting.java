import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {

        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }

            T temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;

        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {

        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;

            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {

        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        int end = arr.length - 1;

        while (0 < end) {

            int currentSwap = 0;

            for (int j = 0; j < end; j++) {

                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    currentSwap = j;
                }
            }

            end = currentSwap;

        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {

        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        if (arr.length <= 1) {
            return;
        }

        int middle = arr.length / 2;
        T[] leftArray = (T[]) new Object[middle];
        T[] rightArray = (T[]) new Object[arr.length - middle];

        int r = 0; // right array index

        for (int l = 0; l < arr.length; l++) { // l is left array index

            if (l < middle) {
                leftArray[l] = arr[l];

            } else {
                rightArray[r] = arr[l];
                r++;
            }
        }

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);
        merge(arr, leftArray, rightArray, comparator);
        
    }

    /**
     * Merge helper method for mergeSort.
     *
     * @param arr the current array
     * @param leftArray the left array
     * @param rightArray the right array
     * @param comparator the Comparator used to compare the data in the left and right arr
     * @param <T> the data type to sort
     */
    private static <T> void merge(T[] arr, T[] leftArray, T[] rightArray, Comparator<T> comparator) {

        int leftSize = arr.length / 2;
        int rightSize = arr.length - leftSize;
        int i = 0; // arr index
        int l = 0; // left array index
        int r = 0; // right array index

        while (l < leftSize && r < rightSize) {

            if (comparator.compare(leftArray[l], rightArray[r]) <= 0) {
                arr[i] = leftArray[l];
                i++;
                l++;

            } else {
                arr[i] = rightArray[r];
                i++;
                r++;
            }
        }

        while (l < leftSize) {
            arr[i] = leftArray[l];
            i++;
            l++;
        }

        while (r < rightSize) {
            arr[i] = rightArray[r];
            i++;
            r++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {

        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }

        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];

        if (arr.length == 0) { // array is empty so nothing to sort
            return;
        }

        int max = arr[0];

        for (int i = 0; i < arr.length; i++) {
            if (max < Math.abs(arr[i])) {
                max = Math.abs(arr[i]);
            }
            if (arr[i] == Integer.MIN_VALUE) {
                max = Integer.MAX_VALUE;
            }
        }

        int counter = 0; // k, keeps track of the # of digits in the greatest # of magnitude
        while (max != 0) {

            max = max / 10;
            counter++;

        }

        int base = 1;
        for (int i = 0; i < counter; i++) {

            if (i != 0) {
                base *= 10;
            }

            for (int j = 0; j < arr.length; j++) {
                int bIndex = (arr[j] / base) % 10 + 9;

                if (buckets[bIndex] == null) {
                    buckets[bIndex] = new LinkedList<>();
                }
                buckets[bIndex].add(arr[j]); // add to back
            }

            int arrIndex = 0;

            for (int k = 0; k < buckets.length; k++) {

                if (buckets[k] != null) {
                    while (!buckets[k].isEmpty()) {
                        arr[arrIndex] = buckets[k].removeFirst(); // remove from front
                        arrIndex++;
                    }
                }
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        PriorityQueue<Integer> q = new PriorityQueue<>(data);

        int[] sortedArray = new int[data.size()];

        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = q.remove();

        }

        return sortedArray;

    }
}
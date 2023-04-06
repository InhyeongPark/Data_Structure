import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Sorting {

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
        nullCheck(arr, comparator);
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        int j = 0;
        for (int i = 1; i < arr.length; i++) {
            j = i;
            while (j != 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                swap(arr, j, j - 1);
                j--;
            }
        }
    }

    /**
     * A private method that checks if the array or comparator is null.
     *
     * @param <T> data type to check if it is null
     * @param arr the array to check if it is null or not
     * @param comparator the Comparator to check if it is null or not
     */
    private static <T> void nullCheck(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("The array or comparator should not be null");
        }
    }

    /**
     * A private method that swap two data
     *
     * @param <T> data type to swap
     * @param arr the array that is storing the data
     * @param first the index to change with the second index
     * @param second the index to change with the first index
     */
    private static <T> void swap(T[] arr, int first, int second) {
        T dummy = arr[first];
        arr[first] = arr[second];
        arr[second] = dummy;
    }

    /**
     * Implement cocktail sort.
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
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        nullCheck(arr, comparator);
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        boolean didSwap = true;
        int start = 0;
        int end = arr.length - 1;
        int i = 0;
        int j = 0;
        while (didSwap) {
            didSwap = false;
            for (i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    didSwap = true;
                    j = i;
                }
            }
            end = j;
            if (didSwap) {
                didSwap = false;
                for (i = end; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        swap(arr, i - 1, i);
                        didSwap = true;
                        j = i;
                    }
                }
            }
            start = j;
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
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        nullCheck(arr, comparator);
        if (arr.length == 0 || arr.length == 1) {   // Additional check if it's empty
            return;
        }
        int len = arr.length;
        int mid = len / 2;
        T[] left = (T[]) new Object[mid];
        T[] right = (T[]) new Object[len - mid];
        int i = 0;
        int j = 0;
        for (i = 0; i < mid; i++) {
            left[i] = arr[i];
        }
        i = 0;
        for (j = mid; j < len; j++) {
            right[i] = arr[j];
            i++;
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        i = 0;
        j = 0;
        while (i != left.length && j != right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
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
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        nullCheck(arr, comparator);
        if (rand == null) {
            throw new java.lang.IllegalArgumentException("The random object should also not be null");
        }
        helpQuickSorting(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * A helper method to implement QuickSort
     *
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start the index where to start comparing from left to right
     * @param end the index where to start comparing from right to left
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    private static <T> void helpQuickSorting(T[] arr, Comparator<T> comparator,
                                     Random rand, int start, int end) {
        nullCheck(arr, comparator);
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIndex];
        swap(arr, start, pivotIndex);
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(pivotVal, arr[i]) >= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        helpQuickSorting(arr, comparator, rand, start, j - 1);
        helpQuickSorting(arr, comparator, rand, j + 1, end);
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
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
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
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array should not be null");
        }
        Queue<Integer>[] buckets = new Queue[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        if (arr.length > 0) {
            int count = 0;
            int max = Math.abs(arr[0]);
            for (int part : arr) {
                if (max < Math.abs(part)) {
                    max = part;
                }
            }
            while (max != 0) {
                max /= 10;
                count++;
            }

            int dividing = 1;
            int index = 0;
            for (int i = 0; i <= count; i++) {
                for (int j = 0; j < arr.length; j++) {
                    index = (arr[j] / dividing) % 10;
                    buckets[index + 9].add(arr[j]);
                }
                index = 0;
                for (Queue<Integer> bucket : buckets) {
                    while (!bucket.isEmpty()) {
                        arr[index++] = bucket.remove();
                    }
                }
                dividing *= 10;
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
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The list should not be null");
        }
        int[] returnArray = new int[data.size()];
        if (data.size() != 0) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(data);
            for (int i = 0; i < data.size(); i++) {
                returnArray[i] = pq.remove();
            }
        }
        return returnArray;
    }
}

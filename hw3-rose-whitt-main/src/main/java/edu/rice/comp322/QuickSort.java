package edu.rice.comp322;

import edu.rice.hj.api.HjFinishAccumulator;
import edu.rice.hj.api.HjOperator;
import edu.rice.hj.api.SuspendableException;
import edu.rice.hj.runtime.accumulator.FinishAccumulator;

import javax.xml.crypto.Data;
import java.util.Random;
import static edu.rice.hj.Module0.*;
import static edu.rice.hj.Module1.*;
import static edu.rice.hj.Module2.*;

/**
 * Description available at http://en.wikipedia.org/wiki/Quicksort
 *
 * @author <a href="http://shams.web.rice.edu/">Shams Imam</a> (shams@rice.edu)
 */
public class QuickSort extends Sort {

    @Override
    protected void seqSort(final DataItem[] dataArray) {
        recursiveQuickSort(dataArray, 0, dataArray.length - 1);
    }

    /**
     * Sorting algorithm using parallel, recursive quick sort helper
     * @param dataArray
     * @throws SuspendableException
     */
    protected void parSort(final DataItem[] dataArray) throws SuspendableException {
        parRecursiveQuickSort(dataArray, 0, dataArray.length - 1);
    }

    /**
     * Method that sorts an array using the quicksort algorithm in parallel.
     *
     * @param inA - Array of comparable objects
     * @param inM - the lower bounds of the area to sort
     * @param inN - the upper bounds of the area to sort
     */
    private void parRecursiveQuickSort(final DataItem[] inA, final int inM, final int inN) throws SuspendableException {
        if (inM < inN) {
            final int[] p = partition(inA, inM, inN);
            int inI = p[0];
            int inJ = p[1];
            finish(() -> {
                async(() -> {
                    parRecursiveQuickSort(inA, inM, inI);
                });
                async(() -> {
                    parRecursiveQuickSort(inA, inJ, inN);
                });
            });

        }
    }


    /**
     * Method that sorts an array using the quicksort algorithm.
     *
     * @param inA - Array of comparable objects
     * @param inM - the lower bounds of the area to sort
     * @param inN - the upper bounds of the area to sort
     */
    private void recursiveQuickSort(final DataItem[] inA, final int inM, final int inN) {
        if (inM < inN) {
            final int[] p = partition(inA, inM, inN);
            int inI = p[0];
            int inJ = p[1];
            recursiveQuickSort(inA, inM, inI);
            recursiveQuickSort(inA, inJ, inN);
        }
    }

    /**
     * Mutates an array such that between two points in the array, all points less then a pivot point are to the left of
     * that point, and all points greater then a pivot point are to the right of that point.
     *
     * @param inA - a comparable array
     * @param inM - the lower bounds of the area to partition
     * @param inN - the upper bounds of the area to partition
     * @return - A point consisting of two locations around the pivot point
     */
    @SuppressWarnings("unchecked")
    private int[] partition(final DataItem[] inA, final int inM, final int inN) {

        int inI;
        int storeIndex = inM;
        final Random rand = new Random(inM + inN);
        final int pivot = inM + rand.nextInt(inN - inM + 1);
        final DataItem pivotValue = inA[pivot];

        exchange(inA, pivot, inN);
        for (inI = inM; inI < inN; inI++) {
            if (inA[inI].compareTo(pivotValue) <= 0) {
                exchange(inA, inI, storeIndex);
                storeIndex++;
            }
        }
        exchange(inA, storeIndex, inN);

        if (storeIndex == inN) {
            return new int[]{storeIndex - 1, inN};
        } else if (storeIndex == inM) {
            return new int[]{inM, storeIndex + 1};
        }
        return new int[]{storeIndex - 1, storeIndex + 1};
    }
}

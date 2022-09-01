package edu.rice.comp322;

import edu.rice.hj.api.HjMetrics;
import edu.rice.hj.api.HjSuspendingProcedure;
import edu.rice.hj.runtime.config.HjSystemProperty;

import java.util.Random;

import static edu.rice.hj.Module1.*;

/**
 * <p>Homework3 class.</p>
 *
 * @author Vivek Sarkar, Mack Joyner (vsarkar@rice.edu, mjoyner@rice.edu)
 */
public class Homework3 {

    /**
     * Sort based on provided sorting algorithm.
     * @return  The implemented sorting algorithm.
     */
    protected static Sort sortInstance() {
        // TODO return the comparison sort algorithm you want considered for this assignment
        return new QuickSort();
    }
}


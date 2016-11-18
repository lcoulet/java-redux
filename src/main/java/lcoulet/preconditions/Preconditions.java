/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.preconditions;

/**
 * a simple preconditions class, because I do not want to import Guava and I
 * don't like java asserts
 *
 * @author Loic.Coulet
 */
public class Preconditions {

    /**
     * Checks that the string is non-null nor empty
     *
     * @param toCheck parameter to check
     * @param message message to send in case of failure
     *
     * @throws IllegalArgumentException if condition is false with the
     * associated message
     */
    public static final void checkStringArgumentContents(String toCheck, String message) {
        if (toCheck == null || "".equals(toCheck)) {
            throw new IllegalArgumentException(String.valueOf(message));
        }
    }


    /**
     * Checks for a condition validity
     *
     * @param check condition to check
     * @param message message to send in case of failure
     *
     * @throws IllegalStateException if condition is false with the associated
     * message
     */
    public static final void checkState(boolean check, String message) {
        if (!check) {
            throw new IllegalStateException(String.valueOf(message));
        }
    }

    /**
     * Checks for a condition validity
     *
     * @param check condition to check
     * @param message message to send in case of failure
     *
     * @throws IllegalArgumentException if condition is false with the
     * associated message
     */
    public static final void checkArgument(boolean check, String message) {
        if (!check) {
            throw new IllegalArgumentException(String.valueOf(message));
        }
    }

    /**
     * Checks for a non-nullable reference
     *
     * @param toCheck item to check
     * @param message message to send in case of failure
     *
     * @throws NullPointerException if toCheck is null
     */
    public static final void checkNotNull(Object toCheck, String message) {
        if (toCheck == null) {
            throw new NullPointerException(String.valueOf(message));
        }
    }


}

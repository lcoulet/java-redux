/*
 * Copyright 2016 Loic.Coulet.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

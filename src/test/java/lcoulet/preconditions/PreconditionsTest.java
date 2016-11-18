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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Loic.Coulet
 */
public class PreconditionsTest {

    public PreconditionsTest() {
    }

    @Test
    public void checkImproperArgument() {
        try {
            Preconditions.checkArgument(false, FAIL_MSG);
            fail("Should have sent an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(FAIL_MSG, e.getMessage());
        }
    }

    @Test
    public void checkImproperArgumentWithNullMessage() {
        try {
            Preconditions.checkArgument(false, null);
            fail("Should have sent an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("null", e.getMessage());
        }
    }

    @Test
    public void checkValidArgument() {
        // test seems empty this is normal
        // - just checking that no exception is thrown. Tewst would fail otherwise.
        Preconditions.checkArgument(true, FAIL_MSG);
    }

    @Test
    public void checkNotNull() {
        // test seems empty this is normal
        // - just checking that no exception is thrown. Tewst would fail otherwise.
        Preconditions.checkNotNull("", FAIL_MSG);
    }

    @Test
    public void checkNotNullSendsAnExceptionWhenNullGiven() {
        try {
            Preconditions.checkNotNull(null, FAIL_MSG);
            fail("Should have sent an exception");
        } catch (NullPointerException e) {
            assertEquals(FAIL_MSG, e.getMessage());
        }
    }

    @Test
    public void checkNotNullSendsAnExceptionWhenNullGivenAndNullmessage() {
        try {
            Preconditions.checkNotNull(null, null);
            fail("Should have sent an exception");
        } catch (NullPointerException e) {
            assertEquals("null", e.getMessage());
        }
    }

    @Test
    public void checkNonVoidStringSendsAnExceptionWhenNullGiven() {
        try {
            Preconditions.checkStringArgumentContents(null, FAIL_MSG);
            fail("Should have sent an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(FAIL_MSG, e.getMessage());
        }
    }

    @Test
    public void checkNonVoidStringSendsAnExceptionWhenNullGivenAndNullMsg() {
        try {
            Preconditions.checkStringArgumentContents(null, null);
            fail("Should have sent an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(NULL_MSG, e.getMessage());
        }
    }

    @Test
    public void checkNonVoidStringValidGiven() {
        // test seems empty this is normal
        // - just checking that no exception is thrown. Tewst would fail otherwise.
        Preconditions.checkStringArgumentContents("ABCDEF", null);
    }

    @Test
    public void checkNonVoidStringSendsExceptionWhenEmptyGiven() {
        try {
            Preconditions.checkStringArgumentContents("", FAIL_MSG);
            fail("Should have sent an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(FAIL_MSG, e.getMessage());
        }
    }

    public static final String NULL_MSG = "null";
    public static final String FAIL_MSG = "Failed the test";

}

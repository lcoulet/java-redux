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
package lcoulet.redux;

import static lcoulet.redux.CounterState.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Loic.Coulet
 */
public class TypeSafeReducerTest {

    public TypeSafeReducerTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testSomeMethod() {
    }

    @Test
    public void typedReducerSHouldNotSendExceptionWhenImproperType() {
        State dummyState = new DummyState();
        Reducer instance = new TypeSafeReducer(CombinedReducer.create()
                .with("Inc1", INCREMENT_REDUCER)
                .with("Reset", RESET_REDUCER));

        State results = instance.apply(dummyState, RESET_OR_INC_COUNTER);

        assertTrue("State should be unchanged", results == dummyState);

    }

    @Test
    public void typedReducerSHouldNotSendExceptionWhenImproperActionType() {
        CounterState inputState = new CounterState(0);
        Reducer instance = new TypeSafeReducer(CombinedReducer.create()
                .with("Inc1", INCREMENT_REDUCER)
                .with("Reset", RESET_REDUCER));

        State results = instance.apply(inputState, new Action() {
        });

        assertTrue("State should be unchanged", results == inputState);

    }

    @Test
    public void typedReducerShouldApplyOnProperType() {
        Reducer instance = new TypeSafeReducer(ReducingChain.create()
                .with(INCREMENT_REDUCER)
                .with(RESET_REDUCER));

        CounterState results = (CounterState) instance.apply(new CounterState(0), COUNTER_PLUS_1110);
        assertEquals("State should be changed properly", 1110, ((CounterState) results).counter);
    }


}

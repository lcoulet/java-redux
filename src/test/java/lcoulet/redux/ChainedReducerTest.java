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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Loic.Coulet
 */
public class ChainedReducerTest {

    public ChainedReducerTest() {
    }

    @Before
    public void setUp() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void chainWithNullReducerShallFail() {
        ReducingChain instance = ReducingChain.create();
        instance.with((Reducer) null);
    }

    @Test
    public void combineWithEmpty() {
        ReducingChain instance = ReducingChain.create().with(Reducer.NULL_REDUCER);
        assertTrue("should return the same state", State.NULL_STATE == instance.apply(State.NULL_STATE, Action.NULL_ACTION));
    }


    @Test
    public void chainingAppliesInOrder() {
        ReducingChain<CounterState, Action> instance = ReducingChain.create()
                .with(RESET_REDUCER)
                .with(INCREMENT_REDUCER);

        CounterState s = new CounterState(0);
        s = instance.apply(s, INCREMENT_COUNTER);
        assertEquals(1, s.counter);
        s = instance.apply(s, INCREMENT_COUNTER);
        assertEquals(2, s.counter);
        s = instance.apply(s, COUNTER_PLUS_1110);
        assertEquals(1112, s.counter);
        s = instance.apply(s, RESET_OR_INC_COUNTER);
        assertEquals(1, s.counter);

        instance = ReducingChain.create()
                .with(INCREMENT_REDUCER)
                .with(RESET_REDUCER);

        s = new CounterState(0);
        s = instance.apply(s, INCREMENT_COUNTER);
        assertEquals(1, s.counter);
        s = instance.apply(s, INCREMENT_COUNTER);
        assertEquals(2, s.counter);
        s = instance.apply(s, COUNTER_PLUS_1110);
        assertEquals(1112, s.counter);
        s = instance.apply(s, RESET_OR_INC_COUNTER);
        assertEquals(0, s.counter);

    }




}

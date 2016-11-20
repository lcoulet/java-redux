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
public class CombinedReducerTest {

    public CombinedReducerTest() {
    }

    @Before
    public void setUp() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void combineWithNullMemberNameShallFail() {
        CombinedReducer.create().with(null, Reducer.NULL_REDUCER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void combineWithEmptyMemberNameShallFail() {
        CombinedReducer.create().with("", Reducer.NULL_REDUCER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void combineWithNullReducerShallFail() {
        CombinedReducer.create().with("Test", null);
    }


    @Test
    public void combineAndApplyToCompositeState() {
        CompositeState s = CompositeState.create()
                .with("counter1", new CounterState(1))
                .with("counter2", new CounterState(2));

        ChainedReducer<CounterState, Action> reducer2 = ChainedReducer.create()
                .with(RESET_REDUCER)
                .with(INCREMENT_REDUCER);

        CombinedReducer instance = CombinedReducer.create()
                .with("counter1", INCREMENT_REDUCER)
                .with("counter2", reducer2);

        s = instance.apply(s, INCREMENT_COUNTER);
        assertEquals(2, ((CounterState) s.getMember("counter1")).counter);
        assertEquals(3, ((CounterState) s.getMember("counter2")).counter);
        s = (CompositeState) instance.apply(s, INCREMENT_COUNTER);
        s = (CompositeState) instance.apply(s, INCREMENT_COUNTER);
        assertEquals(4, ((CounterState) s.getMember("counter1")).counter);
        assertEquals(5, ((CounterState) s.getMember("counter2")).counter);
        s = (CompositeState) instance.apply(s, COUNTER_PLUS_1110);
        assertEquals(1114, ((CounterState) s.getMember("counter1")).counter);
        assertEquals(1115, ((CounterState) s.getMember("counter2")).counter);
        s = (CompositeState) instance.apply(s, RESET_OR_INC_COUNTER);
        assertEquals(1115, ((CounterState) s.getMember("counter1")).counter);
        assertEquals(1, ((CounterState) s.getMember("counter2")).counter);


    }





}

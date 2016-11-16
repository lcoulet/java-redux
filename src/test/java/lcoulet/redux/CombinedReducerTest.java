/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public void combineWithEmpty() {
        CombinedReducer instance = CombinedReducer.create().with("Test", Reducer.NULL_REDUCER);
        assertTrue("should return the same state", State.NULL_STATE == instance.apply(State.NULL_STATE, new Action() {
        }));
    }

    @Test
    public void combineWithCompositeState() {
        CompositeState s = CompositeState.create()
                .with("counter1", new CounterState(1))
                .with("counter2", new CounterState(2));

        CombinedReducer instance = CombinedReducer.create()
                .with("counter1", INCREMENT_REDUCER)
                .with("counter2", CombinedReducer.create()
                        .with("c2_Reset", RESET_REDUCER)
                        .with("c2_Inc1", INCREMENT_REDUCER));

        s = (CompositeState) instance.apply(s, INCREMENT_COUNTER);
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

    @Test
    public void combineAndVerifyChainingOrder() {
        CombinedReducer instance = CombinedReducer.create()
                .with("Reset", RESET_REDUCER)
                .with("Inc1", INCREMENT_REDUCER);

        CounterState s = new CounterState(0);
        s = (CounterState) instance.apply(s, INCREMENT_COUNTER);
        assertEquals(1, s.counter);
        s = (CounterState) instance.apply(s, INCREMENT_COUNTER);
        assertEquals(2, s.counter);
        s = (CounterState) instance.apply(s, COUNTER_PLUS_1110);
        assertEquals(1112, s.counter);
        s = (CounterState) instance.apply(s, RESET_OR_INC_COUNTER);
        assertEquals(1, s.counter);

        instance = CombinedReducer.create()
                .with("Inc1", INCREMENT_REDUCER)
                .with("Reset", RESET_REDUCER);

        s = new CounterState(0);
        s = (CounterState) instance.apply(s, INCREMENT_COUNTER);
        assertEquals(1, s.counter);
        s = (CounterState) instance.apply(s, INCREMENT_COUNTER);
        assertEquals(2, s.counter);
        s = (CounterState) instance.apply(s, COUNTER_PLUS_1110);
        assertEquals(1112, s.counter);
        s = (CounterState) instance.apply(s, RESET_OR_INC_COUNTER);
        assertEquals(0, s.counter);

    }




}

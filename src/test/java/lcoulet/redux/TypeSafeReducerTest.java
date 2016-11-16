/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.redux;

import static lcoulet.redux.CounterState.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        Reducer instance = new TypeSafeReducer<DummyState>(CombinedReducer.create()
                .with("Inc1", INCREMENT_REDUCER)
                .with("Reset", RESET_REDUCER));

        State results = instance.apply(dummyState, RESET_OR_INC_COUNTER);

        assertTrue(results == dummyState);

        results = instance.apply(new CounterState(0), COUNTER_PLUS_1110);
        assertEquals(1110, ((CounterState) results).counter);

    }

}

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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Loic.Coulet
 */
public class StoreTest {

    public StoreTest() {
    }

    @Before
    public void setUp() {
    }

    class CounterSubscriber implements Subscriber<CounterState> {

        private int notifications = 0;

        @Override
        public void stateChanged(CounterState newState) {
            notifications++;
        }

        public boolean hasBeenNotified() {
            return (notifications > 0);
        }

    }

    @Test
    public void aTriggeredChangeShouldShowItsEffect() {
        CounterSubscriber witness = new CounterSubscriber();

        Store<CounterState> store = createCounterStore(witness);

        store.dispatch(CounterState.INCREMENT_COUNTER);
        assertEquals("action should have had an effect on value", 1, store.getCurrentState().counter);
        assertEquals("action should have had triggered a notification", 1, witness.notifications);

    }

    @Test
    public void noTriggeredChangeShouldHaveNoEffect() {
        CounterSubscriber witness = new CounterSubscriber();

        Store<CounterState> store = createCounterStore(witness);

        store.dispatch(Action.NULL_ACTION);

        assertEquals("action should have had no effect on value", 0, store.getCurrentState().counter);
        assertFalse("action should not have triggered a notification", witness.hasBeenNotified());
    }

    @Test
    public void chainedStateChangeShouldHaveASingleEffect() {
        CounterSubscriber witness1 = new CounterSubscriber();
        CounterSubscriber witness2 = new CounterSubscriber();

        Store<CounterState> store = Store.create(new CounterState(0),
                ChainedReducer.create()
                .with(CounterState.INCREMENT_REDUCER)
                .with(CounterState.INCREMENT_REDUCER)
                .with(CounterState.INCREMENT_REDUCER));

        store.subscribe(witness1);
        store.subscribe(witness2); // another subscriber just to make sure it has no effect on nb notifications

        store.dispatch(Action.NULL_ACTION);
        store.dispatch(CounterState.INCREMENT_COUNTER);

        assertEquals("action should have had no effect on value", 3, store.getCurrentState().counter);
        assertEquals("action should have triggered exactly one notification", 1, witness1.notifications);
        assertEquals("action should have triggered exactly one notification", 1, witness2.notifications);

    }

    @Test
    public void unSubscribeShouldRemoveSubscriber() {
        CounterSubscriber witness = new CounterSubscriber();

        Store<CounterState> store = createCounterStore(witness);
        
        store.dispatch(CounterState.INCREMENT_COUNTER);
        assertEquals("action should have had an effect on value", 1, store.getCurrentState().counter);
        assertEquals("action should have triggered exactly one notification", 1, witness.notifications);

        store.unsubscribe(witness);
        store.dispatch(CounterState.INCREMENT_COUNTER);
        assertEquals("action should have had an effect on value", 2, store.getCurrentState().counter);
        assertEquals("action should have triggered no notification", 1, witness.notifications);

    }

    @Test
    public void subscribingTwiceShouldConsiderASingleSubscriber() {
        CounterSubscriber witness = new CounterSubscriber();

        Store<CounterState> store = createCounterStore(witness);
        store.subscribe(witness);

        store.dispatch(CounterState.INCREMENT_COUNTER);
        assertEquals("action should have had an effect on value", 1, store.getCurrentState().counter);
        assertEquals("action should have triggered exactly one notification", 1, witness.notifications);

    }

    private class DefectiveDesignReducer implements Reducer<CounterState> {
        Store store;

        @Override
        public CounterState apply(CounterState currentState, Action action) {
            store.dispatch(CounterState.RESET_OR_INC_COUNTER);
            return currentState;
        }
    }

    @Test(expected = IllegalStateException.class)
    public void reducerThatAffetStoreShallBeForbidden() {

        DefectiveDesignReducer reducer = new DefectiveDesignReducer();
        Store<CounterState> store = Store.create(new CounterState(0), reducer);
        reducer.store = store;

        store.dispatch(CounterState.INCREMENT_COUNTER);
    }

    @Test(expected = NullPointerException.class)
    public void nulLSubscriberShouldFail() {

        Store<CounterState> store = createCounterStore(null);

    }

    public Store<CounterState> createCounterStore(CounterSubscriber witness) {
        Store<CounterState> store = Store.create(new CounterState(0), CounterState.INCREMENT_REDUCER);
        store.subscribe(witness);
        return store;
    }


}

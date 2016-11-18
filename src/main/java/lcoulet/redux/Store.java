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

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Loic.Coulet
 */
public class Store<S extends State> extends Observable {

    /**
     * Constructor Factory
     *
     * @param initialState initial state
     * @param reducer reduce function
     * @return created store
     */
    public static Store create(State initialState, Reducer reducer) {
        return new Store(initialState, reducer);
    }

    private final Reducer reducer;
    private State currentState;
    private boolean dispatchInProgress = false;

    /**
     * Constructor
     *
     * @param initialState initial state
     * @param reducer reduce function
     */
    private Store(State initialState, Reducer reducer) {
        this.reducer = reducer;
        this.currentState = initialState;
    }

    public void subscribe(Observer o) {
        super.addObserver(o);
    }

    public void unsubscribe(Observer o) {
        super.deleteObserver(o);
    }

    public State getCurrentState() {
        return currentState;
    }

    public synchronized void dispatch(Action action) {
        assert !dispatchInProgress : "A reducer cannot dispatch actions";
        dispatchInProgress = true;
        currentState = reducer.apply(currentState, action);
        notifyObservers();
        dispatchInProgress = false;
    }

}

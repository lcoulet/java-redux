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

import java.util.concurrent.CopyOnWriteArrayList;
import lcoulet.preconditions.Preconditions;

/**
 *
 * @author Loic.Coulet
 * @param <S> type of state managed by the store
 */
public class Store<S extends State> {

    /**
     * Constructor Factory
     *
     * @param <S> type of state managed by the created store
     * @param initialState initial state
     * @param reducer reduce function
     * @return created store
     */
    public static <S extends State> Store<S> create(S initialState, Reducer<S> reducer) {
        return new Store(initialState, reducer);
    }

    private final Reducer<S> reducer;
    private S currentState;
    private boolean dispatchInProgress = false;

    /**
     * Constructor
     *
     * @param initialState initial state
     * @param reducer reduce function
     */
    private Store(S initialState, Reducer<S> reducer) {
        this.reducer = reducer;
        this.currentState = initialState;
    }

    CopyOnWriteArrayList<Subscriber> subscribers = new CopyOnWriteArrayList<>();

    /**
     * Subscribe to the store to receive notification of updates
     *
     * @param client subscriber
     */
    public void subscribe(Subscriber client) {
        Preconditions.checkNotNull(client, "Subscriber cannot be null");
        if (!subscribers.contains(client)) {
            subscribers.add(client);
        }
    }

    /**
     * Subscribe to the store to receive notification of updates
     *
     * @param client subscriber
     */
    public void unsubscribe(Subscriber client) {
        Preconditions.checkNotNull(client, "Subscriber cannot be null");
        subscribers.remove(client);
    }

    public S getCurrentState() {
        return currentState;
    }

    public synchronized void dispatch(Action action) {
        Preconditions.checkState(!dispatchInProgress, "A reducer cannot dispatch actions");
        dispatchInProgress = true;
        State oldState = currentState;
        currentState = reducer.apply(currentState, action);
        if (oldState != currentState) {
            notifySubscribers();
        }
        dispatchInProgress = false;
    }

    private void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.stateChanged(currentState);
        }
    }

}

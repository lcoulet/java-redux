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

import java.util.Optional;
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
    public static <S extends State> Store<S> create(S initialState, Reducer<S, Action> reducer) {
        return new Store(initialState, reducer, null);
    }

    /**
     * Constructor Factory
     *
     * @param <S> type of state managed by the created store
     * @param initialState initial state
     * @param enhancer enhancer
     * @param reducer reduce function
     * @return created store
     */
    public static <S extends State> Store<S> create(S initialState, Reducer<S, Action> reducer, Enhancer enhancer) {
        return new Store(initialState, reducer, enhancer);
    }

    private final Reducer<S, Action> reducer;
    private S currentState;
    private boolean dispatchInProgress = false;
    private final Optional<Enhancer> enhancer;

    /**
     * Constructor
     *
     * @param initialState initial state
     * @param reducer reduce function
     */
    private Store(S initialState, Reducer<S, Action> reducer, Enhancer enhancer) {
        this.reducer = reducer;
        this.currentState = initialState;
        this.enhancer = Optional.of(enhancer);
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

        enhancer.ifPresent(e -> e.dispatch(action));

    }

    private void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.stateChanged(currentState);
        }
    }

}

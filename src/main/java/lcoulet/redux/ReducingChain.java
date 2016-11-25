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

import java.util.Iterator;
import java.util.LinkedList;
import lcoulet.preconditions.Preconditions;

/**
 * Represents a chain of reducers (reduction chain).
 *
 * @author Loic.Coulet
 * @param <S> the type of state supported by the reduction chain
 */
public class ReducingChain<S extends State, A extends Action> implements Reducer<S, A> {

    private final LinkedList<Reducer<S, A>> reducers;

    private ReducingChain() {
        reducers = new LinkedList<>();
    }

    /**
     * Create a new Combined Reducer from an existing one
     *
     * @param from the source reducer
     */
    private ReducingChain(ReducingChain from) {
        this();
        reducers.addAll(from.reducers);
    }

    /**
     * creates a new combined reducer
     *
     * @param <S> the type of state supported by the reduction chain
     * @return a new combined reducer that does nothing
     */
    public static <S extends State, A extends Action> ReducingChain<S, A> create() {
        return new ReducingChain<>();
    }

    /**
     * adds a reducer in the reducing chain
     *
     * @param reducer reducer to add in the chain.
     * @return a new combined reducer with augmented reducing chain
     */
    public ReducingChain with(Reducer<S, A> reducer) {
        Preconditions.checkArgument(reducer != null, "Cannot chain with a reducer that is a null reference ");


        ReducingChain results = new ReducingChain(this);

        results.reducers.add(reducer);
        return results;
    }

    @Override
    public S apply(S currentState, A action) {
        return applyReducingChain(currentState, action, reducers.iterator());
    }

    public S applyReducingChain(S currentState, A action, Iterator<Reducer<S, A>> reducers) {
        if (!reducers.hasNext()) {
            return currentState;
        }
        Reducer<S, A> reducerDef = reducers.next();

        S nextState = reducerDef.apply(currentState, action);

        return applyReducingChain(
                nextState,
                action,
                reducers);
    }


}

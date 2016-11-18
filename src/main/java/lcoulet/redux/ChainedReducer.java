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
 * represents a chain of reducers (reduction chain).
 * *
 * @author Loic.Coulet
 * @param <S> the type of state supported by the reduction chain
 */
public class ChainedReducer<S extends State> implements Reducer<S> {

    private final LinkedList<Reducer<S>> reducers;

    private ChainedReducer() {
        reducers = new LinkedList<>();
    }

    /**
     * Create a new Combined Reducer from an existing one
     *
     * @param from the source reducer
     */
    private ChainedReducer(ChainedReducer from) {
        this();
        reducers.addAll(from.reducers);
    }

    /**
     * creates a new combined reducer
     *
     * @param <S> the type of state supported by the reduction chain
     * @return a new combined reducer that does nothing
     */
    public static <S extends State> ChainedReducer<S> create() {
        return new ChainedReducer<>();
    }

    /**
     * adds a reducer in the reducing chain
     *
     * @param reducer reducer to add in the chain.
     * @return a new combined reducer with augmented reducing chain
     */
    public ChainedReducer with(Reducer<S> reducer) {
        Preconditions.checkArgument(reducer != null, "Cannot chain with a reducer that is a null reference ");


        ChainedReducer results = new ChainedReducer(this);

        results.reducers.add(reducer);
        return results;
    }

    @Override
    public S apply(S currentState, Action action) {
        return applyReducingChain(currentState, action, reducers.iterator());
    }

    public S applyReducingChain(S currentState, Action action, Iterator<Reducer<S>> reducers) {
        if (!reducers.hasNext()) {
            return currentState;
        }
        Reducer<S> reducerDef = reducers.next();

        S nextState = reducerDef.apply(currentState, action);

        return applyReducingChain(
                nextState,
                action,
                reducers);
    }


}

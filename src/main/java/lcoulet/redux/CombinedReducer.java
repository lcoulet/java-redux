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
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import lcoulet.preconditions.Preconditions;

/**
 * represents a combination of reducers (reduction chain). This reduction chain
 * supports composite states and applications of reducers to a sub-part of the
 * state.
 *
 * @author Loic.Coulet
 */
public class CombinedReducer implements Reducer {

    private final LinkedHashMap<String, Reducer> reducers;

    private CombinedReducer() {
        reducers = new LinkedHashMap<>();
    }

    /**
     * Create a new Combined Reducer from an existing one
     *
     * @param from the source reducer
     */
    private CombinedReducer(CombinedReducer from) {
        this();
        reducers.putAll(from.reducers);
    }

    /**
     * creates a new combined reducer
     *
     * @return a new combined reducer that does nothing
     */
    public static CombinedReducer create() {
        return new CombinedReducer();
    }

    /**
     * adds a reducer in the reducing chain
     *
     * @param forStateMember if the state is composite this reducer will apply
     * to the matching member property, otherwise this provides a label to the
     * reducer
     * @param reducer reducer to add in the chain.
     * @return a new combined reducer with augmented reducing chain
     */
    public CombinedReducer with(String forStateMember, Reducer reducer) {
        Preconditions.checkStringArgumentContents(forStateMember, "Member name must not be null or empty");
        Preconditions.checkArgument(reducer != null, "Cannot combine with a reducer that is a null reference ");
        Preconditions.checkArgument(!reducers.containsKey(forStateMember), "There's already a reducer for the property " + forStateMember + ", for anity reasons they have to be combined before being added to the chain");


        CombinedReducer results = new CombinedReducer(this);

        results.reducers.put(forStateMember, reducer);
        return results;
    }

    @Override
    public State apply(State currentState, Action action) {
        return applyReducingChain(currentState, action, reducers.entrySet().iterator());
    }

    public State applyReducingChain(State currentState, Action action, Iterator<Entry<String, Reducer>> reducers) {
        if (!reducers.hasNext()) {
            return currentState;
        }
        Entry<String, Reducer> reducerDef = reducers.next();
        String label = reducerDef.getKey();
        Reducer reducer = reducerDef.getValue();
        State nextState;
        if (currentState instanceof CompositeState) {
            State subState = ((CompositeState) currentState).getMember(label);
            nextState = ((CompositeState) currentState).change(label, reducer.apply(subState, action));
        } else {
            nextState = reducerDef.getValue().apply(currentState, action);
        }
        return applyReducingChain(
                nextState,
                action,
                reducers);
    }


}

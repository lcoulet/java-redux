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
 * Represents a combination of reducers for a composite state. Each reducer can
 * apply on one single property of the state.
 *
 * One single action may trigger zero to many reducer(s)
 * 
 * @author Loic.Coulet
 */
public class CombinedReducer implements Reducer<CompositeState> {

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
     * @param forStateMember this reducer will only apply to the matching member
     * property
     * @param reducer reducer to add in the composition.
     * @return a new augmented combined reducer
     */
    public CombinedReducer with(String forStateMember, Reducer reducer) {
        Preconditions.checkStringArgumentContents(forStateMember, "Member name must not be null or empty");
        Preconditions.checkArgument(reducer != null, "Cannot combine with a reducer that is a null reference ");
        Preconditions.checkArgument(!reducers.containsKey(forStateMember), "There's already a reducer for the property " + forStateMember + ", for sanity reasons they have to be chained before being added to the combination");


        CombinedReducer results = new CombinedReducer(this);

        results.reducers.put(forStateMember, reducer);
        return results;
    }

    @Override
    public CompositeState apply(CompositeState currentState, Action action) {
        return applyReducersWherePossible(currentState, action, reducers.entrySet().iterator());
    }

    public CompositeState applyReducersWherePossible(CompositeState currentState, Action action, Iterator<Entry<String, Reducer>> reducers) {
        if (!reducers.hasNext()) {
            return currentState;
        }
        Entry<String, Reducer> reducerDef = reducers.next();
        String label = reducerDef.getKey();
        Reducer reducer = reducerDef.getValue();

        State subState = currentState.getMember(label);
        CompositeState nextState = currentState.change(label, reducer.apply(subState, action));

        return applyReducersWherePossible(
                nextState,
                action,
                reducers);
    }


}

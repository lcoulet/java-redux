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

import lcoulet.preconditions.Preconditions;

/**
 * Type safe reducer encapsulation. Some reducers may only apply to certain
 * State classes, encapsulating such reducers in this class this avoids casting
 * the State to its sublass within the reducer apply method itself.
 *
 * Since reducers can apply to any kind of state, it is a design decision that
 * the * apply method is accepting a State as input
 *
 * @author Loic.Coulet
 * @param <S> the type of state this reducer applies to
 */
public class TypeSafeReducer<S extends State> implements Reducer {

    private final Reducer reducer;

    /**
     * Add typing to reducer
     *
     * @param r
     */
    public TypeSafeReducer(Reducer<S> r) {
        reducer = r;
    }

    @Override
    public State apply(State currentState, Action action) {
        Preconditions.checkNotNull(currentState, "state cannot be null");
        Preconditions.checkNotNull(action, "action cannot be null");
        try {
            return reducer.apply((S) currentState, action);
        } catch (ClassCastException e) {
            return currentState;
        }
    }

}

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
 * classes of State or Action , encapsulating such reducers in this class this
 * avoids casting * the State to its subclass within the reducer apply method itself.
 *
 * Therefore it is possible to use combined reducers and composed states and
 * reducer applying to only * some types of states.
 *
 *
 * @author Loic.Coulet
 */
public class TypeSafeReducer implements Reducer {

    private final Reducer reducer;

    /**
     * Creates a reducer that hides typing from provided reducer. The composed
     * reducer will apply if the class type is correct, otherwise it won;t apply
     * and return the state as-provided.
     *
     * @param r reducer to encapsulate.
     */
    public TypeSafeReducer(Reducer r) {
        reducer = r;
    }

    @Override
    public State apply(State currentState, Action action) {
        Preconditions.checkNotNull(currentState, "state cannot be null");
        Preconditions.checkNotNull(action, "action cannot be null");
        try {
            return reducer.apply(currentState, action);
        } catch (ClassCastException e) {
            return currentState;
        }
    }

}

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

/**
 * A Reducer is a function that accepts a state and an action and returns a
 * state. The way to implement a reducer is to return the same state as in input
 * of no change is done. If a change is done a copy of the state has to be
 * returned into which the modification has been applied. A state has to be
 * immutable.
 *
 *
 * @author Loic.Coulet
 * @param <S> the type of state this reducer applies to (for using in a TypeSafe
 * reducer)
 */
public interface Reducer<S extends State, A extends Action> {

    /**
     * A reducer that has no action.
     */
    public static final Reducer NULL_REDUCER = (State currentState, Action action) -> currentState;

    /**
     * The reduce function
     *
     * @param currentState current state
     * @param action action to apply
     * @return new state
     */
    public abstract S apply(S currentState, A action);

}

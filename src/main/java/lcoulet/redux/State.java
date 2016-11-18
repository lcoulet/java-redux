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
 * A state in an immutable data container.
 *
 * The copy method returns a copy of the state
 *
 * @author Loic.Coulet
 * @param <THIS> Self-type, this is useful to workaround non-support of self
 * types in Java. e.g. class MyState implements State&lt;MyState&gt;
 */
public interface State<THIS extends State<THIS>> {

    /**
     * this represents a NULL state. A NULL state has no property and allows a
     * single * reference.
     */
    public static final State NULL_STATE = new State() {
        @Override
        public State copy() {
            return this;
        }
    };

    /**
     * Provides a copy of this state, all mutable members have to be fully
     * copied.
     *
     * @return copy of this state
     */
    public abstract THIS copy();

}

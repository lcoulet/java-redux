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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lcoulet.preconditions.Preconditions;

/**
 * A state that is a composition of other states. Every composed state is mapped
 * a member property.
 * 
 * @author Loic.Coulet
 */
public class CompositeState implements State<CompositeState> {

    private HashMap<String, State> members = new HashMap<>();

    private CompositeState() {
    }

    /**
     * Creates a new composite state
     *
     * @return new composite state without any member
     */
    public static CompositeState create() {
        return new CompositeState();
    }

    /**
     * Creates a new composite state
     *
     * @param name name of the property
     * @param state value of the property
     * @return new composite state with an initial member
     */
    public static CompositeState create(String name, State state) {
        return create().with(name, state);
    }

    /**
     * return a copy of the provided state
     *
     * @param s reference state
     * @return new state
     */
    private CompositeState from(CompositeState referenceState) {
        CompositeState results = create();
        for (Map.Entry<String, State> s : referenceState.members.entrySet()) {
            results.members.put(s.getKey(), s.getValue().copy());
        }
        return results;
    }

    /**
     * return a copy of the provided state with an additional member
     *
     * @param name name of the entry
     * @param state new entry entry
     * @return new state
     */
    public CompositeState with(String name, State state) {
        Preconditions.checkStringArgumentContents(name, "Member name must not be null or empty");
        Preconditions.checkArgument(state != null, "State must not be null");
        Preconditions.checkArgument(!members.containsKey(name), "There is already a property named: " + name);

        CompositeState results = from(this);
        results.members.put(name, state);
        return results;
    }

    /**
     * return a copy of the provided state with a changed member
     *
     * @param name name of the entry
     * @param state new member property state
     * @return new state
     */
    public CompositeState change(String name, State state) {
        Preconditions.checkStringArgumentContents(name, "Member name must not be null or empty");
        Preconditions.checkArgument(state != null, "State must not be null");

        CompositeState results = from(this);
        results.members.put(name, state);
        return results;
    }

    /**
     * Returns a member of this state
     *
     * @param name name of the member
     * @return member value
     */
    public State getMember(String name) {
        if (members.containsKey(name)) {
            return members.get(name).copy();
        }
        return NULL_STATE;
    }

    /**
     * Checks if member of this state exists
     *
     * @param name name of the member
     * @return true if exists, false otherwise
     */
    public boolean hasMember(String name) {
        return members.containsKey(name);
    }

    /**
     * Returns list of members
     *
     * @return
     */
    public Set<String> listMembers() {
        return members.keySet();
    }

    @Override
    public CompositeState copy() {
        return from(this);
    }

}

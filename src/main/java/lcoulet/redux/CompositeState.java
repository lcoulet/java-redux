/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.redux;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Loic.Coulet
 */
public class CompositeState implements State {

    HashMap<String, State> members = new HashMap<>();

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
        assert name != null && !"".equals(name) : "Member name must not be null or empty";
        assert state != null : "State must not be null";
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
        return members.get(name).copy();
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
    public State copy() {
        return from(this);
    }

}

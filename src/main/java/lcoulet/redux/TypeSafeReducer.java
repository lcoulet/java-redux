/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

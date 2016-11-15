package lcoulet.redux;

/**
 *
 * @author Loic.Coulet
 */
public interface Reducer {


    /**
     * The reduce function
     *
     * @param currentState current state
     * @param action action to apply
     * @return new state
     */
    public abstract State apply(State currentState, Action action);

}

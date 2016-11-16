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

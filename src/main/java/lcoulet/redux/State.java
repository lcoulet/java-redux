package lcoulet.redux;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Loic.Coulet
 */
public interface State {


    /**
     * Provides a copy of this state
     *
     * @return copy of this state
     */
    public State copy();

}

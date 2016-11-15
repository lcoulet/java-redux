/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.redux;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import lcoulet.redux.CompositeState;

/**
 *
 * @author Loic.Coulet
 */
public class CombinedReducer implements Reducer {

    private final HashMap<String, Reducer> reducers;

    private CombinedReducer() {
        reducers = new HashMap<>();
    }

    private CombinedReducer(CombinedReducer from) {
        this();
        reducers.putAll(from.reducers);
    }

    public static CombinedReducer create() {
        return new CombinedReducer();
    }

    public CombinedReducer with(String forStateMember, Reducer reducer) {
        CombinedReducer results = new CombinedReducer(this);
        results.reducers.put(forStateMember, reducer);
        return results;
    }

    @Override
    public State apply(State currentState, Action action) {
        return applyReducingChain(currentState, action, reducers.entrySet().iterator());
    }

    public State applyReducingChain(State currentState, Action action, Iterator<Entry<String, Reducer>> reducers) {
        if (!reducers.hasNext()) {
            return currentState;
        }
        Entry<String, Reducer> next = reducers.next();
        State nextState;
        if (currentState instanceof CompositeState) {
            nextState = next.getValue().apply(((CompositeState) currentState).getMember(next.getKey()), action);
        } else {
            nextState = next.getValue().apply(currentState, action);
        }
        return applyReducingChain(
                nextState,
                action,
                reducers);
    }

    public State applyReducingChain(CompositeState currentState, Action action, Iterator<Entry<String, Reducer>> reducers) {
        if (!reducers.hasNext()) {
            return currentState;
        }
        Entry<String, Reducer> nextReducer = reducers.next();
        State nextState = nextReducer.getValue().apply(currentState.getMember(nextReducer.getKey()), action);
        return applyReducingChain(
                nextState,
                action,
                reducers);
    }

}

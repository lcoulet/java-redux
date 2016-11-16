/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.redux;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Loic.Coulet
 */
public class Store<S extends State> extends Observable {

    /**
     * Constructor Factory
     *
     * @param initialState initial state
     * @param reducer reduce function
     * @return created store
     */
    public static Store create(State initialState, Reducer reducer) {
        return new Store(initialState, reducer);
    }

    private final Reducer reducer;
    private State currentState;
    private boolean dispatchInProgress = false;

    /**
     * Constructor
     *
     * @param initialState initial state
     * @param reducer reduce function
     */
    private Store(State initialState, Reducer reducer) {
        this.reducer = reducer;
        this.currentState = initialState;
    }

    public void subscribe(Observer o) {
        super.addObserver(o);
    }

    public void unsubscribe(Observer o) {
        super.deleteObserver(o);
    }

    public State getCurrentState() {
        return currentState;
    }

    public synchronized void dispatch(Action action) {
        assert !dispatchInProgress : "A reducer cannot dispatch actions";
        dispatchInProgress = true;
        currentState = reducer.apply(currentState, action);
        notifyObservers();
        dispatchInProgress = false;
    }

}

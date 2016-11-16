/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.redux;

/**
 *
 * @author Loic.Coulet
 */
class CounterState implements State<CounterState> {

    int counter;

    CounterState(int initialValue) {
        counter = initialValue;
    }

    @Override
    public CounterState copy() {
        return new CounterState(counter);
    }

    static Action INCREMENT_COUNTER = new Action() {
    };
    static Action DECREMENT_COUNTER = new Action() {
    };
    static Action COUNTER_PLUS_1110 = new Action() {
    };
    static Action RESET_OR_INC_COUNTER = new Action() {
    };

    static Reducer RESET_REDUCER = new Reducer() {

        @Override
        public State apply(State currentState, Action action) {
            CounterState s = (CounterState) currentState.copy();
            if (action == RESET_OR_INC_COUNTER) {
                s.counter = 0;
            }
            return s;
        }
    };

    static Reducer INCREMENT_REDUCER = new Reducer() {

        @Override
        public State apply(State currentState, Action action) {
            CounterState s = (CounterState) currentState.copy();
            if (action == INCREMENT_COUNTER) {
                s.counter++;
            }
            if (action == DECREMENT_COUNTER) {
                s.counter--;
            }
            if (action == COUNTER_PLUS_1110) {
                s.counter += 1110;
            }
            if (action == RESET_OR_INC_COUNTER) {
                s.counter++;
            }
            return s;
        }

    };

}

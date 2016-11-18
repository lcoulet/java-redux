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

    static Reducer RESET_REDUCER = new Reducer<CounterState>() {

        @Override
        public CounterState apply(CounterState currentState, Action action) {
            CounterState s = (CounterState) currentState.copy();
            if (action == RESET_OR_INC_COUNTER) {
                s.counter = 0;
            }
            return s;
        }
    };

    static Reducer INCREMENT_REDUCER = new Reducer<CounterState>() {

        @Override
        public CounterState apply(CounterState currentState, Action action) {
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

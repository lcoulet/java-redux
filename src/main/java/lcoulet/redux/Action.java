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
 * An action represents an object that can trigger a change in reducers and lead
 * to state modifications.
 * It is represented by an empty interface (e.g.
 * reference to static instances can serve as action types). This can be derived
 * for parameterised actions.
 *
 * @author Loic.Coulet
 */
public interface Action {

    public static final Action NULL_ACTION = new Action() {
    };

}

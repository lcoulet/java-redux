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

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Loic.Coulet
 */
public class EnhancingChain implements Enhancer {

    private final CopyOnWriteArrayList<Enhancer> enhancers = new CopyOnWriteArrayList<>();


    private EnhancingChain() {

    }

    public static EnhancingChain create() {
        return new EnhancingChain();
    }

    public void with(Enhancer enhancer) {
        enhancers.add(enhancer);
    }


    @Override
    public void dispatch(Action action) {
        dispatch(action, enhancers.iterator());
    }

    public void dispatch(Action action, Iterator<Enhancer> iterator) {
        if (!iterator.hasNext()) {
            return;
        }
        iterator.next().dispatch(action);
        dispatch(action, iterator);
    }

}

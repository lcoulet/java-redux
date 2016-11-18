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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Loic.Coulet
 */
public class CompositeStateTest {

    public CompositeStateTest() {
    }

    @Test
    public void emptyComposite() {
        CompositeState instance = CompositeState.create();
        assertEquals("new instance should have no member", 0, instance.listMembers().size());
    }

    @Test
    public void nonEmptyComposite() {
        CompositeState instance = CompositeState.create("Test", CompositeState.create());
        assertEquals("new instance should have one member", 1, instance.listMembers().size());
        assertTrue("new instance should have one member named Test", instance.listMembers().contains("Test"));
    }

    @Test
    public void composeSeveralStates() {
        CompositeState instance = createDummyInstance();
        assertEquals("new instance should have 3 members", 3, instance.listMembers().size());
        assertTrue("new instance should have one member named Another", instance.listMembers().contains(DUMMY_MEMBER1));
        assertTrue("new instance should have one member named Yet Another", instance.listMembers().contains(DUMMY_MEMBER2));
    }

    @Test
    public void realChangeShouldReturnAnotherState() {
        CompositeState instance = createDummyInstance();

        CompositeState newInstance = instance.change(DUMMY_MEMBER1, new lcoulet.redux.DummyState());

        assertFalse("Instance should be different after a change", instance == newInstance);

    }

    @Test
    public void noChangeShouldReturnSameState() {
        CompositeState instance = createDummyInstance();

        CompositeState newInstance = instance.change(DUMMY_MEMBER1, instance.getMember(DUMMY_MEMBER1));

        assertTrue("Instance should be identical if there is no change", instance == newInstance);

    }

    public CompositeState createDummyInstance() {
        CompositeState instance = CompositeState.create("Test", CompositeState.create())
                .with(DUMMY_MEMBER1, new DummyState())
                .with(DUMMY_MEMBER2, new DummyState());
        return instance;
    }
    public static final String DUMMY_MEMBER2 = "Yet Another";
    public static final String DUMMY_MEMBER1 = "Another";


    @Test
    public void copyComposite() {
        CompositeState instance = CompositeState.create("Test", CompositeState.create());
        State copy = instance.copy();
        assertFalse("Copy shall be another refence", copy == instance);
        assertTrue("Copy shall be same type", copy instanceof CompositeState);
        assertEquals("new instance should have one member", 1, ((CompositeState) copy).listMembers().size());
    }

    @Test
    public void copyMultiComposite() {
        CompositeState instance = CompositeState
                .create("Test", CompositeState.create("ohlala", new DummyState()))
                .with(DUMMY_MEMBER1, new DummyState())
                .with(DUMMY_MEMBER2, new DummyState());

        CompositeState copy = (CompositeState) instance.copy();
        assertFalse("Copy shall be another refence", copy == instance);
        assertEquals("new instance should same number of members", 3, copy.listMembers().size());
        assertFalse("Copy member shall be another refence", copy.getMember("Test") == instance.getMember("Test"));
        assertTrue("Copy member shall be same type", copy.getMember("Test") instanceof CompositeState);
        assertTrue("Copy is recursive", ((CompositeState) copy.getMember("Test")).hasMember("ohlala"));
        assertFalse("Copy member shall be another refence", copy.getMember(DUMMY_MEMBER1) == instance.getMember(DUMMY_MEMBER1));
        assertTrue("Copy member shall be same type", copy.getMember(DUMMY_MEMBER1) instanceof DummyState);
        assertFalse("Copy member shall be another refence", copy.getMember(DUMMY_MEMBER2) == instance.getMember(DUMMY_MEMBER2));

    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPropertyNameCompositionShouldFail() {
        CompositeState
                .create(null, CompositeState.create("ohlala", new DummyState()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStateNameCompositionShouldFail() {
        CompositeState
                .create("ohlala", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicatepropertyInCompositionShouldFail() {
        CompositeState
                .create("ohlala", State.NULL_STATE)
                .with("ohlala", State.NULL_STATE);
    }

    private static class DummyState implements State {

        public DummyState() {
        }

        @Override
        public State copy() {
            return new DummyState();
        }
    }

}

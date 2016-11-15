package lcoulet.redux;

import lcoulet.redux.CompositeState;
import lcoulet.redux.State;
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
    public void NonEmptyComposite() {
        CompositeState instance = CompositeState.create("Test", CompositeState.create());
        assertEquals("new instance should have one member", 1, instance.listMembers().size());
        assertTrue("new instance should have one member named Test", instance.listMembers().contains("Test"));
    }

    @Test
    public void ComposeSeveralStates() {
        CompositeState instance = CompositeState.create("Test", CompositeState.create())
                .with("Another", new DummyState())
                .with("Yet Another", new DummyState());
        assertEquals("new instance should have 3 members", 3, instance.listMembers().size());
        assertTrue("new instance should have one member named Another", instance.listMembers().contains("Another"));
        assertTrue("new instance should have one member named Yet Another", instance.listMembers().contains("Yet Another"));
    }


    @Test
    public void CopyComposite() {
        CompositeState instance = CompositeState.create("Test", CompositeState.create());
        State copy = instance.copy();
        assertFalse("Copy shall be another refence", copy == instance);
        assertTrue("Copy shall be same type", copy instanceof CompositeState);
        assertEquals("new instance should have one member", 1, ((CompositeState) copy).listMembers().size());
    }

    @Test
    public void CopyMultiComposite() {
        CompositeState instance = CompositeState
                .create("Test", CompositeState.create("ohlala", new DummyState()))
                .with("Another", new DummyState())
                .with("Yet Another", new DummyState());

        CompositeState copy = (CompositeState) instance.copy();
        assertFalse("Copy shall be another refence", copy == instance);
        assertEquals("new instance should same number of members", 3, copy.listMembers().size());
        assertFalse("Copy member shall be another refence", copy.getMember("Test") == instance.getMember("Test"));
        assertTrue("Copy member shall be same type", copy.getMember("Test") instanceof CompositeState);
        assertTrue("Copy is recursive", ((CompositeState) copy.getMember("Test")).hasMember("ohlala"));
        assertFalse("Copy member shall be another refence", copy.getMember("Another") == instance.getMember("Another"));
        assertTrue("Copy member shall be same type", copy.getMember("Another") instanceof DummyState);
        assertFalse("Copy member shall be another refence", copy.getMember("Yet Another") == instance.getMember("Yet Another"));

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

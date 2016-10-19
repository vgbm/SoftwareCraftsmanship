import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by james on 10/18/16.
 */
public class SpyTest {

    //using integer objects to test, since we need objects
    private static Spy<Integer> _spy = new Spy<>();
    private static final Integer A = 1,
                                B = 2,
                                C = 3,
                                D = 4,
                                E = 5;


    public static class CreateTester {

        @Test
        public void Should_add_new_key_and_return_true() {
            boolean didCreate = _spy.create(A);
            assertTrue(didCreate);
            assertTrue(_spy.containsKey(A));
        }

        @Test
        public void Should_return_false_when_creating_existing_key() {
            _spy.create(A);
            boolean didCreate = _spy.create(A);
            assertFalse(didCreate);
        }

        @After
        public void TearDown() {
            _spy = new Spy<>();
        }

    }

    public static class OpposeTester {

        @Test
        public void Should_create_entry_for_new_objects(){
            _spy.create(A);
            assertFalse(_spy.containsKey(B));
            _spy.oppose(A,B);
            assertTrue(_spy.containsKey(B));
        }

        @Test
        public void Should_add_objects_as_opponents_bidirectionally(){
            _spy.oppose(A,B);
            assertTrue(_spy.opponents(A,B).get());
            assertTrue(_spy.opponents(B,A).get());
        }

        @Test (expected=RuntimeException.class)
        public void Should_error_if_objects_are_not_opponents(){
            _spy.oppose(A,B);
            _spy.oppose(B,C);
            _spy.oppose(A,C);
        }

        @After
        public void TearDown() {
            _spy = new Spy<>();
        }
    }

    public static class OpponentTester {

        @Before
        public void SetUp() {
            _spy.create(E);
            _spy.oppose(A,B);
            _spy.oppose(B,C);
            _spy.oppose(C,D);
        }

        @Test
        public void Should_return_empty_optional_if_no_connection_exists(){
            assertFalse(_spy.opponents(A,E).isPresent());
        }

        @Test
        public void Should_be_opponents_when_direct_opponents(){
            assertTrue(_spy.opponents(A,B).get());
        }

        @Test
        public void Should_be_opponents_when_depth_to_object_is_odd(){
            assertFalse(_spy.opponents(A,D).get());
        }

        @Test
        public void Should_not_be_opponents_when_depth_to_object_is_even(){
            assertFalse(_spy.opponents(A,C).get());
        }

        @After
        public void TearDown() {
            _spy = new Spy<>();
        }
    }
}

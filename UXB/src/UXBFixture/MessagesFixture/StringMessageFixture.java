package UXBFixture.MessagesFixture;

import UXB.Messages.StringMessage;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by james on 9/23/16.
 */
public class StringMessageFixture {

    private final String messageText = "test message text";
    private final StringMessage stringMessage = new StringMessage("Puppies");
    private final StringMessage stringMessage2 = new StringMessage("Kittens");

    @Test
    public void Should_create_message_with_given_value() {

        StringMessage message = new StringMessage(messageText);

        assertEquals(message.getMessage(), messageText);
    }

    @Test
    public void Should_create_message_with_bits_set_to_zero_if_the_constructor_is_null(){

        StringMessage emptyMessage = new StringMessage(null);

        assertEquals(emptyMessage.getMessage(), "");
    }

    @Test
    public void Should_say_binary_messages_with_the_same_message_are_equal(){

        StringMessage message1 = new StringMessage(messageText);
        StringMessage message2 = new StringMessage(messageText);

        assertTrue(message1.equals(message2));
    }

    @Test
    public void Should_say_binary_messages_with_different_messages_are_not_equal(){

        StringMessage message1 = new StringMessage(messageText);
        StringMessage message2 = new StringMessage(messageText +"new message text");

        Assert.assertFalse(message1.equals(message2));
    }


    @Test
    public void equalsSameString() {
        assertTrue(stringMessage.equals(new StringMessage("Puppies")));
    }

    @Test
    public void equalsDifferentString() {
        assertFalse(stringMessage.equals(stringMessage2));
    }

    @Test
    public void lengthTest() {
        assertEquals(stringMessage.length(), 7);
    }

    @Test
    public void charAtTest() {
        assertEquals(stringMessage.charAt(3), 'p' );
    }

    @Test
    public void containsTest() {
        assertTrue(stringMessage.contains("e"));
    }

    @Test
    public void startsWithTest() {
        assertTrue(stringMessage.startsWith("P"));
    }

    @Test
    public void endsWithTest() {
        assertTrue(stringMessage.endsWith("s"));
    }

    @Test
    public void isEmptyTest() {
        assertTrue(new StringMessage("").isEmpty());
    }

    @Test
    public void indexOfWithCharTest() {
        assertEquals(stringMessage.indexOf('u'), 1 );
    }

    @Test
    public void indexOfWithCharAndFromIndexTest() {
        assertEquals(stringMessage.indexOf('p', 1), 2 );
    }

    @Test
    public void indexOfWithStringTest() {
        assertEquals(stringMessage.indexOf("u"), 1 );
    }

    @Test
    public void indexOfWithStringAndFromIndexTest() {
        assertEquals(stringMessage.indexOf("p", 1), 2 );
    }

    @Test
    public void lastIndexOfWithCharTest() {
        assertEquals(stringMessage.lastIndexOf('p'), 3);
    }

    @Test
    public void lastIndexOfWithStringTest() {
        assertEquals(stringMessage.lastIndexOf("p"), 3);
    }

}

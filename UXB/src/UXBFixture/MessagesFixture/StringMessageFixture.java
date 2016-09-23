package UXBFixture.MessagesFixture;

import UXB.Messages.StringMessage;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by james on 9/23/16.
 */
public class StringMessageFixture {

    private final String messageText = "test message text";
    private final StringMessage testStrMes0 =new StringMessage("Puppies");
    private final StringMessage testStrMes1 =new StringMessage("Kittens");

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

        Assert.assertTrue(message1.equals(message2));
    }

    @Test
    public void Should_say_binary_messages_with_different_messages_are_not_equal(){

        StringMessage message1 = new StringMessage(messageText);
        StringMessage message2 = new StringMessage(messageText +"new message text");

        Assert.assertFalse(message1.equals(message2));
    }


    @Test
    public void equalsSameString() throws Exception {
        StringMessage testStrMes3 = new StringMessage(
                "Help, I'm stuck in a test factory!");
        assertEquals(testStrMes0.equals(testStrMes3), true);
    }

    @Test
    public void equalsDifferentString() throws Exception {
        assertEquals(testStrMes0.equals(testStrMes1), false);
    }

    @Test
    public void length() throws Exception {
        assertEquals(testStrMes0.length(), 34);
    }

    @Test
    public void charAt() throws Exception {
        assertEquals(testStrMes0.charAt(3), "p".charAt(0) );
    }

    @Test
    public void contains() throws Exception {
        assertEquals(testStrMes0.contains("e"), true);
    }

    @Test
    public void endsWith() throws Exception {
        assertEquals(testStrMes0.endsWith("!"), true);
    }

    @Test
    public void startsWith() throws Exception {
        assertEquals(testStrMes0.startsWith("H"), true);
    }

    @Test
    public void indexOfWithChar() throws Exception {
        assertEquals(testStrMes0.indexOf( "e".charAt(0) ), 1 );
    }

    @Test
    public void indexOfWithCharAndFromIndex() throws Exception {
        assertEquals(testStrMes0.indexOf( "e".charAt(0), 3 ), 22 );
    }

    @Test
    public void indexOfWithString() throws Exception {
        assertEquals(testStrMes0.indexOf( "e" ), 1 );
    }

    @Test
    public void indexOfWithStringAndFromIndex() throws Exception {
        assertEquals(testStrMes0.indexOf( "e", 10 ), 22 );
    }

    @Test
    public void lastIndexOfWithChar() throws Exception {
        assertEquals(testStrMes0.lastIndexOf( "e".charAt(0) ), 22 );
    }

    @Test
    public void lastIndexOfWithCharAndFromIndex() throws Exception {
        assertEquals(testStrMes0.indexOf( "e".charAt(0), 0 ), 1 );
    }

    @Test
    public void lastIndexOfWithString() throws Exception {
        assertEquals(testStrMes0.indexOf( "e" ), 1 );
    }

    @Test
    public void lastIndexOfWithStringAndFromIndex() throws Exception {
        assertEquals(testStrMes0.indexOf( "e", 0 ), 1 );
    }

    @Test
    public void isEmpty() throws Exception {
        StringMessage testStrMes4 = new StringMessage("");
        assertEquals(testStrMes4.isEmpty(), true);
    }

    @Test
    public void hashCode() throws Exception {
        assertEquals(testStrMes0.hashCode(), 2);
    }
}

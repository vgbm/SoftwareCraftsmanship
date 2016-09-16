package UXBFixture;

import UXB.BinaryMessage;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

/**
 * Created by james on 9/16/16.
 */
public class BinaryMessageFixture {

    BigInteger messageBits = BigInteger.valueOf(121);

    @Test
    public void Should_create_message_with_given_value() {

        BinaryMessage message = new BinaryMessage(messageBits);

        assertEquals(message.getMessage(), messageBits);
    }

    @Test
    public void Should_create_message_with_bits_set_to_zero_if_the_constructor_is_null(){

        BinaryMessage emptyMessage = new BinaryMessage(null);

        assertEquals(emptyMessage.getMessage(), BigInteger.ZERO);
    }

    @Test
    public void Should_say_binary_messages_with_the_same_message_are_equal(){

        BinaryMessage message1 = new BinaryMessage(messageBits);
        BinaryMessage message2 = new BinaryMessage(messageBits);

        Assert.assertTrue(message1.equals(message2));
    }

    @Test
    public void Should_say_binary_messages_with_different_messages_are_not_equal(){

        BinaryMessage message1 = new BinaryMessage(messageBits);
        BinaryMessage message2 = new BinaryMessage(messageBits.add(BigInteger.ONE));

        Assert.assertFalse(message1.equals(message2));
    }
}

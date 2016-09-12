package UXB;

import java.math.BigInteger;

/**
 * Created by james on 9/12/16.
 */
public final class BinaryMessage implements Message {

    private final BigInteger message;

    public BinaryMessage(BigInteger value){
        if(value == null) {
            message = BigInteger.ZERO;
        } else {
            message = value;
        }
    }

    public BigInteger getMessage(){
        return message;
    }

    @Override
    //TODO: clean
    public boolean equals(Object anObject) {
        if(anObject == null || anObject.getClass() != BinaryMessage.class){
            return false;
        }

        return message.equals(((BinaryMessage)anObject).getMessage());
    }
}

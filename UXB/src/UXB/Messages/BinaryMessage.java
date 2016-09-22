package UXB.Messages;

import UXB.Connector;
import UXB.Device;

import java.math.BigInteger;

/**
 * Created by james on 9/12/16.
 *
 * Message as a series of bits
 */
public final class BinaryMessage implements Message {

    //Message being sent across the connector
    private final BigInteger message;

    public BinaryMessage(BigInteger value){
        if(value == null) {
            message = BigInteger.ZERO;
        } else {
            //BigIntegers are immutable, so no need to copy
            message = value;
        }
    }

    public BigInteger getMessage(){
        return message;
    }

    public void reach(Device device, Connector connector) {
        device.recv(this, connector);
    }

    @Override
    //Returns whether the object passed in is not null and has the same message type
    public boolean equals(Object anObject) {
        if(anObject == null || !(anObject instanceof BinaryMessage)){
            return false;
        }

        BinaryMessage objectMessage = (BinaryMessage) anObject;
        return message.equals(objectMessage.getMessage());
    }
}

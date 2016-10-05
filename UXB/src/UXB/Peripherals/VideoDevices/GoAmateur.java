package UXB.Peripherals.VideoDevices;

import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.Message;
import UXB.Messages.StringMessage;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by james on 9/19/16.
 */
public class GoAmateur extends AbstractVideo<GoAmateur.Builder> {

    public GoAmateur(Builder builder) {
        super(builder);
    }

    public void recv(StringMessage message, Connector connector) {
        validateRecvArguments(message, connector);

        System.out.println("GoAmateur does not understand string messages: "
                            + message.getMessage()
                            + "\nConnector index:"
                            + connector.getIndex());

    }

    public void recv(BinaryMessage message, Connector connector) {
        validateRecvArguments(message, connector);

        Message messageToSend = new BinaryMessage(BigInteger.valueOf(293));
        sendMessageToAllPeersExceptIncomingConnector(messageToSend, null); //sending message to incoming conn as well
    }


    public static class Builder extends AbstractVideo.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

        public GoAmateur build() {
            super.validate();
            return new GoAmateur(this);
        }

        protected Builder getThis() {
            return this;
        }
    }
}

package UXB.Peripherals.Printers;

import UXB.AbstractDevice;
import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Created by james on 9/19/16.
 */
public class SisterPrinter extends AbstractPrinter<SisterPrinter.Builder> {


    public SisterPrinter(Builder builder) {
        super(builder);
    }

    public void recv(StringMessage message, Connector connector) {
        validateRecvArguments(message, connector);

        System.out.println("Sister printer has printed the string: "
                            + message.getMessage()
                            + "\nSerial number: "
                            + getSerialNumber().toString());
    }

    public void recv(BinaryMessage message, Connector connector) {
        validateRecvArguments(message, connector);

        BigInteger printedMessage = calculatePrintedMessage(message.getMessage(), getProductCode());

        System.out.println("Sister printer has printed the binary message: "
                + printedMessage.toString());
    }

    //Add the product code to the message in order to solve for the message printed
    //If productCode is empty, we simply add 0
    //TODO : Optional orElse
    private BigInteger calculatePrintedMessage(BigInteger messageValue, Optional<Integer> productCode) {
        BigInteger addedProductCodeValue = productCode.isPresent()
                                            ? new BigInteger(productCode.get().toString())
                                            : BigInteger.ZERO;

        return messageValue.add(addedProductCodeValue);
    }

    public static class Builder extends AbstractPrinter.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

        public SisterPrinter build() {
            super.validate();
            return new SisterPrinter(this);
        }

        protected Builder getThis() {
            return this;
        }
    }
}

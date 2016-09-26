package UXB.Peripherals.Printers;

import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Created by james on 9/19/16.
 */
public class CannonPrinter extends AbstractPrinter<CannonPrinter.Builder> {

    public CannonPrinter(Builder builder) {
        super(builder);
    }

    public void recv(StringMessage message, Connector connector) {
        validateRecvArguments(message, connector);

        System.out.println("Cannon printer has printed the string: "
                + message.getMessage()
                + "\nSerial number: "
                + getVersion().toString());
    }

    public void recv(BinaryMessage message, Connector connector) {
        validateRecvArguments(message, connector);

        BigInteger printedMessage = calculatePrintedMessage(message.getMessage(), getSerialNumber());

        System.out.println("Cannon printer has printed the binary message: "
                + printedMessage.toString());
    }

    //Add the product code to the message in order to solve for the message printed
    //If productCode is empty, we simply add 0
    //TODO : Optional orElse
    private BigInteger calculatePrintedMessage(BigInteger messageValue, Optional<BigInteger> serialNumber) {
        BigInteger addedProductCodeValue = serialNumber.isPresent()
                ? new BigInteger(serialNumber.get().toString())
                : BigInteger.ONE;

        return messageValue.multiply(addedProductCodeValue);
    }


    public static class Builder extends AbstractPrinter.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

        public CannonPrinter build() {
            super.validate();
            return new CannonPrinter(this);
        }

        protected Builder getThis() {
            return this;
        }
    }
}

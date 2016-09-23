package UXBFixture.PeripheralsFixture.PrintersFixture;

import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;
import UXB.Peripherals.Printers.CannonPrinter;
import UXB.Peripherals.VideoDevices.GoAmateur;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by james on 9/23/16.
 */
public class CannonPrinterFixture {

    private List<Connector.Type> connectorList;
    private CannonPrinter cannonPrinter;
    private Connector goodConnector, badConnector;
    private final BinaryMessage binaryMessage = new BinaryMessage(BigInteger.TEN);
    private final StringMessage stringMessage = new StringMessage("test message");

    @Before
    public void setUp() {
        connectorList = Arrays.asList(Connector.Type.PERIPHERAL);
        cannonPrinter = new CannonPrinter.Builder(1).connectors(connectorList).build();

        Connector connectorToDevice = new Connector(0, Connector.Type.PERIPHERAL, cannonPrinter);

        goodConnector = new Connector(0, Connector.Type.PERIPHERAL, null);
        goodConnector.setPeer(connectorToDevice);
        badConnector = new Connector(0, Connector.Type.PERIPHERAL, null);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_string_messages() throws NullPointerException {
        cannonPrinter.recv((StringMessage)null, goodConnector);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_binary_messages() throws NullPointerException {
        cannonPrinter.recv((BinaryMessage)null, goodConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_string_messages() throws IllegalArgumentException {
        cannonPrinter.recv(stringMessage, badConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_binary_messages() throws IllegalArgumentException {
        cannonPrinter.recv(binaryMessage, badConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_string_messages() {
        cannonPrinter.recv(stringMessage, goodConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_binary_messages() {
        cannonPrinter.recv(binaryMessage, goodConnector);
    }

}

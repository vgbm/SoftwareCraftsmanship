package UXBFixture.PeripheralsFixture.PrintersFixture;

import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;
import UXB.Peripherals.Printers.SisterPrinter;
import UXB.Peripherals.VideoDevices.GoAmateur;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by james on 9/23/16.
 */
public class SisterPrinterFixture {

    private List<Connector.Type> connectorList;
    private SisterPrinter sisterPrinter;
    private Connector goodConnector, badConnector;
    private final BinaryMessage binaryMessage = new BinaryMessage(BigInteger.TEN);
    private final StringMessage stringMessage = new StringMessage("test message");

    @Before
    public void setUp() {
        connectorList = Arrays.asList(Connector.Type.PERIPHERAL);
        sisterPrinter = new SisterPrinter.Builder(1).connectors(connectorList).build();
        goodConnector = new Connector(0, Connector.Type.PERIPHERAL, sisterPrinter);
        badConnector = new Connector(0, Connector.Type.PERIPHERAL, null);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_string_messages() throws NullPointerException {
        sisterPrinter.recv((StringMessage)null, goodConnector);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_binary_messages() throws NullPointerException {
        sisterPrinter.recv((BinaryMessage)null, goodConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_string_messages() throws IllegalArgumentException {
        sisterPrinter.recv(stringMessage, badConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_binary_messages() throws IllegalArgumentException {
        sisterPrinter.recv(binaryMessage, badConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_string_messages() {
        sisterPrinter.recv(stringMessage, goodConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_binary_messages() {
        sisterPrinter.recv(binaryMessage, goodConnector);
    }

}

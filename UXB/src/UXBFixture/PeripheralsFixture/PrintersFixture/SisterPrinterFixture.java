package UXBFixture.PeripheralsFixture.PrintersFixture;

import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;
import UXB.Peripherals.Printers.SisterPrinter;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by james on 9/23/16.
 */
public class SisterPrinterFixture {

    private List<Connector.Type> _connectorList;
    private SisterPrinter _sisterPrinter;
    private Connector _goodConnector, _badConnector;
    private final BinaryMessage _binaryMessage = new BinaryMessage(BigInteger.TEN);
    private final StringMessage _stringMessage = new StringMessage("test message");

    @Before
    public void setUp() {
        _connectorList = Arrays.asList(Connector.Type.PERIPHERAL);
        _sisterPrinter = new SisterPrinter.Builder(1).connectors(_connectorList).build();

        _goodConnector = new Connector(0, Connector.Type.PERIPHERAL, _sisterPrinter);
        _badConnector = new Connector(0, Connector.Type.PERIPHERAL, null);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_string_messages() throws NullPointerException {
        _sisterPrinter.recv((StringMessage)null, _goodConnector);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_binary_messages() throws NullPointerException {
        _sisterPrinter.recv((BinaryMessage)null, _goodConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_string_messages() throws IllegalArgumentException {
        _sisterPrinter.recv(_stringMessage, _badConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_binary_messages() throws IllegalArgumentException {
        _sisterPrinter.recv(_binaryMessage, _badConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_string_messages() {
        _sisterPrinter.recv(_stringMessage, _goodConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_binary_messages() {
        _sisterPrinter.recv(_binaryMessage, _goodConnector);
    }

}

package UXBFixture.PeripheralsFixture.VideoDevicesFixture;

import UXB.Connector;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;
import UXB.Peripherals.VideoDevices.GoAmateur;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/23/16.
 */
public class GoAmateurFixture {

    private List<Connector.Type> connectorList;
    private GoAmateur goAmateur;
    private Connector goodConnector, badConnector;
    private final BinaryMessage binaryMessage = new BinaryMessage(BigInteger.TEN);
    private final StringMessage stringMessage = new StringMessage("test message");

    @Before
    public void setUp() {
        connectorList = Arrays.asList(Connector.Type.PERIPHERAL);
        goAmateur = new GoAmateur.Builder(1).connectors(connectorList).build();

        goodConnector = new Connector(0, Connector.Type.PERIPHERAL, goAmateur);
        badConnector = new Connector(0, Connector.Type.PERIPHERAL, null);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_string_messages() throws NullPointerException {
        goAmateur.recv((StringMessage)null, goodConnector);
    }

    @Test(expected=NullPointerException.class)
    public void Should_throw_validation_error_with_no_message_for_binary_messages() throws NullPointerException {
        goAmateur.recv((BinaryMessage)null, goodConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_string_messages() throws IllegalArgumentException {
        goAmateur.recv(stringMessage, badConnector);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_throw_validation_error_with_bad_connectors_for_binary_messages() throws IllegalArgumentException {
        goAmateur.recv(binaryMessage, badConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_string_messages() {
        goAmateur.recv(stringMessage, goodConnector);
    }

    @Test
    public void Should_appropriately_handle_recv_for_binary_messages() {
        goAmateur.recv(binaryMessage, goodConnector);
    }
}

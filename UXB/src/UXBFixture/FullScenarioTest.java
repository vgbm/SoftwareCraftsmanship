package UXBFixture;

import UXB.Connector;
import UXB.Exceptions.ConnectionException;
import UXB.Hub;
import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;
import UXB.Peripherals.Printers.CannonPrinter;
import UXB.Peripherals.Printers.SisterPrinter;
import UXB.Peripherals.VideoDevices.GoAmateur;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by james on 10/5/16.
 */
public class FullScenarioTest {

    private static final List<Connector.Type> _peripheralTypeList = Arrays.asList(Connector.Type.PERIPHERAL);
    private static final List<Connector.Type> _hubTypeList = Arrays.asList(Connector.Type.PERIPHERAL, Connector.Type.COMPUTER, Connector.Type.COMPUTER, Connector.Type.COMPUTER);

    private static final SisterPrinter _printer1 = new SisterPrinter.Builder(1).serialNumber(BigInteger.valueOf(5318008)).connectors(_peripheralTypeList).build();
    private static final SisterPrinter _printer2 = new SisterPrinter.Builder(2).serialNumber(BigInteger.valueOf(1235234)).connectors(_peripheralTypeList).build();
    private static final CannonPrinter _printer3 = new CannonPrinter.Builder(3).serialNumber(BigInteger.valueOf(76)).connectors(_peripheralTypeList).build();
    private static final GoAmateur _webCam = new GoAmateur.Builder(4).connectors(_peripheralTypeList).build();
    private static final Hub _hub1 = new Hub.Builder(5).connectors(_hubTypeList).build();
    private static final Hub _hub2 = new Hub.Builder(6).connectors(_hubTypeList).build();


    //stray connector connected to the hub and nothing else
    //allows for arcane entities to push messages through the graph without expanding the graph and adding devices
    private static Connector _broadcastingConnector = new Connector(0, Connector.Type.PERIPHERAL, _hub1);

    @Before
    public void SetUpConnections() throws ConnectionException {

        //connect the hubs together with their computer connectors
        _hub1.getConnector(0).setPeer(_hub2.getConnector(3));

        //connect 2 peripherals to each hub

        _hub1.getConnector(1).setPeer(_printer1.getConnector(0));
        _hub1.getConnector(2).setPeer(_printer2.getConnector(0));

        _hub2.getConnector(1).setPeer(_printer3.getConnector(0));
        _hub2.getConnector(2).setPeer(_webCam.getConnector(0));
    }


    @Test
    public void Should_send_string_message_through_graph() {
        StringMessage message = new StringMessage("It's over. It's done.");
        _broadcastingConnector.recv(message);
    }

    //logged messages should double up since GoAmateur reflects the message
    @Test
    public void Should_send_binary_message_through_graph() {
        BinaryMessage message = new BinaryMessage(BigInteger.valueOf(666));
        _broadcastingConnector.recv(message);
    }

    //no double up should be present since the reflection is the only signal that traverses the graph
    @Test
    public void Should_send_binary_message_from_hub_to_webcam() {
        BinaryMessage message = new BinaryMessage(BigInteger.valueOf(666));
        _webCam.getConnector(0).recv(message);
    }
}

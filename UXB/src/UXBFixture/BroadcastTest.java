package UXBFixture;

import UXB.Connector;
import UXB.Device;
import UXB.Hub;
import UXB.Messages.BinaryMessage;
import UXB.Messages.Message;
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
 * Created by james on 9/23/16.
 */
public class BroadcastTest {

    Hub hub;
    CannonPrinter cannonPrinter;
    SisterPrinter sisterPrinter;
    GoAmateur goAmateur;
    private final BinaryMessage binaryMessage = new BinaryMessage(BigInteger.TEN);
    private final StringMessage stringMessage = new StringMessage("test message");
    List<Device> deviceList;
    List<Message> messageList;


    @Before
    public void set_up(){
        List<Connector.Type> hubConnectorTypes = Arrays.asList(Connector.Type.COMPUTER, Connector.Type.PERIPHERAL);
        List<Connector.Type> deviceConnectorTypes = Arrays.asList(Connector.Type.PERIPHERAL);

        hub = new Hub.Builder(1).connectors(hubConnectorTypes).build();
        cannonPrinter = new CannonPrinter.Builder(1).connectors(deviceConnectorTypes).build();
        sisterPrinter = new SisterPrinter.Builder(1).connectors(deviceConnectorTypes).build();
        goAmateur = new GoAmateur.Builder(1).connectors(deviceConnectorTypes).build();

        deviceList = Arrays.asList(hub, cannonPrinter, sisterPrinter, goAmateur);
        messageList = Arrays.asList(stringMessage, binaryMessage);
    }

    @Test
    public void deliver_Messages() {
        for(Device device : deviceList) {

            Connector connector = new Connector(0, Connector.Type.PERIPHERAL, device);

            for (Message message : messageList) {
                connector.recv(message);
                message.reach(device, connector);
            }
        }
    }
}

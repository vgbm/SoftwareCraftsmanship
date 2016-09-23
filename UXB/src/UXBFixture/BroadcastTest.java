package UXBFixture;

import UXB.Connector;
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

    Hub mainHub, testHub;
    CannonPrinter testCannonPrinter;
    SisterPrinter testSisterPrinter;
    GoAmateur testGoAmateur;

    @Before
    public void set_up(){

        List<Connector.Type> mainHubConnList = Arrays.asList(
                Connector.Type.COMPUTER,
                Connector.Type.PERIPHERAL,
                Connector.Type.PERIPHERAL,
                Connector.Type.PERIPHERAL,
                Connector.Type.PERIPHERAL);

        mainHub = new Hub.Builder(1)
                .productCode(13)
                .serialNumber(BigInteger.valueOf(35756) )
                .connectors(mainHubConnList)
                .build();

        // Build Hub 1
        List<Connector.Type> testHubConnList = Arrays.asList(
                Connector.Type.COMPUTER,
                Connector.Type.PERIPHERAL);

        testHub = new Hub.Builder(2)
                .productCode(35)
                .serialNumber(BigInteger.valueOf(5) )
                .connectors(testHubConnList)
                .build();

        // Build Cannon Printer 2
        List<Connector.Type> cannonPrinterConnList = Arrays.asList(
                Connector.Type.PERIPHERAL,
                Connector.Type.PERIPHERAL);

        testCannonPrinter = new CannonPrinter.Builder(242)
                .serialNumber( BigInteger.valueOf(666633) )
                .productCode(22)
                .connectors(cannonPrinterConnList)
                .build();

        // Build Sister Printer 3
        List<Connector.Type> sisterPrinterTestHub = Arrays.asList(
                Connector.Type.PERIPHERAL,
                Connector.Type.PERIPHERAL);

        testSisterPrinter = new SisterPrinter.Builder(147)
                .serialNumber( BigInteger.valueOf(544) )
                .productCode(355)
                .connectors(sisterPrinterTestHub)
                .build();

        // Build Go Amateur 4
        List<Connector.Type> goAmateurConnList = Arrays.asList(
                Connector.Type.PERIPHERAL,
                Connector.Type.PERIPHERAL);

        testGoAmateur = new GoAmateur.Builder(242)
                .serialNumber( BigInteger.valueOf(643) )
                .productCode(7335)
                .connectors(goAmateurConnList)
                .build();


    }

    @Test
    public void broadcast_test_for_devices_and_message_passing() {
        // Set Peers
        List<Connector> testConList0 = mainHub.getConnectors();
        List<Connector> testConList1 = testHub.getConnectors();
        List<Connector> testConList2 = testCannonPrinter.getConnectors();
        List<Connector> testConList3 = testSisterPrinter.getConnectors();
        List<Connector> testConList4 = testGoAmateur.getConnectors();
        testConList0.get(0).setPeer(testConList1.get(0) );
        testConList0.get(1).setPeer(testConList2.get(0) );
        testConList0.get(2).setPeer(testConList3.get(0) );
        testConList0.get(3).setPeer(testConList4.get(0) );

        // Create Messages
        Message testStringMes0 = new StringMessage("Hello. Pleased to meet you");
        Message testBinMes0 = new BinaryMessage(new BigInteger("213078964") );

        // Send Messages
        mainHub.getConnector(0).recv(testStringMes0);
        mainHub.getConnector(1).recv(testStringMes0);
        mainHub.getConnector(2).recv(testStringMes0);
        mainHub.getConnector(3).recv(testStringMes0);

        mainHub.getConnector(0).recv(testBinMes0);
        mainHub.getConnector(1).recv(testBinMes0);
        mainHub.getConnector(2).recv(testBinMes0);
        mainHub.getConnector(3).recv(testBinMes0);
    }

}

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

        List<Connector.Type> testHubConnList = Arrays.asList(Connector.Type.COMPUTER, Connector.Type.PERIPHERAL);

        testHub = new Hub.Builder(2)
                .productCode(35)
                .serialNumber(BigInteger.valueOf(5) )
                .connectors(testHubConnList)
                .build();

        List<Connector.Type> cannonPrinterConnList = Arrays.asList(Connector.Type.PERIPHERAL);

        testCannonPrinter = new CannonPrinter.Builder(5555)
                .serialNumber( BigInteger.valueOf(6666) )
                .productCode(22222)
                .connectors(cannonPrinterConnList)
                .build();

        List<Connector.Type> sisterPrinterTestHub = Arrays.asList(Connector.Type.PERIPHERAL);

        testSisterPrinter = new SisterPrinter.Builder(777777)
                .serialNumber( BigInteger.valueOf(88888) )
                .productCode(2222)
                .connectors(sisterPrinterTestHub)
                .build();

        List<Connector.Type> goAmateurConnList = Arrays.asList(Connector.Type.PERIPHERAL);

        testGoAmateur = new GoAmateur.Builder(7765)
                .serialNumber( BigInteger.valueOf(56756) )
                .productCode(111)
                .connectors(goAmateurConnList)
                .build();


    }

    @Test
    public void broadcast_test_for_devices_and_message_passing() {

        List<Connector> mainHubConnList = mainHub.getConnectors();
        List<Connector> testHubConnList = testHub.getConnectors();
        List<Connector> cannonPrinterConnList = testCannonPrinter.getConnectors();
        List<Connector> sisterPrinterConnList = testSisterPrinter.getConnectors();
        List<Connector> goAmateurConnList = testGoAmateur.getConnectors();

        mainHubConnList.get(0).setPeer(testHubConnList.get(0) );
        mainHubConnList.get(1).setPeer(cannonPrinterConnList.get(0) );
        mainHubConnList.get(2).setPeer(sisterPrinterConnList.get(0) );
        mainHubConnList.get(3).setPeer(goAmateurConnList.get(0) );


        Message testStringMes0 = new StringMessage("Hello. Pleased to meet you");
        Message testBinMes0 = new BinaryMessage(new BigInteger("213078964") );

        //Checking for errors to not be thrown
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

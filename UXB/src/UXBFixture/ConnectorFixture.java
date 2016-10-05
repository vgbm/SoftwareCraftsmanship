package UXBFixture;

import UXB.Connector;
import UXB.Exceptions.ConnectionException;
import UXB.Hub;
import UXB.Peripherals.Printers.SisterPrinter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by james on 9/27/16.
 */
public class ConnectorFixture {

    private static final List<Connector.Type> _peripheralTypeList = Arrays.asList(Connector.Type.PERIPHERAL, Connector.Type.PERIPHERAL);
    private static final List<Connector.Type> _hubTypeList = Arrays.asList(Connector.Type.PERIPHERAL, Connector.Type.COMPUTER, Connector.Type.COMPUTER);

    public static class SetPeerFixtures {

        private Connector _peripheralConn;
        private Connector _hubPeripheralConn, _hubComputerConn1, _hubComputerConn2;

        @Before
        public void SetUp() {

            SisterPrinter device = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            Hub hub = new Hub.Builder(1).connectors(_hubTypeList).build();

            _peripheralConn = device.getConnector(0);
            _hubPeripheralConn = hub.getConnector(0);
            _hubComputerConn1 = hub.getConnector(1);
            _hubComputerConn2 = hub.getConnector(2);

        }

        @Test (expected = NullPointerException.class)
        public void Should_error_when_setting_a_null_peer() throws ConnectionException {
            _peripheralConn.setPeer(null);
        }

        @Test (expected = ConnectionException.class)
        public void Should_error_when_a_peer_already_exists() throws ConnectionException{

            //must use the opposite connector type
            _peripheralConn.setPeer(_hubComputerConn1);

            //should error the second time around since a peer already exists
            _peripheralConn.setPeer(_hubComputerConn2);
        }


        @Test (expected = ConnectionException.class)
        public void Should_error_when_the_peer_type_is_the_same() throws ConnectionException{
            _peripheralConn.setPeer(_hubPeripheralConn);
        }

        @Test (expected = ConnectionException.class)
        public void Should_error_when_setting_the_peer_creates_a_cycle() throws ConnectionException{
            _hubComputerConn1.setPeer(_peripheralConn);
            _peripheralConn.setPeer(_hubComputerConn2);
        }

        @Test
        public void Should_properly_set_peer() throws ConnectionException{
            _hubComputerConn1.setPeer(_peripheralConn);
            assertEquals(_hubComputerConn1.getPeer().get(), _peripheralConn);
            assertEquals(_peripheralConn.getPeer().get(), _hubComputerConn1);
        }
    }

    public static class IsReachableFixtures {

        private Connector _peripheralConn;
        private Connector _hubComputerConn;

        @Before
        public void SetUp() throws ConnectionException{

            SisterPrinter device = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            Hub hub = new Hub.Builder(1).connectors(_hubTypeList).build();

            _peripheralConn = device.getConnector(0);
            _hubComputerConn = hub.getConnector(1);
            _peripheralConn.setPeer(_hubComputerConn);
        }

        @Test
        public void Should_be_reachable() throws ConnectionException{
            assertTrue(_peripheralConn.isReachable(_hubComputerConn.getDevice()));
        }
        @Test
        public void Should_not_be_reachable() throws ConnectionException{
            Hub unreachableHub = new Hub.Builder(1).connectors(_hubTypeList).build();
            assertFalse(unreachableHub.isReachable(_peripheralConn.getDevice()));
        }
    }
}

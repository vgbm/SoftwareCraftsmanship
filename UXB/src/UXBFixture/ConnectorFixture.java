package UXBFixture;

import UXB.AbstractDevice;
import UXB.Connector;
import UXB.Exceptions.ConnectionException;
import UXB.Peripherals.Printers.SisterPrinter;
import UXB.Peripherals.VideoDevices.GoAmateur;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by james on 9/27/16.
 */
public class ConnectorFixture {

    private static Connector copyConnectorWithType(Connector connector, Connector.Type type) {
        return new Connector(connector.getIndex(), type, connector.getDevice());
    }

    public static class SetPeerFixtures {

        private Connector _connector;
        private final List<Connector.Type> _connTypeList = Arrays.asList(Connector.Type.PERIPHERAL);

        @Before
        public void SetUp() {

            SisterPrinter device = new SisterPrinter.Builder(1).connectors(_connTypeList).build();

            _connector = new Connector(0, Connector.Type.PERIPHERAL, device);
        }

        @Test (expected = NullPointerException.class)
        public void Should_error_when_setting_a_null_peer() throws ConnectionException {
            _connector.setPeer(null);
        }

        @Test (expected = ConnectionException.class)
        public void Should_error_when_a_peer_already_exists() throws ConnectionException{

            //need type to be different than the connector so we dont trigger an error unintentionally
            Connector.Type newConnType = _connector.getType().equals(Connector.Type.PERIPHERAL)
                                            ? Connector.Type.COMPUTER
                                            : Connector.Type.PERIPHERAL;

            Connector connectorWithPeer = ConnectorFixture.copyConnectorWithType(_connector, newConnType);

            connectorWithPeer.setPeer(_connector);

            //should error the second time around since a peer already exists
            connectorWithPeer.setPeer(_connector);
        }


        @Test (expected = ConnectionException.class)
        public void Should_error_when_the_peer_type_is_the_same() throws ConnectionException{
            Connector connectorWithSameType = copyConnectorWithType(_connector, _connector.getType());
            connectorWithSameType.setPeer(_connector);
        }

        @Test (expected = ConnectionException.class)
        public void Should_error_when_setting_the_peer_creates_a_cycle() throws ConnectionException{

            SisterPrinter sisterPrinter = new SisterPrinter.Builder(1).connectors(_connTypeList).build();
            Connector connector1 = new Connector(0, Connector.Type.PERIPHERAL, sisterPrinter);

            GoAmateur goAmateur = new GoAmateur.Builder(1).connectors(_connTypeList).build();
            Connector connector2 = new Connector(0, Connector.Type.COMPUTER, goAmateur);

            connector1.setPeer(connector2);
            //setting the 1st connector's device's connector list
            ((AbstractDevice)connector1.getDevice()).setConnectors(Arrays.asList(connector1));

            //should error since this would create a cycle between 1 and 2
            connector2.setPeer(connector1);
        }
    }

    public static class IsReachableFixtures {

        private final List<Connector.Type> _connTypeList = Arrays.asList(Connector.Type.PERIPHERAL);
        private final SisterPrinter _sisterPrinter = new SisterPrinter.Builder(1).connectors(_connTypeList).build();
        private Connector _connector1 = new Connector(0, Connector.Type.PERIPHERAL, _sisterPrinter);
        private final GoAmateur _goAmateur = new GoAmateur.Builder(1).connectors(_connTypeList).build();
        private Connector _connector2 = new Connector(0, Connector.Type.COMPUTER, _goAmateur);

        @Test
        public void Should_be_reachable() throws ConnectionException{
            Connector tempConnector = copyConnectorWithType(_connector1, _connector1.getType());
            tempConnector.setPeer(_connector2);
            _sisterPrinter.setConnectors(Arrays.asList(tempConnector));
            assertTrue(tempConnector.isReachable(_connector2.getDevice()));
        }
        @Test
        public void Should_not_be_reachable() throws ConnectionException{
            assertFalse(_connector1.isReachable(_connector2.getDevice()));
        }
    }
}

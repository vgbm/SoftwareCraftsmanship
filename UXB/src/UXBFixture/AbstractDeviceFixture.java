package UXBFixture;

import UXB.Connector;
import UXB.Exceptions.ConnectionException;
import UXB.Hub;
import UXB.Peripherals.Printers.SisterPrinter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by james on 9/27/16.
 *
 * Tests new methods for Abstract device
 * Since abstracts cannot be instantiated, the tests will make concrete devices.
 * I do not want to litter the concrete device fixtures, so I am adding these tests here.
 */

public class AbstractDeviceFixture {

    private static final List<Connector.Type> _peripheralTypeList = Arrays.asList(Connector.Type.PERIPHERAL, Connector.Type.PERIPHERAL);
    private static final List<Connector.Type> _hubTypeList = Arrays.asList(Connector.Type.COMPUTER, Connector.Type.PERIPHERAL);

    public static class peerDevicesFixture {

        @Test
        public void Should_return_all_peer_devices() throws ConnectionException{
            SisterPrinter baseDevice = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            Hub peer1 = new Hub.Builder(1).connectors(_hubTypeList).build();
            Hub peer2 = new Hub.Builder(2).connectors(_hubTypeList).build();

            Connector baseDeviceConn0 = baseDevice.getConnector(0);
            Connector baseDeviceConn1 = baseDevice.getConnector(1);
            Connector peer1Conn = peer1.getConnector(0);
            Connector peer2Conn = peer2.getConnector(0);

            baseDeviceConn0.setPeer(peer1Conn);
            baseDeviceConn1.setPeer(peer2Conn);

            assertTrue(baseDevice.peerDevices().contains(peer1));
            assertTrue(baseDevice.peerDevices().contains(peer2));
        }

        @Test
        public void Should_return_an_empty_list_for_new_devices() {
            SisterPrinter device = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            assertTrue(device.peerDevices().size() == 0);
        }

    }

    public static class reachableDevicesFixture {

        @Test
        public void Should_return_only_reachable_devices_with_multiple_levels() throws ConnectionException{
            SisterPrinter baseDevice = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            Hub peer1 = new Hub.Builder(1).connectors(_hubTypeList).build();
            Hub peer2 = new Hub.Builder(2).connectors(_hubTypeList).build();
            Hub peer3 = new Hub.Builder(3).connectors(_hubTypeList).build();

            Connector baseDeviceConn0 = baseDevice.getConnector(0);
            Connector baseDeviceConn1 = baseDevice.getConnector(1);
            Connector peer1Conn = peer1.getConnector(0);
            Connector peer3Conn = peer3.getConnector(0);

            baseDeviceConn0.setPeer(peer1Conn);
            baseDeviceConn1.setPeer(peer3Conn);

            assertTrue(baseDevice.reachableDevices().contains(peer1));
            assertFalse(baseDevice.reachableDevices().contains(peer2));
            assertTrue(baseDevice.reachableDevices().contains(peer3));
        }

    }

    public static class isReachableFixture {

        @Test
        public void Should_return_nonreachable_devices_as_false() throws ConnectionException{
            SisterPrinter baseDevice = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            Hub peer1 = new Hub.Builder(1).connectors(_hubTypeList).build();

            assertFalse(baseDevice.isReachable(peer1));
        }

        @Test
        public void Should_return_reachable_devices_as_true_with_multiple_levels() throws ConnectionException{
            SisterPrinter baseDevice = new SisterPrinter.Builder(1).connectors(_peripheralTypeList).build();
            Hub peer1 = new Hub.Builder(1).connectors(_hubTypeList).build();
            Hub peer2 = new Hub.Builder(2).connectors(_hubTypeList).build();
            Hub peer3 = new Hub.Builder(3).connectors(_hubTypeList).build();

            Connector baseDeviceConn0 = baseDevice.getConnector(0);
            Connector baseDeviceConn1 = baseDevice.getConnector(1);
            Connector peer1Conn = peer1.getConnector(0);
            Connector peer3Conn = peer3.getConnector(0);

            baseDeviceConn0.setPeer(peer1Conn);
            baseDeviceConn1.setPeer(peer3Conn);

            assertTrue(baseDevice.isReachable(peer1));
            assertTrue(baseDevice.isReachable(peer3));
        }
    }
}

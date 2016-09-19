package UXBFixture;

import UXB.Connector;
import UXB.Hub;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by james on 9/16/16.
 */
public class HubFixture {

    Hub.Builder _builder;

    private final Integer _version = 6;
    private final Optional<Integer> _productCode = Optional.of(123);
    private final Optional<BigInteger> _serialNumber = Optional.of(BigInteger.valueOf(321));

    @Before
    public void setUp() {
        _builder = new Hub.Builder(_version)
                .productCode(_productCode.get())
                .serialNumber(_serialNumber.get());
    }

    @Test
    public void Should_create_new_hub_without_error_and_with_correct_values() {
        List<Connector.Type> connList = Arrays.asList(Connector.Type.COMPUTER, Connector.Type.PERIPHERAL);
        Hub hub = _builder.connectors(connList)
                    .build();

        assertEquals(hub.getVersion(), _version);
        assertEquals(hub.getConnectorCount(), new Integer(connList.size()));

        //TODO make conn list a list of connectors
        assertEquals(hub.getConnectors(), connList);
        assertEquals(hub.getProductCode(), _productCode);
        assertEquals(hub.getSerialNumber(), _serialNumber);
    }

    @Test(expected=IllegalStateException.class)
    public void Should_create_new_hub_with_errors_since_connection_list_is_empty() throws Exception{
        List<Connector.Type> connList = new ArrayList<>();
        Hub hub = _builder.connectors(connList)
                .build();
    }

    @Test(expected=IllegalStateException.class)
    public void Should_create_new_hub_with_errors_since_connection_list_is_missing_a_connector_type() throws Exception{
        List<Connector.Type> connList = Arrays.asList(Connector.Type.COMPUTER);
        Hub hub = _builder.connectors(connList)
                .build();
    }
}

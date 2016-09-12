package UXB;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/12/16.
 */
public interface Device {

    Optional<Integer> getProductCode();

    Optional<BigInteger> getSerialNumber();

    Integer getVersion();

    DeviceClass getDeviceClass();

    Integer getConnectorCount();

    List<Connector.Type> getConnectors();

    Connector getConnector(int index);

}

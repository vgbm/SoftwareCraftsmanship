package UXB;

import UXB.Messages.BinaryMessage;
import UXB.Messages.StringMessage;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/12/16.
 *
 * Represents a device using the UXB
 */
public interface Device {

    Optional<Integer> getProductCode();

    Optional<BigInteger> getSerialNumber();

    Integer getVersion();

    DeviceClass getDeviceClass();

    Integer getConnectorCount();

    //Return a list of all the connectors attached to this device
    List<Connector> getConnectors();

    Connector getConnector(int index);

    //signifies the arrival of a BinaryMessage at the connector
    void recv(BinaryMessage message, Connector connector);

    //signifies the arrival of a StringMessage at the connector
    void recv(StringMessage message, Connector connector);
}

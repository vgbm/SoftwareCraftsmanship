package UXB;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by james on 9/12/16.
 *
 * Provides the basic functionality and requirements for devices using the USB
 */
public class AbstractDevice<T extends AbstractDevice.Builder<T>> implements Device {

    private final Integer version;
    private final Optional<Integer> productCode;
    private final Optional<BigInteger> serialNumber;

    //using a list of connectors rather than types because using the full connectors makes operations
    //like getConnector easier to read and perform
    private final List<Connector> connectorList;

    protected AbstractDevice(Builder<T> builder) {
        this.productCode = builder.productCode;
        this.serialNumber = builder.serialNumber;
        this.version = builder.version;
        this.connectorList = builder.connectorList;
    }

    @Override
    public Optional<Integer> getProductCode() {
        return productCode.isPresent() ? productCode : Optional.empty();
    }

    @Override
    public Optional<BigInteger> getSerialNumber() {
        return serialNumber.isPresent() ? serialNumber : Optional.empty();
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public DeviceClass getDeviceClass() {
        return null;
    }

    @Override
    public Integer getConnectorCount() {
        return connectorList.size();
    }

    @Override
    // returns a list of connectors
    public List<Connector.Type> getConnectors() {
        return connectorList.stream()
                .map(connector -> connector.getType())
                .collect(Collectors.toList());
    }

    @Override
    // returns the connector of the device at a given index
    public Connector getConnector(int index) {
        return connectorList.stream()
                .filter(connector -> connector.getIndex() == index)
                .findFirst()
                .get();
    }

    public static abstract class Builder<T> {

        private Integer version;
        private Optional<Integer> productCode;
        private Optional<BigInteger> serialNumber;
        private List<Connector> connectorList;

        public Builder(Integer version){
            this.version = version;
            productCode = Optional.empty();
            serialNumber = Optional.empty();
            connectorList = new ArrayList<>();
        }

        public T productCode (Integer productCode) {
            this.productCode = productCode == null ? Optional.empty() : Optional.of(productCode);
            return getThis();
        }

        public T serialNumber (BigInteger serialNumber) {
            this.serialNumber = serialNumber == null ? Optional.empty() : Optional.of(serialNumber);
            return getThis();
        }

        public T connectors (List<Connector> connectors) {
            //initializing connector list with connectors to prevent copying reference
            this.connectorList = connectors == null ? new ArrayList<>() : new ArrayList<>(connectors);
            return getThis();
        }

        protected abstract T getThis();

        protected List<Connector.Type> getConnectors() {
            return connectorList.stream()
                    .map(connector -> connector.getType())
                    .collect(Collectors.toList());
        }

        protected void validate() {
            if(version == null)
                throw new NullPointerException("A version was not given for this device.");
        }
    }
}

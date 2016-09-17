package UXB;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/12/16.
 *
 * Provides the basic functionality and requirements for devices using the USB
 */
public abstract class AbstractDevice<T extends AbstractDevice.Builder<T>> implements Device {

    private final Integer version;
    private final Optional<Integer> productCode;
    private final Optional<BigInteger> serialNumber;

    //list of the type of connectors attached to the device
    private final List<Connector.Type> connectorTypeList;
    private List<Connector> connectorList;

    protected AbstractDevice(Builder<T> builder) {
        this.productCode = builder.productCode;
        this.serialNumber = builder.serialNumber;
        this.version = builder.version;
        this.connectorTypeList = builder.connectorList;

        //creating list of connectors to the device
        for(int i = 0; i < connectorTypeList.size(); i++) {
            connectorList.add( new Connector(i, connectorTypeList.get(i), this) );
        }
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
        return connectorTypeList.size();
    }

    @Override
    // returns a list of connectors
    public List<Connector.Type> getConnectors() {
        return connectorTypeList;
    }

    @Override
    // returns a new connector for the device with the same type as the connector of a given index
    public Connector getConnector(int index) {
        return connectorList.get(index);
    }

    public static abstract class Builder<T> {

        private Integer version;
        private Optional<Integer> productCode;
        private Optional<BigInteger> serialNumber;

        //list of the type of connectors attached to the device
        private List<Connector.Type> connectorList;

        public Builder(Integer version){
            this.version = version;
            productCode = Optional.empty();
            serialNumber = Optional.empty();
            connectorList = new ArrayList<>();
        }

        public T productCode (Integer productCode) {
            this.productCode = Optional.ofNullable(productCode);
            return getThis();
        }

        public T serialNumber (BigInteger serialNumber) {
            this.serialNumber = Optional.ofNullable(serialNumber);
            return getThis();
        }

        public T connectors (List<Connector.Type> connectors) {
            //initializing connector list with connectors to prevent copying reference
            this.connectorList = connectors == null ? new ArrayList<>() : new ArrayList<>(connectors);
            return getThis();
        }

        protected abstract T getThis();

        protected List<Connector.Type> getConnectors() {
            return connectorList;
        }

        //Throws an error if the version is missing
        protected void validate() {
            if(version == null)
                throw new NullPointerException("A version was not given for this device.");
        }
    }
}

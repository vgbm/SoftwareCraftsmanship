package UXB;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/12/16.
 */
public class AbstractDevice<T extends AbstractDevice.Builder<T>> implements Device {

    private final Integer productCode;
    private final BigInteger serialNumber;
    private final Integer version;
    private final List<Connector.Type> connectorList;

    protected AbstractDevice(Builder<T> builder) {
        this.productCode = builder.productCode;
        this.serialNumber = builder.serialNumber;
        this.version = builder.version;
        this.connectorList = builder.connectorList;
    }

    @Override
    public Optional<Integer> getProductCode() {
        return null;
    }

    @Override
    public Optional<BigInteger> getSerialNumber() {
        return null;
    }

    @Override
    public Integer getVersion() {
        return null;
    }

    @Override
    public DeviceClass getDeviceClass() {
        return null;
    }

    @Override
    public Integer getConnectorCount() {
        return null;
    }

    @Override
    public List<Connector.Type> getConnectors() {
        return null;
    }

    @Override
    public Connector getConnector(int index) {
        return null;
    }

    public static abstract class Builder<T> {

        private Integer productCode;
        private BigInteger serialNumber;
        private Integer version;
        private List<Connector.Type> connectorList;

        public Builder(Integer version){
            this.version = version;
            productCode = null;
            serialNumber = null;
            connectorList = new ArrayList<>();
        }

//        T productCode (Integer productCode) {
//            this.productCode = productCode == null? Optional<Integer>().empty() : productCode;
//        }

        protected abstract T getThis();

        protected List<Connector.Type> getConnectors() {
            return connectorList;
        }

        //void validate
    }
}

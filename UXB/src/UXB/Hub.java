package UXB;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/12/16.
 *
 * Represents a hub for connectors to join the connections from several devices and peripherals
 * Hubs have to have at least one computer and peripheral
 * */
public class Hub extends AbstractDevice<Hub.Builder> {

    private Integer version;
    private Integer productCode;
    private BigInteger serialNumber;
    private List<Connector> connectorList;

    public Hub(Builder builder){
        super(builder);
        this.version = builder.version;
        this.productCode = builder.productCode.get();
        this.serialNumber = builder.serialNumber.get();
        this.connectorList = builder.connectorList;
    }

    @Override
    public DeviceClass getDeviceClass() {
        return DeviceClass.HUB;
    }

    public static class Builder extends AbstractDevice.Builder<Builder> {

        private Integer version;
        private Optional<Integer> productCode;
        private Optional<BigInteger> serialNumber;
        private List<Connector> connectorList;

        public Builder(Integer version){
            super(version);
            this.productCode = Optional.empty();
            this.serialNumber = Optional.empty();
            connectorList = null;
        }

        public Hub build() {

            validate();

            if(!hasAtLeastOneComputerOrPeripheral()) {
                throw new IllegalStateException("The hub is missing either a version or a connector type.");
            }
            return new Hub(this);
        }

        private boolean hasAtLeastOneComputerOrPeripheral(){
            return connectorList.stream()
                    .anyMatch(connector -> connector.getType() == Connector.Type.COMPUTER || connector.getType() == Connector.Type.PERIPHERAL);
        }

        public Builder getThis() {
            return this;
        }

    }

}

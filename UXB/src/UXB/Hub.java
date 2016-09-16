package UXB;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by james on 9/12/16.
 *
 * Represents a hub for connectors to join the connections from several devices and peripherals
 * Hubs have to have at least one computer and peripheral
 * */
public class Hub extends AbstractDevice<Hub.Builder> {

    public Hub(Builder builder){
        super(builder);
    }

    @Override
    public DeviceClass getDeviceClass() {
        return DeviceClass.HUB;
    }


    public static class Builder extends AbstractDevice.Builder<Builder> {

        public Builder(Integer version){
            super(version);
        }

        public Hub build() {

            validate();

            if(hasNullComponents()) {
                throw new IllegalStateException("The hub is missing either a version or a connector type.");
            }

            return new Hub(this);
        }

        private boolean hasNullComponents() {
            return getProductCode() == null || getSerialNumber() == null || !hasAtLeastOneComputerOrPeripheral();
        }

        private boolean hasAtLeastOneComputerOrPeripheral(){
            return getConnectorList().stream()
                    .anyMatch(connector -> connector == Connector.Type.COMPUTER || connector == Connector.Type.PERIPHERAL);
        }
        public Builder getThis() {
            return this;
        }

    }

}

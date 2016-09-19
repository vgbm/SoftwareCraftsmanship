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
    //Always return that this is a hub
    public DeviceClass getDeviceClass() {
        return DeviceClass.HUB;
    }


    public static class Builder extends AbstractDevice.Builder<Builder> {

        public Builder(Integer version){
            super(version);
        }

        public Hub build() {
            validate();

            return new Hub(this);
        }

        @Override
        //throws an error if the version is null or if a computer/peripheral connector is missing
        protected void validate() {

            //validating the version
            super.validate();

            if(!hasAtLeastOneComputerAndPeripheral()) {
                throw new IllegalStateException("Either the version is null or the hub has no connectors to computers or peripherals");
            }
        }

        private boolean hasAtLeastOneComputerAndPeripheral(){
            boolean hasComputerConnector = getConnectors().stream()
                                            .anyMatch(connector -> connector == Connector.Type.COMPUTER);
            boolean hasPeripheralConnector = getConnectors().stream()
                                            .anyMatch(connector -> connector == Connector.Type.PERIPHERAL);

            return hasComputerConnector && hasPeripheralConnector;
        }
        public Builder getThis() {
            return this;
        }

    }

}

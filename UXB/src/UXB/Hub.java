package UXB;

import UXB.Messages.BinaryMessage;
import UXB.Messages.Message;
import UXB.Messages.StringMessage;

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


    //have both of the recv methods just forward to handle recv
    //not sure if we are allowed to just remove the useless recvs since they are identical
    //or if he wants us to leave it
    public void recv(StringMessage message, Connector connector) {
        handleRecv(message, connector);
    }

    public void recv(BinaryMessage message, Connector connector) {
        handleRecv(message, connector);
    }

    //performs the functions that both message types of recvs share
    private void handleRecv(Message message, Connector connector) {
        validateRecvArguments(message, connector);
        sendMessageToAllPeersExceptIncomingConnector(message, connector);
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
                throw new IllegalStateException("The hub has no connectors to computers or peripherals");
            }
        }

        private boolean hasAtLeastOneComputerAndPeripheral(){
            return hasComputerConnector() && hasPeripheralConnector();
        }

        private boolean hasComputerConnector() {
            return getConnectors()
                    .stream()
                    .anyMatch(connType -> connType == Connector.Type.COMPUTER);
        }

        private boolean hasPeripheralConnector() {
            return getConnectors()
                    .stream()
                    .anyMatch(connType -> connType == Connector.Type.PERIPHERAL);
        }

        public Builder getThis() {
            return this;
        }

    }

}

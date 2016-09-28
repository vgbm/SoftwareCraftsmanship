package UXB;

import UXB.Messages.Message;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

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
        this.connectorTypeList = builder.connectorTypeList;

        connectorList = new ArrayList<>();

        //creating list of connectors to the device
        for(int i = 0; i < connectorTypeList.size(); i++) {
            Connector conn = new Connector(i, connectorTypeList.get(i), this);
            connectorList.add( conn );
        }
    }

    @Override
    public Optional<Integer> getProductCode() {
        return productCode;
    }

    @Override
    public Optional<BigInteger> getSerialNumber() {
        return serialNumber;
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
    public List<Connector> getConnectors() {
        return new ArrayList<>(connectorList);
    }

    public void setConnectors(List<Connector> connectorList) {
        this.connectorList = new ArrayList<>(connectorList);
    }

    @Override
    // returns a new connector for the device with the same type as the connector of a given index
    public Connector getConnector(int index) {
        return connectorList.get(index);
    }

    //Checks the paramaters sent to a device's recv and throws any appropriate errors
    //Inputs: paramaters sent to recv
    //Does not output but rather throws errors if necessary
    protected void validateRecvArguments(Message message, Connector connector) {
        if(message == null || connector == null) {
            throw new NullPointerException("Recv cannot accept null arguments");
        }

        //checks if the peer is present
        //if it is, it then checks if the peer device is the right device
        if( !this.equals(connector.getDevice()) ) {
            throw new IllegalArgumentException("The connector sent to recv does not belong to this device");
        }
    }

    public Set<Device> peerDevices() {
        return connectorList.stream()
                .filter(connector -> connector.getPeer().isPresent()) //filters out any empty peers
                .map(connector -> connector.getPeer().get().getDevice()) //gets the peer device
                .collect(Collectors.toSet());
    }

    public Set<Device> reachableDevices() {
        Set<Device> emptyDeviceSet = new HashSet<>();
        return reachableDevicesHelper(emptyDeviceSet, this, null);
    }

    private Set<Device> reachableDevicesHelper(Set<Device> deviceSet, Device currDevice, Device deviceToFind){

        peerDeviceLoop:
        for (Device neighboringDevice : currDevice.peerDevices()) {

            if( neighboringDevice.equals(deviceToFind)) {
                deviceSet.add(neighboringDevice);
                break peerDeviceLoop;
            }

            if ( !deviceSet.contains(neighboringDevice) ) {
                deviceSet.add(neighboringDevice);
                reachableDevicesHelper(deviceSet, neighboringDevice, deviceToFind);
            }
        }
        return deviceSet;
    }

    public boolean isReachable(Device device) {
        Set<Device> emptyDeviceSet = new HashSet<>();
        return reachableDevicesHelper(emptyDeviceSet, this, device).contains(device);
    }


    public static abstract class Builder<T> {

        private Integer version;
        private Optional<Integer> productCode;
        private Optional<BigInteger> serialNumber;

        //list of the type of connectors attached to the device
        private List<Connector.Type> connectorTypeList;

        public Builder(Integer version){
            this.version = version;
            productCode = Optional.empty();
            serialNumber = Optional.empty();
            connectorTypeList = new ArrayList<>();
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
            this.connectorTypeList = connectors == null ?
                                    new ArrayList<>() :
                                    new ArrayList<>(connectors);
            return getThis();
        }

        protected abstract T getThis();

        protected List<Connector.Type> getConnectors() {
            return new ArrayList<>(connectorTypeList);
        }

        //Throws an error if the version is missing
        protected void validate() {
            if(version == null)
                throw new NullPointerException("A version was not given for this device.");
        }
    }
}

package UXB;

import java.util.Optional;

/**
 * Created by james on 9/12/16.
 *
 * Connectors are the physical connections between computers and peripherals
 * No end device is required
 * */
public final class Connector {

    //Number of the connection (which port)
    private final int index;

    //Connector type (which side of the connector is being used)
    private final Type type;

    //Device using the connector (sending end)
    private final Device device;

    //the end device that is on the receiving end
    private Optional<Connector> peer;


    public Connector(int index, Type type, Device device){
        this.index = index;
        this.type = type;
        this.device = device;
        this.peer = Optional.empty();
    }

    public int getIndex() {
        return index;
    }

    public Type getType() {
        return type;
    }

    public Device getDevice() {
        return device;
    }

    public Optional<Connector> getPeer() {
        return peer;
    }

    //Types of ports that the connector can connect to
    public enum Type {
        COMPUTER,
        PERIPHERAL
    }
}

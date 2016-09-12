package UXB;

import java.util.Optional;

/**
 * Created by james on 9/12/16.
 */
public final class Connector {

    final int index;
    final Type type;
    final Device device;
    Optional<Connector> peer;

    public Connector(int index, Type type, Device device){
        this.index = index;
        this.type = type;
        this.device = device;
        this.peer = null;
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

    public enum Type {

    }
}

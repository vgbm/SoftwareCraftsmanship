package UXB.Messages;

import UXB.Connector;
import UXB.Device;

/**
 * Created by james on 9/12/16.
 */
public interface Message {

    //signifies the given device has reached the given connector
    void reach(Device device, Connector connector);
}

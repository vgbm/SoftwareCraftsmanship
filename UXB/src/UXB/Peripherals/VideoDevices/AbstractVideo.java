package UXB.Peripherals.VideoDevices;

import UXB.DeviceClass;
import UXB.Peripherals.AbstractPeripheral;

/**
 * Created by james on 9/19/16.
 *
 */
public class AbstractVideo<T extends AbstractPeripheral.Builder<T>> extends AbstractPeripheral<T> {

    public AbstractVideo(Builder builder) {
        super(builder);
    }

    @Override
    //Always return that this is a video device
    public DeviceClass getDeviceClass() {
        return DeviceClass.VIDEO;
    }



    public static abstract class Builder<T> extends AbstractPeripheral.Builder<T> {

        public Builder(Integer version) {
            super(version);
        }

    }
}

package UXB.Peripherals.Printers;

import UXB.Peripherals.AbstractPeripheral;
import UXB.DeviceClass;

/**
 * Created by james on 9/19/16.
 */
public class AbstractPrinter extends AbstractPeripheral {

    public AbstractPrinter(Builder builder) {
        super(builder);
    }

    @Override
    //Always return that this is a printer
    public DeviceClass getDeviceClass() {
        return DeviceClass.PRINTER;
    }



    public static abstract class Builder<T> extends AbstractPeripheral.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

    }
}
package UXB.Peripherals.Printers;

import UXB.Peripherals.AbstractPeripheral;
import UXB.DeviceClass;

/**
 * Created by james on 9/19/16.
 *
 */
public abstract class AbstractPrinter<T extends AbstractPrinter.Builder<T>> extends AbstractPeripheral<T> {

    protected AbstractPrinter(Builder<T> builder) {
        super(builder);
    }

    @Override
    //Always return that this is a printer
    public DeviceClass getDeviceClass() {
        return DeviceClass.PRINTER;
    }



    public static abstract class Builder<T> extends AbstractPeripheral.Builder<T> {

        public Builder(Integer version) {
            super(version);
        }

    }
}

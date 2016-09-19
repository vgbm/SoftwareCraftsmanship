package UXB.Peripherals.VideoDevices;

import UXB.AbstractDevice;
import UXB.Peripherals.Printers.AbstractPrinter;

/**
 * Created by james on 9/19/16.
 */
public class GoAmateur extends AbstractVideo {

    public GoAmateur(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractVideo.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

        public GoAmateur build() {
            super.validate();
            return new GoAmateur(this);
        }

        protected Builder getThis() {
            return this;
        }
    }
}

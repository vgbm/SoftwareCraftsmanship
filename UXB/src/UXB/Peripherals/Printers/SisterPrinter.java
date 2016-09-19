package UXB.Peripherals.Printers;

import UXB.AbstractDevice;

/**
 * Created by james on 9/19/16.
 */
public class SisterPrinter extends AbstractPrinter {


    public SisterPrinter(Builder builder) {
        super(builder);
    }



    public static class Builder extends AbstractPrinter.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

        public SisterPrinter build() {
            super.validate();
            return new SisterPrinter(this);
        }

        protected Builder getThis() {
            return this;
        }
    }
}

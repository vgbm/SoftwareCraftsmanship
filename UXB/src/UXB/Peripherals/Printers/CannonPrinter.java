package UXB.Peripherals.Printers;

/**
 * Created by james on 9/19/16.
 */
public class CannonPrinter extends AbstractPrinter {

    public CannonPrinter(Builder builder) {
        super(builder);
    }



    public static class Builder extends AbstractPrinter.Builder<Builder> {

        public Builder(Integer version) {
            super(version);
        }

        public CannonPrinter build() {
            super.validate();
            return new CannonPrinter(this);
        }

        protected Builder getThis() {
            return this;
        }
    }
}

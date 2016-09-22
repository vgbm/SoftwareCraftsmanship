package UXB.Peripherals;

import UXB.AbstractDevice;
import UXB.Connector;

/**
 * Created by james on 9/19/16.
 */
public abstract class AbstractPeripheral<T extends AbstractPeripheral.Builder<T>> extends AbstractDevice<T> {

    protected AbstractPeripheral(Builder<T> builder) {
        super(builder);
    }



    public static abstract class Builder<T> extends AbstractDevice.Builder<T> {

        public Builder(Integer version) {
            super(version);
        }

        @Override
        protected void validate() {

            //validation the version
            super.validate();

            if(!allConnectorsArePeriheralConnectors()) {
                throw new IllegalStateException("The peripheral non-peripheral connector types");
            }
        }

        //checks the non-null connector types and returns whether or not they are of Connector Type peripheral
        private boolean allConnectorsArePeriheralConnectors() {
            //not using parallel streams due to high overhead and the num of connections is usually low
            return getConnectors()
                    .stream()
                    .filter(connType -> connType != null)
                    .allMatch(connType -> connType == Connector.Type.PERIPHERAL);
        }

    }

}

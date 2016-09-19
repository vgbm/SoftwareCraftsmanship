package UXB;

/**
 * Created by james on 9/19/16.
 */
public abstract class AbstractPeripheral<T extends AbstractPeripheral.Builder<T>> extends AbstractDevice {

    public AbstractPeripheral(Builder builder) {
        super(builder);
    }



    public static abstract class Builder<T> extends AbstractDevice.Builder<Builder> {

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
            return getConnectors()
                    .stream()
                    .filter(connType -> connType != null)
                    .allMatch(connType -> connType == Connector.Type.PERIPHERAL);
        }

    }

}

package UXB;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by james on 9/12/16.
 */
public class Hub extends AbstractDevice<Hub.Builder> {

    public Hub(Builder builder){

    }

    @Override
    DeviceClass getDeviceClass() {
        return DeviceClass.HUB;
    }

    public static class Builder extends AbstractDevice.Builder<Builder> {

        private Integer productCode;
        private BigInteger serialNumber;
        private Integer version;
        private List<Connector.Type> connectorList;

        public Builder(Integer version){

        }

        public Builder getThis() {
            return this;
        }

    }

}

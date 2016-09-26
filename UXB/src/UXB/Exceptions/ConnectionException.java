package UXB.Exceptions;

import UXB.Connector;

import java.sql.Connection;

/**
 * Created by james on 9/26/16.
 */
public class ConnectionException extends Exception {

    private static final long serialVersionUID = 293L;

    private final Connector connector;
    private final ErrorCode errorCode;

    public enum ErrorCode {
        CONNECTOR_BUSY,
        CONNECTOR_MISMATCH,
        CONNECTION_CYCLE
    }

    public ConnectionException(Connector connector, ErrorCode errorCode) {
        this.connector = connector;
        this.errorCode = errorCode;
    }

    public Connector getConnector() {
        return connector;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

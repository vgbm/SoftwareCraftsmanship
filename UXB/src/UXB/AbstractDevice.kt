package UXB

import UXB.Messages.Message

import java.math.BigInteger
import java.util.*
import java.util.stream.Collectors

/**
 * Created by james on 9/12/16.

 * Provides the basic functionality and requirements for devices using the USB
 */
abstract class AbstractDevice<T : AbstractDevice.Builder<T>> protected constructor(builder: AbstractDevice.Builder<T>) : Device {

    private val version: Int?
    private val productCode: Optional<Int>
    private val serialNumber: Optional<BigInteger>

    //list of the type of connectors attached to the device
    private val connectorTypeList: List<Connector.Type>
    private var connectorList: MutableList<Connector>? = null

    init {
        this.productCode = builder.productCode
        this.serialNumber = builder.serialNumber
        this.version = builder.version
        this.connectorTypeList = builder.connectorTypeList

        connectorList = ArrayList<Connector>()

        //creating list of connectors to the device
        for (i in connectorTypeList.indices) {
            val conn = Connector(i, connectorTypeList[i], this)
            connectorList!!.add(conn)
        }
    }

    override fun getProductCode(): Optional<Int> {
        return productCode
    }

    override fun getSerialNumber(): Optional<BigInteger> {
        return serialNumber
    }

    override fun getVersion(): Int? {
        return version
    }

    override fun getDeviceClass(): DeviceClass? {
        return null
    }

    override fun getConnectorCount(): Int? {
        return connectorTypeList.size
    }

    override // returns a list of connectors
    fun getConnectors(): List<Connector> {
        return ArrayList(connectorList!!)
    }

    fun setConnectors(connectorList: List<Connector>) {
        this.connectorList = ArrayList(connectorList)
    }

    override // returns a new connector for the device with the same type as the connector of a given index
    fun getConnector(index: Int): Connector {
        return connectorList!![index]
    }

    //Checks the paramaters sent to a device's recv and throws any appropriate errors
    //Inputs: paramaters sent to recv
    //Does not output but rather throws errors if necessary
    protected fun validateRecvArguments(message: Message?, connector: Connector?) {
        if (message == null || connector == null) {
            throw NullPointerException("Recv cannot accept null arguments")
        }

        //checks if the peer is present
        //if it is, it then checks if the peer device is the right device
        if (this != connector.device) {
            throw IllegalArgumentException("The connector sent to recv does not belong to this device")
        }
    }

    //forwards message to all peer connectors, barring incomingConnector
    //if incomingConnector is null, this should send the message to all peers
    //Allowing message to be null as the error will be picked up by validateRecvArguments
    protected fun sendMessageToAllPeersExceptIncomingConnector(message: Message, incomingConnector: Connector) {
        this.connectors.filter({ conn -> conn.getPeer().isPresent() && conn != incomingConnector }).map({ connsWithPeers -> connsWithPeers.getPeer().get() }) // grab the peer connectors
                .forEach({ peerConns -> peerConns.recv(message) })
    }

    override fun peerDevices(): Set<Device> {
        return connectorList!!.stream().filter({ connector -> connector.getPeer().isPresent() }) //filters out any empty peers
                .map({ connector -> connector.getPeer().get().getDevice() }) //gets the peer device
                .collect(Collectors.toSet<Device>())
    }

    override fun reachableDevices(): Set<Device> {
        val emptyDeviceSet = HashSet<Device>()
        return reachableDevicesHelper(emptyDeviceSet, this, null)
    }

    private fun reachableDevicesHelper(deviceSet: MutableSet<Device>, currDevice: Device, deviceToFind: Device?): Set<Device> {

        peerDeviceLoop@ for (neighboringDevice in currDevice.peerDevices()) {

            if (neighboringDevice == deviceToFind) {
                deviceSet.add(neighboringDevice)
                break@peerDeviceLoop
            }

            if (!deviceSet.contains(neighboringDevice)) {
                deviceSet.add(neighboringDevice)
                reachableDevicesHelper(deviceSet, neighboringDevice, deviceToFind)
            }
        }
        return deviceSet
    }

    override fun isReachable(device: Device): Boolean {
        val emptyDeviceSet = HashSet<Device>()
        return reachableDevicesHelper(emptyDeviceSet, this, device).contains(device)
    }


    abstract class Builder<T>(private val version: Int?) {
        private var productCode: Optional<Int>? = null
        private var serialNumber: Optional<BigInteger>? = null

        //list of the type of connectors attached to the device
        private var connectorTypeList: List<Connector.Type>? = null

        init {
            productCode = Optional.empty<Int>()
            serialNumber = Optional.empty<BigInteger>()
            connectorTypeList = ArrayList<Type>()
        }

        fun productCode(productCode: Int?): T {
            this.productCode = Optional.ofNullable<Int>(productCode)
            return `this`
        }

        fun serialNumber(serialNumber: BigInteger): T {
            this.serialNumber = Optional.ofNullable(serialNumber)
            return `this`
        }

        fun connectors(connectors: List<Connector.Type>?): T {
            //initializing connector list with connectors to prevent copying reference
            this.connectorTypeList = if (connectors == null)
                ArrayList<Type>()
            else
                ArrayList(connectors)
            return `this`
        }

        protected abstract val `this`: T

        protected val connectors: List<Connector.Type>
            get() = ArrayList(connectorTypeList!!)

        //Throws an error if the version is missing
        protected open fun validate() {
            if (version == null)
                throw NullPointerException("A version was not given for this device.")
        }
    }
}

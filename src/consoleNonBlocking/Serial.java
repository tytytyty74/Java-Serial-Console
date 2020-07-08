package consoleNonBlocking;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

/**
 * class to handle serial port transactions and setup.
 * @author Ty
 *
 */
public class Serial {
    SerialPort port;
    int portNum;
    int baud = 9600;
    
    public Serial(int port){
        this.portNum = port;
        this.port = SerialPort.getCommPorts()[portNum];
    }
    
    /**
     * initializes the serial port with the default values
     * @return if the initialization went successfully
     */
    public boolean init()
    {
        boolean retval = true;
        retval &= port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        retval &= port.setBaudRate(baud);
        retval &= port.setNumDataBits(8);
        retval &= port.setNumStopBits(1);
        retval &= port.setParity(SerialPort.NO_PARITY);
        retval &= port.openPort();
        return retval;
    }
    
    public void setBaud(int baud)
    {
        this.baud = baud;
    }
    
    
    /**
     * writes the bytes in toWrite to the serial port
     * @param toWrite
     */
    public void write(byte[] toWrite)
    {
        port.writeBytes(toWrite, toWrite.length);
    }

    public void close() {
        // TODO Auto-generated method stub
        port.closePort();
    }

    public boolean addInterrupt(SerialPortDataListener interrupt) {
        // TODO Auto-generated method stub
        return port.addDataListener(interrupt);
    }

}

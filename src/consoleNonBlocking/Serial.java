package consoleNonBlocking;

import com.fazecast.jSerialComm.SerialPort;

/**
 * class to handle serial port transactions and setup.
 * @author Ty
 *
 */
public class Serial {
    SerialPort port;
    int portNum;
    
    public Serial(int port){
        this.portNum = port;
    }
    
    /**
     * initializes the serial port with the default values
     * @return if the initialization went successfully
     */
    public boolean init()
    {
        boolean retval = true;
        port = SerialPort.getCommPorts()[portNum];
        retval &= port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        retval &= port.setBaudRate(9600);
        retval &= port.setNumDataBits(8);
        retval &= port.setNumStopBits(1);
        retval &= port.setParity(SerialPort.NO_PARITY);
        retval &= port.addDataListener(new SerialPrintInterrupt(20));
        retval &= port.openPort();
        return retval;
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

}

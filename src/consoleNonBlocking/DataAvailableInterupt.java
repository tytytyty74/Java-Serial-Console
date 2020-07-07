package consoleNonBlocking;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 * The interupt that is called in the event that serial port it is attached to is written to by another device.
 * @author Ty
 *
 */
public class DataAvailableInterupt implements SerialPortDataListener {
    
    
    Queue<Byte> buffer;
    int bufferSize;
    /**
     * creates the object with a byte buffer of bufferSize
     * @param bufferSize the size of the buffer to be created
     * @throws IllegalArgumentException - if bufferSize > 0
     */
    public DataAvailableInterupt(int bufferSize) throws IllegalArgumentException{
        buffer = new PriorityQueue<Byte>(bufferSize);
        this.bufferSize = bufferSize;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent eventData) {
        // TODO Auto-generated method stub
        for (byte character : eventData.getReceivedData()) {
            buffer.add(character);
        }
    }
    
    /**
     * gets the new available bytes in the buffer, in the order that they came in.
     * @return an array of the bytes that have been sent in since the last call to this function
     */
    public byte[] getAvailableBytes()
    {
        byte[] retval = new byte[buffer.size()];
        int i = 0;
        for (Byte character : buffer) {
            retval[i] = character;
        }
        buffer = new PriorityQueue<Byte>(bufferSize);
        return retval;
    }
    
    /**
     * checks if there are new bytes to be returned.
     * @return if there are any new bytes
     */
    public boolean bytesAvailable()
    {
        return !buffer.isEmpty();
        
    }

}

package consoleNonBlocking;

import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * takes incoming data from the serial port and prints it to the console
 * @author Ty
 *
 */
public class SerialPrintInterrupt extends DataAvailableInterupt {

    public SerialPrintInterrupt(int bufferSize) throws IllegalArgumentException {
        super(bufferSize);
    }
    
    @Override
    public void serialEvent(SerialPortEvent eventData) {
        super.serialEvent(eventData);
        System.out.print(new String(getAvailableBytes()));
    }
}

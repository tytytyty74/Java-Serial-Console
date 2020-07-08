package consoleNonBlocking;

import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialHexDumpInterupt extends DataAvailableInterupt {

    public SerialHexDumpInterupt(int bufferSize) throws IllegalArgumentException {
        super(bufferSize);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void serialEvent(SerialPortEvent eventData) {
        super.serialEvent(eventData);
        byte[] recieved = super.getAvailableBytes();
        for (int i = 0; i < recieved.length; i++) {
            System.out.println(String.format("%02X %c", recieved[i], 
                    isAsciiPrintable(recieved[i])?new String(new byte[] {recieved[i]}).charAt(0):'.'));
        }
    }
    
    private static boolean isAsciiPrintable(byte ch) {
        return ch >= 32 && ch < 127;
    }
}

package serialTest;

import java.util.Arrays;


import com.fazecast.jSerialComm.SerialPort;

/**
 * a test to work over serial using the console. will graduate to GUI later
 * @author Ty
 *
 */
public class ConsoleTest {
    public ConsoleTest() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) throws InterruptedException {
        int tests =10;
        System.out.println(Arrays.deepToString(SerialPort.getCommPorts()));
        SerialPort port1 = SerialPort.getCommPorts()[2];
        port1.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        port1.setBaudRate(9600);
        port1.setNumDataBits(8);
        port1.setNumStopBits(1);
        port1.setParity(SerialPort.NO_PARITY);
        port1.openPort();
        for (int i = 0; i < tests; i++) {
            while (port1.bytesAvailable() == 0)
            {
                Thread.sleep(20);
                i++;
            }
            byte[] readBuffer = new byte[port1.bytesAvailable()];
            port1.readBytes(readBuffer, readBuffer.length);
            System.out.print(new String(readBuffer));
        }
        for (int i = 0; i < 0xF; i++) {
            System.out.println(Integer.toHexString(i));
            port1.writeBytes(Integer.toHexString(i).getBytes(), 1);
            Thread.sleep(500);
        }
        
        port1.closePort();
    }
}

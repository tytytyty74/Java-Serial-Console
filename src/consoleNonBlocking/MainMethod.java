package consoleNonBlocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.sql.rowset.serial.SerialDatalink;

import com.fazecast.jSerialComm.SerialPort;

public class MainMethod {

    public MainMethod() {
        // TODO Auto-generated constructor stub
    }
    public static void main(String[] args) {
        final int[] BAUDS = new int[] {9600, 57600};
        char endOfProgram = '!';
        
        Console in = new Console(endOfProgram);
        in.init();
        int port = in.getUserInt("choose which port index to use from the following array\n"+
        Arrays.deepToString(SerialPort.getCommPorts()), 0, SerialPort.getCommPorts().length-1, -1);
        int baud = in.getUserInt("choose baud rate: type 0 for 9600, or 1 for 57600", 0, 1, -1);
        DataAvailableInterupt serialData = new DataAvailableInterupt(20);
        Serial port1 = new Serial(port);
        port1.setBaud(BAUDS[baud]);
        port1.addInterrupt(serialData);
        port1.init();
        in.printHelp();
        try {
            while (in.shouldRun)
            {
                if (in.hasData())
                {
                    //System.out.println("working");
                    byte[] toWrite = in.getParsedData();
                    port1.write(toWrite);
                }
                if (serialData.bytesAvailable())
                {
                    byte[] recieved = serialData.getAvailableBytes();
                    for (int i = 0; i < recieved.length; i++) {
                        System.out.println(String.format("%02X %c", recieved[i], 
                                isAsciiPrintable(recieved[i])?new String(new byte[] {recieved[i]}).charAt(0):'.'));
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        port1.close();
        System.out.println("exiting");
    }
    private static boolean isAsciiPrintable(byte ch) {
        return ch >= 32 && ch < 127;
    }
}

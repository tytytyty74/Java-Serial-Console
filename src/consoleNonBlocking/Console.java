package consoleNonBlocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.fazecast.jSerialComm.SerialPort;

public class Console {
    private InputStreamReader consoleIn;
    private BufferedReader consoleReader;
    private char endOfProgram;
    boolean shouldRun = true;
    
    public Console(char end) {
        endOfProgram = end;
    }
    
    public void init() {
        consoleIn = new InputStreamReader(System.in);
        consoleReader = new BufferedReader(consoleIn);
    }
    
    public int getPortNum()
    {
        int port = -1;
        System.out.println("serial ports available: \n"+Arrays.toString(SerialPort.getCommPorts()));
        System.out.print("which would you like to use?\n> ");
        while (port == -1)
        {
            
            try {
                if (consoleReader.ready())
                {
                    try {
                        int temp = Integer.parseInt(consoleReader.readLine());
                        if (temp >= 0 && temp < SerialPort.getCommPorts().length) {
                            port = temp;
                        }
                        else {
                            System.out.println("port out of range");
                            System.out.println("serial ports available: \n"+Arrays.toString(SerialPort.getCommPorts()));
                            System.out.print("which would you like to use?\n> ");
                        }
                    } catch (NumberFormatException e) {
                       System.out.println("not a number");
                       System.out.println("serial ports available: \n"+Arrays.toString(SerialPort.getCommPorts()));
                       System.out.print("which would you like to use?\n> ");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        printHelp();
        return port;
    }
    
    public void printHelp() {
        System.out.println("port found. nonblocking read and write console enabled.");
        System.out.println(" - '!' to exit");
        System.out.println(" - 0xn to send over the raw byte(s) n in hex.");
        System.out.println("     if an odd number of hex digits, will ignore the last digit");
    }

    public boolean hasData() throws IOException
    {
        return consoleReader.ready();
        
    }
    
    public byte[] getParsedData() throws IOException
    {
        byte[] retval = consoleReader.readLine().getBytes();
        if (retval[0] == endOfProgram)
        {
            shouldRun = false;
        }
        else if(retval.length > 1 && new String(retval).substring(0, 2).toLowerCase().equals("0x"))
        {
            String workingVal = new String(retval).substring(2);
            retval = new byte[workingVal.length()/2];
            for (int i = 0; i < retval.length; i++) {
                int index = i*2;
                int j = Integer.parseInt(workingVal.substring(index, index+2), 16);
                retval[i] = (byte) j;
            }
        }
        return retval;
    }
    public void close() throws IOException
    {
        consoleReader.close();
    }
}

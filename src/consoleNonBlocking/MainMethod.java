package consoleNonBlocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class MainMethod {

    public MainMethod() {
        // TODO Auto-generated constructor stub
    }
    public static void main(String[] args) {
        char endOfProgram = '!';
        
        Console in = new Console(endOfProgram);
        in.init();
        int port = in.getPortNum();
        Serial port1 = new Serial(2);
        port1.init();
        boolean running = true;
        try {
            while (in.shouldRun)
            {
                if (in.hasData())
                {
                    //System.out.println("working");
                    byte[] toWrite = in.getParsedData();
                    port1.write(toWrite);
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

}

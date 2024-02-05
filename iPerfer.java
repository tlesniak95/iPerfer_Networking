import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
/*
 * 
 */
public class iPerfer {
    /*
     * To operate Iperfer in client mode, it should be invoked as follows:
     * java Iperfer -c -h <server hostname> -p <server port> -t <time>
     */
    public static void main(String[] args) {


        //check if in client or server mode
        if(args[0].equals("-c")) {
            //client mode
            //check nunmber of arguments
            if(args.length != 7) {
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
            String hostname = args[2];
            int port = Integer.parseInt(args[4]);
            int time = Integer.parseInt(args[6]);

            //check port number is in the range 1024 to 65535
            if(port < 1024 || port > 65535) {
                System.out.println("Error: port number must be in the range 1024 to 65535");
                System.exit(1);
            }
            
            runClient(hostname, port, time);

        }
}


private static void runClient(String hostname, int port, int time) {

    try( Socket clientSocket = new Socket(hostname, port);
        OutputStream outputStream = clientSocket.getOutputStream()) {

            byte[] data = new byte[1000]; //1000-byte array filled with zeros
            long startTime = System.currentTimeMillis();
            long endTime = startTime + time * 1000L; //Converting time to milliseconds
            long totalBytesSent = 0;

            //start sending data
            while(System.currentTimeMillis() < endTime) {
                outputStream.write(data);
                totalBytesSent += data.length;
            }

            double rate = (totalBytesSent * 8) / (time * 1000.0 * 1000.0); //rate in Megabits per second

            System.out.printf("sent=%d KB %.3f Mbps\n", totalBytesSent / 1000, rate);
        }

    catch (Exception e) {
        e.printStackTrace();
    }    
}

}
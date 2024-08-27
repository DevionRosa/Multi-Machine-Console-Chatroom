import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Thread;
import java.net.*;
import java.io.*;
public class ChatClient{
    public static void main(String[] args) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";

        if (args.length != 2) {
            System.err.println(
			       "Usage: java ChatClient <host name> <port number>");
            System.exit(1);
        }
 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
 
        try(Socket socket = new Socket(hostName, portNumber)){
            //Connect to the server by instantiating a Socket object
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);//Set the Socket output stream to a PrintWriter object
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));//Set the Socket input stream to a Buffered Reader           	    
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //Read in username from terminal
            System.out.print(ANSI_RED + "Create a Username: " + ANSI_RESET);
            String username = reader.readLine(); //Reads in username
            socketOut.println(username);//Send username string to server
            ClientReceiver ClientReceiver = new ClientReceiver(stdIn);//Fire up a ClientReceiver thread
            ClientReceiver.start();
            
            //Chatting below
            Thread.sleep(500);
            String userInput;
            while ((userInput = reader.readLine()) != null) {
                //moves the cursor to clear the line of the terminal input
                System.out.print("\u001B[s");
                System.out.print("\u001B[1A");
                System.out.print("\u001B[2K"); //Clears the line

                socketOut.println(userInput); //Sends input to the server
                Thread.sleep(500);
                System.out.print("\u001B[u");
            }

        } catch (Exception e) {
            System.err.println("ChatClient: " + e);
            System.exit(1);
        }
    }
}

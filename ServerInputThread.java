import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Thread;
import java.net.*;
import java.io.*;

public class ServerInputThread extends Thread{

	private Socket currConnection; //Tracks current connection
	private String username; //Stores client's username
	private BufferedReader inputStream; //Reads data from server to client
	private String color;
	private static volatile int counter = 0;

	String ANSI_RESET = "\u001B[0m";
	String ANSI_RED = "\u001B[31m";

    public void run(){
		try{
			username = inputStream.readLine();//Read in a line representing the username
			ChatServer.connections.add(currConnection);//Add a new socket to the static connection list
						
			ChatServer.messages.add(ANSI_RED + username + " has joined the chat room!" + ANSI_RESET);
			
			//Continually read input from the client socket
			//Add that input to the messages list with the username infront "JR: lorum ipsum"
			
			String full_message;
			while((full_message = inputStream.readLine()) != null){
				full_message = color + username + ": " + full_message + ANSI_RESET;
				ChatServer.messages.add(full_message);
			}
		
		}catch(Exception e){
			System.out.println("ServerInputThread (run): " + e);
			System.exit(1);
		}
    }

    public ServerInputThread(Socket clientSocket){
		if(counter == ChatServer.colors.size()){
			counter = 0;
		}
		color = ChatServer.colors.get(counter);
		counter = counter + 1;
		try{
	    	//Set the appropriate fields 
			currConnection = clientSocket;
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		}catch(Exception e){
	    	System.out.println("ServerInputThread (Constructor)"+e);
	    	System.exit(1);
		}
    }
}

import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Thread;
import java.net.*;
import java.io.*;
public class ChatServer{
    public static volatile ArrayList<Socket> connections = new ArrayList<>();
    public static volatile LinkedList<String> messages = new LinkedList<>();
	public static ArrayList<String> colors = new ArrayList<>();

	//Puts colors indide the arraylist that users are assigned
	static{

		String ANSI_ORANGE = "\u001B[33m";
		String ANSI_CYAN = "\u001B[36m";
		String ANSI_GREEN = "\u001B[32m";
		String ANSI_BLUE = "\u001B[34m";
		String ANSI_INDIGO = "\u001B[35m";
		String ANSI_GREY = "\u001B[90m";
		colors.add(ANSI_ORANGE);
		colors.add(ANSI_CYAN);
		colors.add(ANSI_GREEN);
		colors.add(ANSI_BLUE);
		colors.add(ANSI_INDIGO);
		colors.add(ANSI_GREY);
	}

	 
    public static void main(String[] args){
		try{
			ServerOutputThread ServerOutputThread = new ServerOutputThread();//Fire up a ServerOutputThread
			ServerOutputThread.start();

			//Instantiate a ServerSocket in the below try parenthesis
			String IP_Address = args[0];
			try(ServerSocket serverSocket = new ServerSocket(0,0, InetAddress.getByName(IP_Address))){ //

				System.out.println("IP Address " + serverSocket.getInetAddress());//Print out the IP address using getInetAddress()
				System.out.println("Port " + serverSocket.getLocalPort());//Print out the ephemeral Port using getLocalPort()

				//Construct a while loop to continually accept sockets
				while(true){
					Socket clientSocket = serverSocket.accept();
					connections.add(clientSocket);
					ServerInputThread inputThread = new ServerInputThread(clientSocket);
					inputThread.start();
				}

			}
			catch(Exception e){
				System.out.println("ChatServer: "+e);
			}
		}
		catch(Exception e){
			System.out.println("ChatServer: "+e);
		}
    }
}

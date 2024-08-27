import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Thread;
import java.net.*;
import java.io.*;
public class ServerOutputThread extends Thread{
    public void run(){
		//Continually loop through all messages
		//If queue is not empty then pop message off the queue
		//Loop through all connections and send popped message to each connection
		//Sleep for 100 miliseconds if the queue is currently empty

		try{
			while(true){
				if(ChatServer.messages.isEmpty()){
					Thread.sleep(100);
				}
				else{
					String message = ChatServer.messages.peek();
					ChatServer.messages.remove();
					for(Socket socket : ChatServer.connections){
						PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
						socketWriter.println(message);
					}
				}
			}
			
		}catch(Exception e){
			System.out.println("ServerOutputThread (run): " + e);
		}
    }
}

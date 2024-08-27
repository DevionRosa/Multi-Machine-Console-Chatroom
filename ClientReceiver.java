import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Thread;
import java.net.*;
import java.io.*;
public class ClientReceiver extends Thread{
    BufferedReader in;
    public void run(){

		while(true){
			try{
				//Read in message and print if not null
				String message;
				if((message = in.readLine()) != null){
					System.out.println(message);
				}
				
				//Sleep for 100 miliseconds if message is null
				if((message = in.readLine()) == null){
					Thread.sleep(100);
				}

			}catch(Exception e){
				System.out.println("ClientReceiver: " + e);
				System.exit(1);
			}
		}
    }
	
	//Takes the input stream from the server socket connected to client instance
    ClientReceiver(BufferedReader in){
		this.in = in;
    }
}

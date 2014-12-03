import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ZipServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		try{
			serverSocket = new ServerSocket(5000);
		}
		catch(IOException e){
			System.err.println("Could not listen on port: 5000");
			System.exit(-1);
		}
		
		while(listening){
			new ClientHandler(serverSocket.accept()).start();
		}
	} 

}

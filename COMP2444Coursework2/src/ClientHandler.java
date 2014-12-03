import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Observable;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;


public class ClientHandler extends Thread implements Runnable {
	
	private Socket socket;
	BufferedReader in = null;
	BufferedWriter out = null;

	public ClientHandler(Socket client) {
		super("ClientHandler");
		this.socket = client;
	}

	@Override
	public void run() {
		try {
			System.out.println("ClientHandler running");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(socket.getOutputStream())));
			System.out.println("Got the two streams");
			String line = null;
			while((line=in.readLine())!= null){
				if(line.equals("End of transmission")){
					break;
				}
				System.out.println(line);
				out.write(line);
				out.newLine();
			}
			out.close();
			System.out.println("client handler run finished");
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

}

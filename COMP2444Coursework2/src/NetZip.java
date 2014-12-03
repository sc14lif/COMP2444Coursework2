import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class NetZip {
	
	private BufferedReader inputFileReader;
	private String fileName;
	private String NetZipFileName;
	private Socket socket;

	public NetZip(String host, int port, BufferedReader fileinput, String fileName) {
		try{
			socket = new Socket(host, port);
			inputFileReader = fileinput;
			this.fileName = fileName;
			NetZipFileName = fileName.concat(".gz");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try{
			File file = new File(args[0]);
			NetZip z = new NetZip("127.0.0.1", 5000, new BufferedReader(new FileReader(file)), args[0]);
			z.talktoServer(); 
			z.readFromServer();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	private void talktoServer() {
		//System.out.println("Trying to talk to server");
		try{
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			String content;
				while ((content = inputFileReader.readLine()) != null) {
					socketOut.println(content);
					}
				socketOut.println("End of transmission");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void readFromServer() throws IOException {
		//System.out.println("Trying to read from server");
		File targetFile = new File(NetZipFileName);
		 
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter out = new BufferedWriter(new FileWriter(targetFile));
		
		String line = null;
		while((line=in.readLine())!= null){
			out.write(line);
			out.newLine();
		}

	    //System.out.println("read from server");
	    out.close();
	}

}


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
public class Client {
	  /* The server port to which 
	  the client socket is going to connect */
	  public final static int SERVICE_PORT = 50001;

	  public String serverIPAddress;
	  public static int serverPort = 0;
	  
	 public static void main(String[] args) throws IOException{
		 Client client = new Client();
		    try{
		      /* Instantiate client socket. 
		      No need to bind to a specific port */
		      DatagramSocket clientSocket = new DatagramSocket();
		      
		      // Get the IP address of the node from the user
		      InetAddress IPAddress = InetAddress.getByName("localhost");
		      
		      // Creating corresponding buffers
		      byte[] sendingDataBuffer = new byte[1024];
		      byte[] receivingDataBuffer = new byte[1024];
		      
		      /* Converting data to bytes and 
		      storing them in the sending buffer */
			  Scanner scanner = new Scanner(System.in);
			  String queryText = "Very wrong example text indeed";
			  String connectionText = "234.675.218.451:4000000 ";

			
			  do{

				System.out.println("Pls Enter a IP address and PortNumber for the server, like:\nA. <IPAddress>:<PortNumber>. Example: “123.456.678:9999");	 
				connectionText = scanner.nextLine();

			  } while(client.checkConnection(connectionText));

			  do{

 				System.out.println("Pls Enter a Query like:\nA. GET:<id>:<key>. Example: “GET:3487563:mykey”.\nor B. SET:<id>:<key>:<value>. Example: “SET:4564561:mykey:myvalue”.");	 
			  	queryText = scanner.nextLine();

			  } while(client.checkQuery(queryText));
			 
		      
		      sendingDataBuffer = queryText.getBytes();
		      
		      // Creating a packet 
		      DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,IPAddress, serverPort);
		      
		      // sending packet to the server
		      clientSocket.send(sendingPacket);
		      
		      // Get the server response .i.e. capitalized sentence
		      DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,receivingDataBuffer.length);
		      clientSocket.receive(receivingPacket);
		      
		      // Printing the received data
		      String receivedData = new String(receivingPacket.getData());
		      System.out.println("Sent from the server: "+receivedData);
		      
		      // Closing the socket connection with the server
		      clientSocket.close();
		    }
		    catch(SocketException e) {
		      e.printStackTrace();
		    }
		  }

	public void connectToNode() {
		
	}
	
	public void sendSetQuery(int id, String key, String value) {
		
	}
	
	public void sendGetQuery(int id, String key) {
		
	}

	public boolean checkQuery(String queryText){
		String[] queryParts = queryText.split(":");
		boolean flag = true;

		switch (queryParts[0]) {
			case "GET":
				if(queryParts.length==3){
					flag = false;
				}
				break;
		
			case "SET":
				if(queryParts.length==4){
					flag = false;
				}
				break;
		
			default:
			System.out.println("Not a real Query. Try again.");
				break;
		}

		return flag;
	}
	
	public boolean checkConnection(String connectionText){
		String[] ipAndPort = connectionText.split(":");
		boolean flag = true;
		if(ipAndPort.length==2){
			serverIPAddress = ipAndPort[0];
			serverPort = Integer.parseInt(ipAndPort[1]);
			flag = false;
		}
		else{System.out.println("Wrong IPAddress or PortNumber for the server");}
		

		return flag;
	}
}

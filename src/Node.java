
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node extends Thread{
	private String ipAddress;
	private String portNumber;
	private Node[] connectedNodes;
	public final static int SERVICE_PORT=50001;
	
	private Map<String, String> keyValueMap = new HashMap<String, String>();
	private ArrayList<Node> nodeList;
	
	
	public Node() {
		
	}
	
	public Node(String ipAddress, String portNumber) {
		super();
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}

	public void setNodes(Node[] nodes){
		connectedNodes=nodes;
	}

	
	public Map<String, String> getKeyValueMap() {
		return keyValueMap;
	}

	public void addKeyValue(String key, String value) {
		getKeyValueMap().put(key, value);
	}

	public void connectToNode() {
		
	}
	
	public String executeSetQuery(String key, String value) {	
		String result =  " Key " + key + " and value "+value + " set." ;
		addKeyValue(key, value);
		System.out.println("SERVER: ExecutedSetQuery");
		return result;
	}
	
	public String executeGetQuery(String key) {
		//checkCurrentNodeForKey
		String result = "nothin in it";
		if(getKeyValueMap().containsKey(key)){
			result = ( " Key " + key+ " holds value " + getKeyValueMap().get(key));
			System.out.println("SERVER: ExecutedGetQuery");
		}
		else{
			System.out.println("SERVER: Keyvalue not inside");
		}
		
		//connect to another node and check int that node
			//connect to other nodes or setup broadcast

		return result;
	}

	public String checkReceivedQuery(String queryText){
		String[] queryParts = queryText.split(":");
		String queryType = queryParts[0];
		String returnMessageText = queryParts[0]+" msg w/ id "+queryParts[1]+" rcvd. " ;

		switch (queryType) {
			case "GET":
				if(queryParts.length==3){
					System.out.println("SERVER: Server needs to execute GET with id:"+queryParts[1]);
					returnMessageText+=executeGetQuery(queryParts[2]);
				}
				break;
		
			case "SET":
				if(queryParts.length==4){
					System.out.println("SERVER: " + "SERVER: Server needs to execute SET with id:"+queryParts[1]);
					returnMessageText+=executeSetQuery(queryParts[2], queryParts[3]);
				}
				break;
		
			default:
				System.out.println("SERVER: Not a real Query. Try again.");
				returnMessageText+="didn't work";
				break;
		}
		return returnMessageText;
	}


	public void run(){
		Node node= new Node();
		//TEST
		node.addKeyValue("1", "69");
		System.out.println("SERVER: " + node.keyValueMap.get("1"));

	    try{
			// Instantiate a new DatagramSocket to receive responses from the client
			DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
			
			/* Create buffers to hold sending and receiving data.
			It temporarily stores data in case of communication delays */
			byte[] receivingDataBuffer = new byte[1024];
			byte[] sendingDataBuffer = new byte[1024];
			
			/* Instantiate a UDP packet to store the 
			client data using the buffer for receiving data*/
			DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
			System.out.println("SERVER: Waiting for a client to connect...");
			
			// Receive data from the client and store in inputPacket
			serverSocket.receive(inputPacket);
			
			// Printing out the client sent data
			String receivedData = new String(inputPacket.getData()).trim();
			System.out.println("SERVER: Sent from the client: "+receivedData);


			
			//work on receivedString
			sendingDataBuffer = node.checkReceivedQuery(receivedData).toUpperCase().getBytes();


			/* 
			* Convert client sent data string to upper case,
			* Convert it to bytes
			*  and store it in the corresponding buffer. */
			//sendingDataBuffer = receivedData.toUpperCase().getBytes();
			
			// Obtain client's IP address and the port
			InetAddress senderAddress = inputPacket.getAddress();
			int senderPort = inputPacket.getPort();
			
			// Create new UDP packet with data to send to the client
			DatagramPacket outputPacket = new DatagramPacket(
				sendingDataBuffer, sendingDataBuffer.length,
				senderAddress,senderPort
			);
			
			// Send the created packet to client
			serverSocket.send(outputPacket);
			// Close the socket connection
			serverSocket.close();
	    }
	    catch (SocketException e){
	      	e.printStackTrace();
	    } catch (IOException e) {
			e.printStackTrace();
		}
	  }

	
}

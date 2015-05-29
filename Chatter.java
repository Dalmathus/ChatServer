import java.net.*;
import java.util.*;
import java.io.*;

public class Chatter {
    
    //James Luxton 1190809
    
    //int PORT = 40080;
    //byte[] buf = new byte[1000];
    //InetAddress IP = InetAddress.getByName("localhost");
    //DatagramPacket dgp = new DatagramPacket(buf, buf.length);
    
    public static class ReceiveMess extends Thread{ 
 
DatagramSocket ds;	
   public ReceiveMess(DatagramSocket s){
   		ds = s;
   	} 
 	
 	 byte[] Rbuf = new byte[1000];
	 DatagramPacket Rdgp = new DatagramPacket(Rbuf, Rbuf.length); 
        
        public void run() {
            try{
                while (true){
                    for(Peer p : Peer.PeerList){
                        ds.receive(Rdgp);
                        String rcvd = new String(Rdgp.getData(), 0, Rdgp.getLength()) + ", from address: "
                        + Rdgp.getAddress() + ", port: " + Rdgp.getPort();
                        System.out.println(rcvd);
                    }
                }
            }
            catch(IOException e) {
                System.out.println(e);
            }
        }
    }
    
    public static class SendMess extends Thread{
   DatagramSocket ds;	
   public SendMess(DatagramSocket s){
   		ds = s;
   	}
	   	
   	
  	 int SPORT = 40080;
    byte[] Sbuf = new byte[1000];
	 DatagramPacket Sdgp = new DatagramPacket(Sbuf, Sbuf.length);
    	
        public void run() {
            try{
                while (true) {
                    BufferedReader consR = new BufferedReader(new InputStreamReader(System.in));
                    String MessOut = consR.readLine();
                    if(MessOut.startsWith("NEW")){
                        try{
                            String[] splitArray = MessOut.split(" ");
                            String newIP = (splitArray[1]);
                            Peer p = new Peer(newIP);
                            System.out.println(newIP + " added to the contacts list");
                            continue;
                        }
                        catch(Exception e){
                            System.out.println("Please format NEW IP address's as NEW XXX.XXX.XXX.XXX");
                            continue;
                        }
                    }
                    else{
                        Sbuf = ("Server Said: " + MessOut).getBytes();
                        for(Peer p : Peer.PeerList){
                            DatagramPacket out = new DatagramPacket(Sbuf, Sbuf.length, p.IP, SPORT);
                        ds.send(out);}
                    }
                }
            }
            catch(IOException e) {
                System.out.println(e);
            }
        }
    }
    
    public static void main(String [] args){
      
      try{  
        for(String s : args){
            String address = s;
            Peer peer = new Peer(address);
        }
        
      int PORT = 40080;
      DatagramSocket ds = new DatagramSocket(PORT);
        
      Peer.PrintList();    
 
		SendMess sendmess = new SendMess(ds);
		sendmess.start();        
		ReceiveMess receivemess = new ReceiveMess(ds);
		receivemess.start();  
    }
    catch(Exception e){
    	System.out.println(e);
    }
    }
}


import java.net.*;
import java.util.*;

public class Peer{
	
	//James Luxton 1190809
	
	InetAddress IP;
	static List<Peer> PeerList = new LinkedList<Peer>();
	
	Peer(String clientAddress){	
		try{
			IP = IP.getByName(clientAddress);
			AddToList(this);
		}
		catch(UnknownHostException e){
			System.out.println(e.toString());
		}
	}	
	
	public void AddToList(Peer peer){
		PeerList.add(this);
	}
	
	public List<Peer> GetList(){
        return PeerList;
    }
	
	public static void PrintList(){
		for(Peer p : PeerList){
		System.out.println(p.IP.toString());
		}	
	}
}
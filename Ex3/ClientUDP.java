package Ex3;

import java.net.*;
public class ClientUDP {
public static void main(String argv[]) {


try {

InetAddress adr;
DatagramPacket packet;
DatagramSocket socket;

adr = InetAddress.getByName("localhost");

byte[] data = (new String("Hello Word")).getBytes();

packet = new DatagramPacket(data, data.length, adr, 1250);

socket = new DatagramSocket();

socket.send(packet);
// 19 est le nombre minimum d'octets nécessaire pour la date et l'heure complètes. 
byte[] reponse = new byte[19];
packet.setData(reponse);
packet.setLength(reponse.length);

socket.receive(packet);

String chaine = new String(packet.getData(), 0,packet.getLength());

System.out.println(" reçu du serveur : " + chaine);
socket.close();
} catch (Exception e) {
System.err.println("Erreur : " + e);
}
}
}

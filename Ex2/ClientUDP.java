package Ex2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
public class ClientUDP {
public static void main(String argv[]) {
int port = 0;
String host = "";
Scanner keyb = new Scanner(System.in);
try {

System.out.print("Adresse du serveur : ");
host = keyb.next();
System.out.print("port d'écoute du serveur : ");
port = keyb.nextInt();


InetAddress adr = InetAddress.getByName(host);
// données à envoyer : une voiture
voiture voit=new voiture("Nissan","Qashqai");
  // Sérialiser l'objet voiture en un tableau d'octets.
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(voit);
            objectStream.flush();
            byte[] data = byteStream.toByteArray();

DatagramPacket packet = new DatagramPacket(data, data.length, adr, port);

 DatagramSocket socket = new DatagramSocket();

socket.send(packet);

// Le saisie de la quantité du carburant à ajouter.
int c=0;
System.out.print("Quantité du carburant à ajouter : ");
c=keyb.nextInt();
data = ByteBuffer.allocate(4).putInt(c).array();
packet.setData(data);
packet.setLength(data.length);
socket.send(packet);



data = new byte[1024];

packet.setData(data);
packet.setLength(data.length);
socket.receive(packet);

 // Désérialiser les données reçues en un objet voiture.
            ByteArrayInputStream byteStream2 = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectStream2 = new ObjectInputStream(byteStream2);
            voit = (voiture) objectStream2.readObject();
            System.out.println("Carburant: "+voit.getCarburant());


data = new byte[1024];
packet.setData(data);
packet.setLength(data.length);
socket.receive(packet);

String chaine = new String(packet.getData(), 0,packet.getLength());

System.out.println(" reçu du serveur : " + chaine);
socket.close();
keyb.close();
} catch (Exception e) {
System.err.println("Erreur : " + e);
}
}
}



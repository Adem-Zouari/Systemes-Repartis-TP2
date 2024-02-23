package Ex2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
public class ServeurUDP {
public static void main(String argv[]) {
int port = 0;
Scanner keyb = new Scanner(System.in);
try {

System.out.print("port d'écoute : ");
port = keyb.nextInt();
DatagramPacket packet;

DatagramSocket socket = new DatagramSocket(port);

byte[] data = new byte[1024];

packet = new DatagramPacket(data, data.length);

socket.receive(packet);

 // Désérialiser les données reçues en un objet voiture.
            ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            voiture voit = (voiture) objectStream.readObject();

 // Extraire l'entier des octets reçus.
            data = new byte[1024];
            packet.setData(data);
            packet.setLength(data.length);
            socket.receive(packet);
            int c = ByteBuffer.wrap(data).getInt();


        System.out.println(" ca vient de : " + packet.getAddress().getHostAddress() + ":" +packet.getPort());

// Remplissage du carburant

voit.setCarburant(c);

  // Sérialiser l'objet voiture en un tableau d'octets.
            ByteArrayOutputStream byteStream2 = new ByteArrayOutputStream();
            ObjectOutputStream objectStream2 = new ObjectOutputStream(byteStream2);
            objectStream2.writeObject(voit);
            objectStream2.flush();
            data = byteStream2.toByteArray();


packet.setData(data);
packet.setLength(data.length);
socket.send(packet);

data = (new String("bien recu")).getBytes();
packet.setData(data);
packet.setLength(data.length);

socket.send(packet);
socket.close();
keyb.close();
} catch (Exception e) {
System.err.println("Erreur : " + e);
}
}
}


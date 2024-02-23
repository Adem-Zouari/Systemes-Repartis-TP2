package Ex3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ServeurUDP {
public static void main(String argv[]) {

    DatagramSocket socket=null;
    try {
        socket = new DatagramSocket(1250);
        while(true) {
            DatagramPacket packet;
            // Seulement 1 octet car le paquet que le serveur a reçu n'a pas d'importance.
            byte[] data = new byte[1];
            packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            System.out.println("Nouvelle connexion : " + packet.getAddress().getHostAddress() + ":" + packet.getPort());

            // Date courante
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);

            byte[] response = formattedDateTime.getBytes();
            packet.setData(response);
            packet.setLength(response.length);
            socket.send(packet);
        }
    } catch (Exception e) {
        System.err.println("Erreur : " + e);
    } finally {
            socket.close();
    }
}
}
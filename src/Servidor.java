import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.util.logging.Level;
import java.io.IOException;
import java.util.logging.Logger;

public class Servidor {
public static void main(String[] args) {
    
    final int PUERTO = 8080;
    byte [] buffer = new byte[1024];
    try {
        System.out.println("Servidor UDP iniciado...");
        DatagramSocket socket = new DatagramSocket(PUERTO);
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquete);
        System.out.println("recibo informacion de cliente");
        String mensaje = new String(paquete.getData());
        System.out.println("Mensaje recibido: " + mensaje);
        int puertoCliente = paquete.getPort();//verificar el puerto del cliente
        Inet4Address ipCliente=(Inet4Address) paquete.getAddress();//verificar la ip del cliente
        //creacion paeute de respuesta
        String mensRespuesta = "Hola cliente, soy el servidor";
        buffer = mensRespuesta.getBytes();
        DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, ipCliente, puertoCliente);//envio la respuesta al cliente
        System.out.println("Enviando respuesta al cliente...");
        socket.send(respuesta);
    }catch(SocketException e){
    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
}catch(IOException e){
    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE,null,e);

}}
}
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Cliente {
    public static void main(String[] args) {

        final int PUERTO_SERVIDOR = 8080;
        byte [] buffer = new byte[1024];
        try {
            InetAddress direccionServidor =InetAddress.getByName("localhost");
            DatagramSocket socket = new DatagramSocket();
            String mensaje="Enviando mensaje al servidor...desde el cliente";
            buffer = mensaje.getBytes();
             DatagramPacket respuesta_Servidor = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
             System.out.println("Enviando mensaje al servidor...");
             socket.send(respuesta_Servidor);
             DatagramPacket peticion= new DatagramPacket(buffer, buffer.length);
             System.out.println("Esperando respuesta del servidor...");
                socket.receive(peticion);
                mensaje=new String(peticion.getData());
                System.out.println("Mensaje recibido del servidor: "+mensaje);
                socket.close();      
        
        } catch (SocketException e) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
        }catch(UnknownError e){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
        }catch(IOException e){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}

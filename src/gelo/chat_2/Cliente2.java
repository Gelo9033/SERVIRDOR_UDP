package gelo.chat_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Cliente2 {
    private static final String SERVER_IP = "localhost"; // Cambia esto a la IP del servidor
    private static final int PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private String username;

    public Cliente2() {
        frame = new JFrame("Chat Client");
        textArea = new JTextArea(20, 50);
        textField = new JTextField(50);
        textArea.setEditable(false);

        // Configurar la interfaz gráfica
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    public void connect() {
        try {
            socket = new Socket(SERVER_IP, PORT);
            System.out.println("Conectado al servidor.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Solicitar nombre de usuario
            String user = JOptionPane.showInputDialog(frame, "Introduce tu nombre de usuario:");
            if (user != null && !user.trim().isEmpty()) {
                username = user;
                out.println(username);
                textArea.append("¡Bienvenido, " + username + "!\n");
            }

            // Hilo para leer mensajes del servidor
            new Thread(new ReadMessages()).start();
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor: " + e.getMessage());
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        if (message != null && !message.trim().isEmpty()) {
            out.println(message);
            textField.setText("");
        }
    }

    private class ReadMessages implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    textArea.append(message + "\n");
                }
            } catch (IOException e) {
                System.err.println("Error en la lectura del mensaje: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Cliente2 client = new Cliente2();
        client.connect();
    }
}

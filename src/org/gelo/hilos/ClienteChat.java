package org.gelo.hilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClienteChat extends JFrame {
    private static final String SERVER_ADDRESS = "localhost"; // Cambia esto a la IP del servidor si es necesario
    private static final int SERVER_PORT = 5000;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entradaServidor;

    private JTextArea areaTexto;
    private JTextField campoMensaje;
    private JTextField campoClave;
    private JTextField campoNombre;

    public ClienteChat() {
        setTitle("Cliente de Chat");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(3, 1));

        campoClave = new JTextField("Ingrese la clave de acceso");
        campoNombre = new JTextField("Ingrese su nombre de usuario");
        campoMensaje = new JTextField("Ingrese su mensaje");

        panelEntrada.add(campoClave);
        panelEntrada.add(campoNombre);
        panelEntrada.add(campoMensaje);
        add(panelEntrada, BorderLayout.SOUTH);

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviarMensaje();
                } catch (IOException ex) {

                }
            }
        });
        add(botonEnviar, BorderLayout.NORTH);

        setVisible(true);
    }

    private void conectar() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            leerMensajes();
        } catch (IOException e) {
            areaTexto.append("Error de conexión: " + e.getMessage() + "\n");
        }
    }

    private void leerMensajes() {
        new Thread(() -> {
            String mensajeServidor;
            try {
                while ((mensajeServidor = entradaServidor.readLine()) != null) {
                    areaTexto.append(mensajeServidor + "\n");
                }
            } catch (IOException e) {
                areaTexto.append("Error al recibir mensajes del servidor: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private void enviarMensaje() throws IOException {
        String clave = campoClave.getText();
        String nombre = campoNombre.getText();
        String mensaje = campoMensaje.getText();

        if (salida == null) {
            // Solicitar acceso
            salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println("SOLICITUD_ACCESO");
            String respuesta = entradaServidor.readLine();
            areaTexto.append(respuesta + "\n");

            // Verificar clave
            salida.println(clave);
            respuesta = entradaServidor.readLine();
            areaTexto.append(respuesta + "\n");

            // Verificar nombre
            salida.println(nombre);
            respuesta = entradaServidor.readLine();
            areaTexto.append(respuesta + "\n");

            if (respuesta.contains("Bienvenido")) {
                campoClave.setEnabled(false);
                campoNombre.setEnabled(false);
            }
        } else {
            salida.println(mensaje);
            campoMensaje.setText(""); // Limpiar campo de mensaje
        }
    }

    public static void main(String[] args) {
        ClienteChat clienteChat = new ClienteChat();
        clienteChat.conectar();
    }
}

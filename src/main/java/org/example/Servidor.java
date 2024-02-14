package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    int port;

    public Servidor(int port) {
        this.port = port;
    }

    public void listen() {

        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Servidor escoltant al port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client conectat");
                ThreadServidorAdivina threadServidor = new ThreadServidorAdivina(clientSocket);
                Thread clientThread = new Thread(threadServidor);
                clientThread.start();

            }
        } catch (IOException ex) {

            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public static void main(String[] args) {

        Servidor srv = new Servidor(5558);
        srv.listen();

    }
}
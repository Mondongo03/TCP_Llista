package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    String hostname;
    int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {

        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            Llista lista = createList();
            out.writeObject(lista);
            out.flush();
            System.out.println("Llista enviada al servidor" + lista.getNom() + " " + lista.getNumberList());


            Llista modifiedList = (Llista) in.readObject();
            System.out.println("Llista endreçada rebuda: " + modifiedList.getNom() + " " + modifiedList.getNumberList());

        } catch (UnknownHostException ex) {

            System.out.println("Error de conexió no n'hi ha host: " + ex.getMessage());

        } catch (IOException | ClassNotFoundException ex) {

            System.out.println("Error de conexión: " + ex.getMessage());

        } finally {

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private Llista createList() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introdueix el nom de la lista: ");
        String nombre = scanner.nextLine();
        List<Integer> numeros = new ArrayList<>();
        System.out.println("Introdueix els numeros (amb un numero negatiu completas la llista): ");
        int num;

        while ((num = scanner.nextInt()) >= 0) {

            numeros.add(num);

        }

        return new Llista(nombre, numeros);
    }

    public static void main(String[] args) {

        Client clientTcp = new Client("localhost", 5558);
        clientTcp.run();

    }
}

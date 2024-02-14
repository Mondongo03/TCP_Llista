package org.example;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class ThreadServidorAdivina implements Runnable {
    private Socket clientSocket;

    public ThreadServidorAdivina(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());  ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {

            Llista llista = (Llista) inputStream.readObject();
            System.out.println("Llista rebuda del client: " + llista.getNom() + " " + llista.getNumberList());
            List<Integer> llistaNumeros = llista.getNumberList();
            Collections.sort(llistaNumeros);
            LinkedHashSet<Integer> uniqueNumbers = new LinkedHashSet<>(llistaNumeros);
            llistaNumeros = new ArrayList<>(uniqueNumbers);

            llista.setNumberList(llistaNumeros);

            outputStream.writeObject(llista);
            outputStream.flush();
            System.out.println(llista.getNom() + " " + llista.getNumberList());

        } catch (IOException | ClassNotFoundException ex) {

            Logger.getLogger(ThreadServidorAdivina.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidorAdivina.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
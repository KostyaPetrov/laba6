package client;


import collection.Product;
import collection.ProductClient;
import exeption.CommandExeption;
import exeption.TimeOutExeption;
import manager.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Client {
    String host;
    Integer port;
    ConsoleManager consoleManager;
    InetAddress ipAddress;
    DatagramChannel clientChannel;
    Serializer serializer;
    SocketAddress socketAddress;
    CommandManager commandManager;
    FileManager fileManager;

    public Client(String host, Integer port, ConsoleManager consoleManager) {
        this.host = host;
        this.port = port;
        this.consoleManager = consoleManager;
        this.serializer = new Serializer();
        this.fileManager = new FileManager(consoleManager);


        try {
            this.clientChannel = DatagramChannel.open();
            this.ipAddress = InetAddress.getByName(host);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void start(CommandManager commandManager) {
        Collection<String> collection = Arrays.asList("help", "info", "show", "head", "history", "count_greater_than_unit_of_measure", "print_field_descending_unit_of_measure", "exit");
        String command;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024000);
        this.commandManager = commandManager;
        while (consoleManager.getCheckerExit()) {
            command = consoleManager.getCommand();
            if (!command.equals("execute_script")){
                send(command);
            }else{
                commandManager.execCommand(command);
            }
            /**
             * If server return to client information
             */
            if (collection.contains(command)) {


                try {
                    clientChannel.socket().setSoTimeout(3000);
                    byte[] bytes = new byte[byteBuffer.limit()];
                    DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);
                    clientChannel.socket().receive(datagramPacket);
                    byteBuffer.put(datagramPacket.getData());
                    AnswerManager response = (AnswerManager) serializer.deserialize(byteBuffer.array());
                    System.out.println("Get from server:\n" + response);
                    byteBuffer.clear();
                    //clientChannel.receive(byteBuffer);
                }catch (SocketTimeoutException e) {System.out.println("Server error");
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                AnswerManager response = (AnswerManager) serializer.deserialize(byteBuffer.array());
//                System.out.println("Get from server:\n" + response);
//                byteBuffer.clear();
            }
        }


//        // Получите ответ от сервера, т.е. предложение из заглавных букв
//        DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,receivingDataBuffer.length);
//        try {
//            clientSocket.receive(receivingPacket);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Выведите на экране полученные данные
//        String receivedData = new String(receivingPacket.getData());
//        System.out.println("Sent from the server: "+receivedData);
//
//        // Закройте соединение с сервером через сокет
//        clientSocket.close();
    }

    public void send(String command) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ObjectManager stringObject = null;
        try {
            /**
             * Command type check (1 or 2 components)
             */
            if (!command.contains(" ")) {
                if (command.equals("add") || command.equals("add_if_max")) {
                    stringObject = new ObjectManager(command, consoleManager.getClientProduct());

                } else {
                    stringObject = new ObjectManager(command, null);
                }
            } else if (command.split(" ")[0].equals("update_id")) {
                stringObject = new ObjectManager(command, consoleManager.getClientProduct());
            } else if (command.split(" ")[0].equals("execute_script")) {
                commandManager.execCommand("execute_script");

            } else {
                stringObject = new ObjectManager(command, null);
            }

            socketAddress = new InetSocketAddress(ipAddress, port);
            byte[] serializeObject = serializer.serialize(stringObject);
            byteBuffer = ByteBuffer.wrap(serializeObject);
            clientChannel.send(byteBuffer, socketAddress);
            System.out.println("Data send");
            byteBuffer.clear();


        } catch (CommandExeption e) {
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String command, ProductClient product) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ObjectManager stringObject=null;
        try {
            /**
             * Command type check (1 or 2 components)
             */
            if (!command.contains(" ")) {
                if (command.equals("add") || command.equals("add_if_max")) {
                    stringObject = new ObjectManager(command, product);

                } else {
                    stringObject = new ObjectManager(command, null);
                }
            } else if (command.split(" ")[0].equals("update")) {
                stringObject = new ObjectManager(command, product);
            } else {
                stringObject = new ObjectManager(command, null);
            }

            socketAddress = new InetSocketAddress(ipAddress, port);
            byte[] serializeObject = serializer.serialize(stringObject);
            byteBuffer = ByteBuffer.wrap(serializeObject);
            clientChannel.send(byteBuffer, socketAddress);
            System.out.println("Data send");
            byteBuffer.clear();

//            socketAddress=clientChannel.receive(byteBuffer);
//            byteBuffer.flip();
//
//            String response=serializer.deserialize(byteBuffer);
//            System.out.println("Get data:"+response);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

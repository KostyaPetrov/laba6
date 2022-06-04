package server;


import command.Exit;
import manager.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Server {
    public final static int SERVICE_PORT = 60001;
    ConsoleManager consoleManager;
    ServerCollectionManager collectionManager;
    FileManager fileManager;
    CommandManager commandManager;

    CommandHandler commandHandler;
    Serializer serializer;
    DatagramChannel server;
    InetSocketAddress inetSocketAddress;
    ObjectManager objectManagerFromClient;
    SocketAddress remoteAddress;

    public Server() {

    }

    public void start(ConsoleManager consoleManager, ServerCollectionManager collectionManager, FileManager fileManager, Serializer serializer, CommandHandler commandHandler, CommandManager commandManager) throws IOException {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.serializer = serializer;
        this.commandHandler = commandHandler;
        this.commandManager = commandManager;
        Collection<String> collection = Arrays.asList("help", "info", "show", "head", "history", "count_greater_than_unit_of_measure", "print_field_descending_unit_of_measure");
        fileManager.fileRead();
        this.server = DatagramChannel.open();
        this.inetSocketAddress = new InetSocketAddress(SERVICE_PORT);
        server.bind(inetSocketAddress);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);


        boolean checkerExit = false;
        System.out.println("Waiting for a client to connect...");
        while (!checkerExit) {
            try {
                server.socket().setSoTimeout(1000);
                byte[] bytes = new byte[byteBuffer.limit()];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                server.socket().receive(datagramPacket);
                byteBuffer.put(datagramPacket.getData());
                this.remoteAddress = datagramPacket.getSocketAddress();
                ObjectManager objectManager = (ObjectManager) serializer.deserialize(byteBuffer.array());

                System.out.println("Get data " + objectManager);
                System.out.println("Command " + objectManager.getCommand());
                System.out.println("Product " + objectManager.getProduct());


                String inputCommand = objectManager.getCommand();


//            String endCommand = consoleManager.getEndScriptCommand();
//            if (endCommand.equals("exit") || inputCommand.equals("exit")) {
//                send(commandManager.execCommand(commandHandler.unpacker(objectManager)));
//                server.close();
//                checkerExit = true;

                //    } else {
                try {
                    if (collection.contains(inputCommand)) {
                        send(commandManager.execCommand(commandHandler.unpacker(objectManager)));
                    } else {
                        commandManager.execCommand(commandHandler.unpacker(objectManager));
                    }
                } catch (NullPointerException ignored) {
                }
                //}
                byteBuffer.clear();
            }catch (SocketTimeoutException e) {
                if (System.in.available()>0) {
                    Scanner scanner = new Scanner(System.in);
                    String str=scanner.next();
                    if (str.equals("exit")) checkerExit=true;
//                    if (str.equals("save")) new Exit(fileManager,collectionManager).execute("");
                }
            }




        }

    }

    public void setObjectManager(ObjectManager objectManagerFromClient) {
        this.objectManagerFromClient = objectManagerFromClient;
    }

    public ObjectManager getObjectManager() {
        return objectManagerFromClient;
    }

    public void send(String stringObject) throws IOException {
//        byteBuffer.flip();
        AnswerManager answerObject = new AnswerManager(stringObject);
//       System.out.println("Ansver object: " + answerObject);
        byte[] serializeObject = serializer.serialize(answerObject);
        ByteBuffer byteBuffer = ByteBuffer.wrap(serializeObject);


        server.send(byteBuffer, remoteAddress);
        System.out.println("Data send to client");

    }
}

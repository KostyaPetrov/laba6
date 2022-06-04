package server;


import manager.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Collection;

public class Server {
    public final static int SERVICE_PORT = 50001;
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
        ByteBuffer byteBuffer = ByteBuffer.allocate(102400000);


        boolean checkerExit = false;
        System.out.println("Waiting for a client to connect...");
        while (!checkerExit) {

            this.remoteAddress = server.receive(byteBuffer);
            ObjectManager objectManager = (ObjectManager) serializer.deserialize(byteBuffer.array());

            System.out.println("Get data " + objectManager);
            System.out.println("Command " + objectManager.getCommand());
            System.out.println("Product " + objectManager.getProduct());


            String inputCommand = objectManager.getCommand();


            String endCommand = consoleManager.getEndScriptCommand();
            if (endCommand.equals("exit") || inputCommand.equals("exit")) {
                send(commandManager.execCommand(commandHandler.unpacker(objectManager)));
                server.close();
                checkerExit = true;

            } else {

                if (collection.contains(inputCommand)) {
                    send(commandManager.execCommand(commandHandler.unpacker(objectManager)));
                }else{
                    commandManager.execCommand(commandHandler.unpacker(objectManager));
                }
            }
            byteBuffer.clear();

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
        System.out.println("Ansver object: " + answerObject);
        byte[] serializeObject = serializer.serialize(answerObject);
        ByteBuffer byteBuffer = ByteBuffer.wrap(serializeObject);


        server.send(byteBuffer, remoteAddress);
        System.out.println("Data send to client");

    }
}

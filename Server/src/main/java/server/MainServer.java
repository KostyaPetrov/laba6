package server;

import manager.*;

import java.io.IOException;

public class MainServer {




    public static void main(String[] args) throws IOException {
        Serializer serializer = new Serializer();
        ConsoleManager consoleManager = new ConsoleManager();
        ServerCollectionManager collectionManager = new ServerCollectionManager();
        FileManager fileManager = new FileManager(consoleManager, collectionManager);
        Server server=new Server();
        CommandHandler commandHandler=new CommandHandler(collectionManager, consoleManager);
        CommandManager commandManager = new CommandManager(collectionManager, consoleManager, fileManager, serializer, commandHandler);

        consoleManager.getServerObject(collectionManager, consoleManager, fileManager, commandManager);




//        commandManager.getterObjects(commandHandler, server);
        server.start(consoleManager, collectionManager,fileManager,serializer, commandHandler, commandManager);

        // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
//        try {
//            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
//             /* Создайте буферы для хранения отправляемых и получаемых данных.
//Они временно хранят данные в случае задержек связи */
//            byte[] receivingDataBuffer = new byte[1024];
//            byte[] sendingDataBuffer = new byte[1024];
//
//            /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
//            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
//            System.out.println("Waiting for a client to connect...");
//
//            // Получите данные от клиента и сохраните их в inputPacket
//            serverSocket.receive(inputPacket);
//            System.out.println("Data from client got");
//
//            // Выведите на экран отправленные клиентом данные
//            Serializer serializer=new Serializer();
//
//            String receivedData = new String(inputPacket.getData());
//
//            System.out.println("Sent from the client: "+receivedData);
//
//
//            /*
//             * Преобразуйте отправленные клиентом данные в верхний регистр,
//             * Преобразуйте их в байты
//             * и сохраните в соответствующий буфер. */
//            sendingDataBuffer = receivedData.toUpperCase().getBytes();
//            String inf= serializer.deserialize(receivedData.getBytes());
//            System.out.println("Sent from the client: "+inf);
//            // Получите IP-адрес и порт клиента
//            InetAddress senderAddress = inputPacket.getAddress();
//            int senderPort = inputPacket.getPort();
//
//            // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
//            DatagramPacket outputPacket = new DatagramPacket(
//                    sendingDataBuffer, sendingDataBuffer.length,
//                    senderAddress,senderPort
//            );
//
//            // Отправьте пакет клиенту
//            serverSocket.send(outputPacket);
//            System.out.println("Data send:"+outputPacket);
//            // Закройте соединение сокетов
//            serverSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }





//    public Serializable deserialize(byte[] data) {
//
//        try {
//            ByteArrayInputStream in = new ByteArrayInputStream(data);
//            ObjectInputStream is = new ObjectInputStream(in);
//            Serializable request = (Serializable) is.readObject();
//            in.close();
//            is.close();
//            return request;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
}

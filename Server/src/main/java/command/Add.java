package command;


import collection.Product;
import manager.ServerCollectionManager;
import manager.ConsoleManager;
import manager.CommandHandler;

import java.util.LinkedList;

public class Add implements Commandable {
    private ConsoleManager consoleManager;
    private ServerCollectionManager collectionManager;
    private CommandHandler commandHandler;
    public Add(ServerCollectionManager collectionManager, ConsoleManager consoleManager, CommandHandler commandHandler) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
        this.commandHandler=commandHandler;
    }

    @Override
    public String execute(String s) {


        LinkedList<Product> list = collectionManager.getCollection();
        Product product = commandHandler.getProduct();
        list.add(product);
        collectionManager.setCollection(list);
        return null;

    }

}
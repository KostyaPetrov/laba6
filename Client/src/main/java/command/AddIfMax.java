package command;


import collection.Product;
import manager.ConsoleManager;
import manager.ServerCollectionManager;
import manager.CommandHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


public class AddIfMax implements Commandable {
    private ConsoleManager consoleManager;
    private ServerCollectionManager collectionManager;
    private CommandHandler commandHandler;

    public AddIfMax(ConsoleManager consoleManager, ServerCollectionManager collectionManager, CommandHandler commandHandler) {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String s) {
        Product newProduct = commandHandler.getProduct();
        LinkedList<Product> collectionElements = collectionManager.getCollection();
        ArrayList<Integer> arrPrice = new ArrayList<>();
        for (Product collectionElement : collectionElements) {
            arrPrice.add(collectionElement.getPrice());
        }
        if (newProduct.getPrice() > Collections.max(arrPrice)) {
            collectionElements.add(newProduct);
            collectionManager.setCollection(collectionElements);
            System.out.println("Element added");
        } else {
            System.out.println("Price of your product is not highest. Product not added.");
        }
        return null;
    }
}

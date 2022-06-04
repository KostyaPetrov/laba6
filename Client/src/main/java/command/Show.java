package command;


import collection.Product;
import manager.ServerCollectionManager;

import java.io.IOException;
import java.util.LinkedList;

public class Show implements Commandable {
    private ServerCollectionManager collectionManager;
    private LinkedList<Product> collectionElements;

    public Show(ServerCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String s) {
        StringBuilder stringObject = new StringBuilder();
        collectionElements = collectionManager.getCollection();
        for (int i = 0; i < collectionElements.size(); i++) {
            stringObject.append(collectionElements.get(i)).append("\n");
        }


        return stringObject.toString();

    }

}


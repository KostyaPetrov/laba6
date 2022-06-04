package command;


import collection.Product;
import manager.ServerCollectionManager;
import manager.FileManager;


import java.io.IOException;
import java.util.LinkedList;

public class Info implements Commandable {
    private ServerCollectionManager collectionManager;
    private FileManager fileManager;

    private LinkedList<Product> collectionElements;

    public Info(ServerCollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public String execute(String s) {
        String stringObject = "";
        collectionElements = collectionManager.getCollection();
        if (collectionElements.isEmpty()) {
            stringObject = "Collection is empty";
        } else {

            stringObject = String.format("LinkedList, %s, %s\n", fileManager.getInitCollectionDate(), collectionElements.size());
        }

        return (stringObject);

    }
}

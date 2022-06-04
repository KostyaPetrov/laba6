package command;


import collection.Product;
import collection.ProductClient;
import manager.CommandManager;
import manager.ServerCollectionManager;
import manager.ConsoleManager;
import manager.CommandHandler;


import java.io.IOException;
import java.util.LinkedList;

public class CountGreaterThanUnitMeasure implements Commandable {
    private ServerCollectionManager collectionManager;
    private CommandHandler commandHandler;
    public CountGreaterThanUnitMeasure(ServerCollectionManager collectionManager, CommandHandler commandHandler) {
        this.collectionManager = collectionManager;
        this.commandHandler=commandHandler;
    }

    @Override
    public String execute(String s) {

        LinkedList<Product> collectionElements = collectionManager.getCollection();
        String comparisonUnitOfMeasure = commandHandler.getСomparisonUnitOfMeasure();
        int count = 0;
        for (Product element : collectionElements) {
            if (comparisonUnitOfMeasure.compareTo(element.getUnitOfMeasure().getWord()) < 0) {
                count += 1;
            }
        }
        String stringObject="In collection " + count + " elements great than " + comparisonUnitOfMeasure;

        return stringObject;


    }
}

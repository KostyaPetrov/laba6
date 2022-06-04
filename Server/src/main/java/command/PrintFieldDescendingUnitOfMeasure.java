package command;


import collection.Product;
import manager.ServerCollectionManager;



import java.util.ArrayList;
import java.util.Collections;


public class PrintFieldDescendingUnitOfMeasure implements Commandable {
    private ServerCollectionManager collectionManager;

    public PrintFieldDescendingUnitOfMeasure(ServerCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String s) {
        String stringObject = "";
        ArrayList<String> arrayUnitOfMesure = new ArrayList<>();
        for (Product element : collectionManager.getCollection()) {
            arrayUnitOfMesure.add(element.getUnitOfMeasure().getWord());
        }
        Collections.sort(arrayUnitOfMesure, Collections.reverseOrder());

        for (int i = 0; i < arrayUnitOfMesure.size(); i++) {
            stringObject = arrayUnitOfMesure.get(i);
        }

        return stringObject;

    }
}

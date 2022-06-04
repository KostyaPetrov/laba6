package manager;

import collection.Coordinates;
import collection.Person;
import collection.Product;
import collection.ProductClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    ServerCollectionManager collectionManager;
    ConsoleManager consoleManager;
    ProductClient productClient;

    String[] arrCommandLine;
    Map<String, String> map = new HashMap<>();
    public CommandHandler(ServerCollectionManager collectionManager,  ConsoleManager consoleManager){
        this.collectionManager=collectionManager;
        this.consoleManager=consoleManager;
    }
    public String unpacker(ObjectManager objectManager){
        String inputCommand = objectManager.getCommand();
        productClient=objectManager.getProduct();

        if (!inputCommand.contains(" ")) {

            collectionManager.setCommand(inputCommand);
            return inputCommand;

        } else {
            arrCommandLine = inputCommand.split(" ");

            /*
             *put data from command line to map for pass to a method
             */
            map.put(arrCommandLine[0], arrCommandLine[1]);
            collectionManager.setCommand(arrCommandLine[0]);
            return arrCommandLine[0];
        }
    }

    ArrayList<Integer> listUniqueId = new ArrayList<>();
    public Integer getUniqueId() {

        if (!collectionManager.getUniqueId().isEmpty()) {
            listUniqueId.addAll(collectionManager.getUniqueId());
        }
        if (listUniqueId.isEmpty()) {
            listUniqueId.add(1);
            collectionManager.setUniqueId(listUniqueId);
            return 1;
        } else {
            listUniqueId.add(Collections.max(listUniqueId) + 1);
            collectionManager.setUniqueId(listUniqueId);
            return Collections.max(listUniqueId);
        }
    }

    public Product getProduct(){
        return new Product(getUniqueId(), productClient.getName(), new Coordinates(productClient.getCoordinates().getCoordinateX(), productClient.getCoordinates().getCoordinateY()),
                productClient.getCreationDate(), productClient.getPrice(), productClient.getPartNumber(),
                productClient.getManufactureCost(), productClient.getUnitOfMeasure(),
                new Person(productClient.getOwner().getNamePerson(), productClient.getOwner().getBirthday(), productClient.getOwner().getWeight()));

    }

    public Integer getUpdateId() {
        try {
            return Integer.valueOf(map.get("update"));
        } catch (NumberFormatException e) {
            System.err.println("Id for update must be integer number");
            consoleManager.setExeptionInfo(true);
            return null;
        }

    }


    public Integer getRemoveId() {
        try {
            return Integer.valueOf(map.get("remove_by_id"));
        } catch (NumberFormatException e) {
            System.err.println("Id for remove must be integer number");
            consoleManager.setExeptionInfo(true);
            return null;
        }

    }

    public Float getRemoveManufactureCost() {
        try {
            return Float.valueOf(map.get("remove_all_by_manufacture_cost"));
        } catch (NumberFormatException e) {
            System.err.println("Remove manufacture cost must be number");
            consoleManager.setExeptionInfo(true);
            return null;
        }

    }

    public String getСomparisonUnitOfMeasure() {
        return map.get("count_greater_than_unit_of_measure");
    }

}

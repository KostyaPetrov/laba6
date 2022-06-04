package command;



import collection.Coordinates;
import collection.Person;
import collection.Product;
import exeption.CommandExeption;
import exeption.FieldProductExeption;
import manager.ConsoleManager;
import manager.ServerCollectionManager;
import manager.CommandHandler;

import java.util.ArrayList;
import java.util.LinkedList;

public class UpdateId implements Commandable{
    private ServerCollectionManager collectionManager;
    private ConsoleManager consoleManager;
    private Integer updateId, indexElementCollection;
    private CommandHandler commandHandler;
    public UpdateId(ServerCollectionManager collectionManager, ConsoleManager consoleManager, CommandHandler commandHandler){
        this.collectionManager=collectionManager;
        this.consoleManager=consoleManager;
        this.commandHandler=commandHandler;
    }
    @Override
    public String execute(String s){
        ArrayList<Integer> collectionId = collectionManager.getUniqueId();
        LinkedList<Product> collectionElements = collectionManager.getCollection();

        updateId=commandHandler.getUpdateId();
        if(consoleManager.getExeptionInfo()){
            consoleManager.setExeptionInfo(false);
        }else {

            try {
                if(!collectionId.contains(updateId)){
                    throw new FieldProductExeption("This element collection does not exist");
                }
                //find position needed element collection if in collection id elements and replace element new product with old id and creation date, but new else field
                indexElementCollection = collectionId.indexOf(updateId);
                collectionElements.set(indexElementCollection, new Product(updateId, consoleManager.getName(), new Coordinates(consoleManager.getCoordinateX(), consoleManager.getCoordinateY()),
                        collectionElements.get(indexElementCollection).getCreationDate(), consoleManager.getPrice(), consoleManager.getPartNumber(), consoleManager.getManufactureCost(),
                        consoleManager.getUnitOfMeasure(), new Person(consoleManager.getNamePerson(), consoleManager.getBirthdayPerson(), consoleManager.getWeightPerson())));


                //write new colllection in collection storage
//                System.out.println(collectionElements);
                collectionManager.setCollection(collectionElements);

            }catch(CommandExeption e){
                System.err.println(e.getMessage());
            }
        }
        return null;

    }
}

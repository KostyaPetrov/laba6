package command;

import collection.Product;
import manager.ConsoleManager;
import manager.ServerCollectionManager;
import manager.CommandHandler;

import java.util.LinkedList;


public class RemoveWithManufactureCost implements Commandable{
    private ConsoleManager consoleManager;
    private ServerCollectionManager collectionManager;
    CommandHandler commandHandler;
    private Float removeCost;
    public RemoveWithManufactureCost(ConsoleManager consoleManager, ServerCollectionManager collectionManager, CommandHandler commandHandler){
        this.consoleManager=consoleManager;
        this.collectionManager=collectionManager;
        this.commandHandler=commandHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return  this.removeCost== that.getManufactureCost();
    }

    @Override
    public int hashCode() {
        return this.removeCost.hashCode();
    }

    @Override
    public String execute(String arg) {
        LinkedList<Product> collectionElements=collectionManager.getCollection();

        removeCost=commandHandler.getRemoveManufactureCost();
        if(consoleManager.getExeptionInfo()){
            consoleManager.setExeptionInfo(false);
        }else {

            for (int i = 0; i < collectionElements.size(); i++) {

                if (removeCost.equals(collectionElements.get(i).getManufactureCost())) {
                    collectionElements.remove(i);
                    //for the correct output of the final collection
                    i = i - 1;
                } else {
                    System.out.println(collectionElements.get(i));
                }
            }
            collectionManager.setCollection(collectionElements);
        }
        return null;
    }
}

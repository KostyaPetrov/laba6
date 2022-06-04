package command;



import collection.Product;
import exeption.CommandExeption;
import exeption.FieldProductExeption;
import manager.ConsoleManager;
import manager.ServerCollectionManager;
import manager.CommandHandler;

import java.util.ArrayList;
import java.util.LinkedList;


public class RemoveId implements Commandable {
    private ConsoleManager consoleManager;
    private ServerCollectionManager collectionManager;
    private Integer indexElementCollection;
    private LinkedList<Product> collectionElements;
    private ArrayList<Integer> collectionId;
    private CommandHandler commandHandler;
    public RemoveId(ConsoleManager consoleManager, ServerCollectionManager collectionManager,CommandHandler commandHandler){
        this.consoleManager=consoleManager;
        this.collectionManager=collectionManager;
        this.commandHandler=commandHandler;
    }
    @Override
    public String execute(String s){
        Integer removeId = commandHandler.getRemoveId();
        if(consoleManager.getExeptionInfo()){
            consoleManager.setExeptionInfo(false);
        }else {

            collectionElements = collectionManager.getCollection();
            try {
                collectionId = collectionManager.getUniqueId();
                //get position element in collection and remove themco
                if(!collectionId.contains(removeId)){
                    throw new FieldProductExeption("This element collection does not exist");
                }
                indexElementCollection = collectionId.indexOf(removeId);

 //               System.out.println(indexElementCollection);
                int i = indexElementCollection;
                collectionElements.remove(i);
                collectionId.remove(i);

                //write new colllection in collection storage
//                System.out.println(collectionElements);
                collectionManager.setCollection(collectionElements);
                collectionManager.setUniqueId(collectionId);
            }catch(CommandExeption e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

}

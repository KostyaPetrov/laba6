package manager;


import collection.Product;
import collection.ProductClient;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *class that work with collections
 */
public class ServerCollectionManager {

    ArrayList<Integer> listUniqueId;
    /**main collection with elements Product*/
    LinkedList<Product> collect;
    LinkedList<String> historyCommand=new LinkedList<>();

    public ServerCollectionManager(){}

    /**
     * getter product collection
     *
     * @return Collection elements
     */
    public LinkedList<Product> getCollection(){

        return collect;

    }

    /**
     * setter product collection
     *
     * @param collect new collection elements Product, witch we get for intermediate storage
     */
    public void setCollection(LinkedList<Product> collect){

        this.collect=collect;
    }

    /**
     * Getter collection with unique id
     *
     * @return collection with unique id all elements main collection
     */
    public ArrayList<Integer> getUniqueId(){
        return listUniqueId;
    }
    /**
     * setter collection with unique id
     *
     * @param listUniqueId new collection unique id, witch we get for intermediate storage
     */
    public void setUniqueId(ArrayList<Integer> listUniqueId){
        this.listUniqueId=listUniqueId;
    }

    /**
     * getter for collection command
     *
     * @return collection of 15 last command
     */
    public LinkedList<String> getHistoryCommand(){
        return historyCommand;
    }
    /**
     * setter for collection command
     *
     * @param command last command, witch was execute
     */
    public void setCommand(String command){

    //only the last 15 commands are stored, so there is a check
        if(historyCommand.size()<15){
            historyCommand.add(command);
        }else{
            historyCommand.removeFirst();
            historyCommand.add(command);
        }
    }



}
package command;


import manager.ServerCollectionManager;

import java.util.ArrayList;
import java.util.LinkedList;

public class Clear implements Commandable {

    private ServerCollectionManager collectionManager;


    public Clear(ServerCollectionManager collectionManager) {

        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String s) {

        collectionManager.setCollection(new LinkedList<>());
        collectionManager.setUniqueId(new ArrayList<>());
        return null;
    }
}

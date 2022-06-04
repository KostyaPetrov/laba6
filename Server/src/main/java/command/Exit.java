package command;


import manager.FileManager;
import manager.ServerCollectionManager;

public class Exit implements Commandable {
    private FileManager fileManager;
    private ServerCollectionManager collectionManager;
    public Exit(FileManager fileManager, ServerCollectionManager collectionManager){
        this.collectionManager=collectionManager;
        this.fileManager=fileManager;
    }
    @Override
    public String execute(String s){
        fileManager.fileWrite(collectionManager.getCollection());
        return s;
        //consoleManager.closeScan();

    }
}

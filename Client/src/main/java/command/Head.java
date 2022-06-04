package command;


import manager.ServerCollectionManager;




public class Head implements Commandable {
    private ServerCollectionManager collectionManager;

    public Head(ServerCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String s) {
        String stringObject = collectionManager.getCollection().getFirst().toString();
        return stringObject;


    }
}

package command;


import manager.ServerCollectionManager;



import java.util.LinkedList;

public class History implements Commandable {
    private ServerCollectionManager collectionManager;

    public History(ServerCollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String s) {
        LinkedList<String> collectCommand = collectionManager.getHistoryCommand();
        String stringObject = "";
        for (int i = 0; i < collectCommand.size(); i++) {
            stringObject += (i + 1) + "." + collectCommand.get(i) + "\n";
        }
        return stringObject;


    }


}

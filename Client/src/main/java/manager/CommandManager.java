package manager;


import client.Client;
import command.*;
import manager.Serializer;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    InetSocketAddress inetSocketAddress;
    protected Map<String, Commandable> map = new HashMap<>();

    public void addCommand(String key, Commandable cmd) {

        map.put(key, cmd);

    }

    public String execCommand(String key) {
        return(map.get(key).execute(""));
    }



    public CommandManager(ServerCollectionManager collectionManager, ConsoleManager consoleManager, FileManager fileManager, Serializer serializer, CommandHandler commandHandler) {

        addCommand("help", new Help(serializer));//вывести справку по доступным командам
        addCommand("info", new Info(collectionManager, fileManager));// вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
        addCommand("show", new Show(collectionManager));//вывести в стандартный поток вывода все элементы коллекции в строковом представлении
        addCommand("add", new Add(collectionManager, consoleManager,commandHandler));// добавить новый элемент в коллекцию
        addCommand("update", new UpdateId(collectionManager, consoleManager, commandHandler));//обновить значение элемента коллекции, id которого равен заданному
        addCommand("remove_by_id", new RemoveId(consoleManager, collectionManager, commandHandler));//     : удалить элемент из коллекции по его id
        addCommand("clear", new Clear(collectionManager));//     : очистить коллекцию

        addCommand("exit", new Exit(fileManager, collectionManager));// : завершить программу (без сохранения в файл)
        addCommand("head", new Head(collectionManager));//     : вывести первый элемент коллекции
        addCommand("add_if_max", new AddIfMax(consoleManager, collectionManager, commandHandler));//     : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
        addCommand("history", new History(collectionManager));//     : вывести последние 15 команд (без их аргументов)
        addCommand("remove_all_by_manufacture_cost", new RemoveWithManufactureCost(consoleManager, collectionManager, commandHandler));//     : удалить из коллекции все элементы, значение поля manufactureCost которого эквивалентно заданному
        addCommand("count_greater_than_unit_of_measure", new CountGreaterThanUnitMeasure(collectionManager, commandHandler));//     : вывести количество элементов, значение поля unitOfMeasure которых больше заданного
        addCommand("print_field_descending_unit_of_measure", new PrintFieldDescendingUnitOfMeasure(collectionManager));//    print_field_descending_unit_of_measure

        //addallcommands

    }

    public CommandManager(ConsoleManager consoleManager, FileManager fileManager, Client client) {
        addCommand("execute_script", new ExecuteScript(consoleManager, fileManager, client));//     : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
    }

    public Map getMap() {
        return map;
    }
}
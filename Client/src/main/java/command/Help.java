package command;



import manager.Serializer;




public class Help implements Commandable {
    Serializer serializer;


    public Help(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public String execute(String arg) {
        String stringObject = "\"help\" : вывести справку по доступным командам\n" +
                "\"info\" : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "\"show\" : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "\"add\" : добавить новый элемент в коллекцию\n" +
                "\"update\" id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "\"remove_by_id\" id : удалить элемент из коллекции по его id\n" +
                "\"clear\" : очистить коллекцию\n" +
                "\"save\" : сохранить коллекцию в файл\n" +
                "\"execute_script\" file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "\"exit\" : завершить программу (без сохранения в файл)\n" +
                "\"head\" : вывести первый элемент коллекции\n" +
                "\"add_if_max\" {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "\"history\" : вывести последние 15 команд (без их аргументов)\n" +
                "\"remove_all_by_manufacture_cost\" manufactureCost : удалить из коллекции все элементы, значение поля manufactureCost которого эквивалентно заданному\n" +
                "\"count_greater_than_unit_of_measure\" unitOfMeasure : вывести количество элементов, значение поля unitOfMeasure которых больше заданного\n" +
                "\"print_field_descending_unit_of_measure\" : вывести значения поля unitOfMeasure всех элементов в порядке убывания";
        return stringObject;


    }

}

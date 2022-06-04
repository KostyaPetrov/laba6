package manager;


import collection.Coordinates;
import collection.Person;
import collection.Product;
import collection.UnitOfMeasure;
import exeption.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;

public class FileManager {
    private LocalDate initCollectionDate;
    ArrayList<Integer> uniqueId = new ArrayList<>();

    private ConsoleManager consoleManager;
    private ServerCollectionManager collectionManager;

    public FileManager(ConsoleManager consoleManager, ServerCollectionManager collectionManager) {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }
    public FileManager(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;

    }

    //Writing data to file
    public void fileWrite(LinkedList<Product> collection) {
        String path = "C:\\Users\\Костя\\Desktop\\ITMO\\ITMO_2sem\\proga\\Lab_5\\src\\main\\java\\ru\\kostyapetrov\\lab_5\\file\\collection.csv";
//        String path = "collection.csv";
        if (!path.equals("Exit")) {


            String headline = "id, name, coordinate X, coordinate Y, creation Date, price, part number, manufacture cost, unit of measure, owner name, owner birthday, owner weight\n";
            StringBuilder products = new StringBuilder(headline);

            for (Product product : collection) {
                System.out.println(product.toString());
                products.append(product).append("\n");
            }


            //  ConsoleManager consoleManager = new ConsoleManager();
            OutputStream outputStream = null;
            try {
                File file = new File(path);
                if (file.isDirectory()) {
                    throw new PathIsDirectoryExeption("The specified path is a directory");
                } else if (!file.isFile()) {
                    throw new MissingFileExeption("No such file exists");
                } else if (!Files.isWritable(Paths.get(path))) {
                    throw new LackOfPermissionExeption("Not enough permissions to write to the file");
                }
                outputStream = new FileOutputStream(path);


                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);


                outputStreamWriter.write(products.toString());
                outputStreamWriter.close();

            } catch (FileExeption | FileNotFoundException e) {
                System.err.println(e.getMessage());
                fileWrite(collection);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //Reading data from file
    public void fileRead() {
        String path="C:\\Users\\Костя\\Desktop\\ITMO\\ITMO_2sem\\proga\\Lab_5\\src\\main\\java\\ru\\kostyapetrov\\lab_5\\file\\collection.csv";

        StringBuilder fileData = new StringBuilder();
        FileInputStream fileInputStream;
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                throw new PathIsDirectoryExeption("The specified path is a directory");
            } else if (!file.isFile()) {
                throw new MissingFileExeption("No such file exists");
            } else if (!Files.isReadable(Paths.get(path))) {
                throw new LackOfPermissionExeption("Not enough permissions to read from the file");
            }

            fileInputStream = new FileInputStream(path);

            //reading text from a file in bloks of 200 bytes
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);

            int i;


            while ((i = bufferedInputStream.read()) != -1) {
                fileData.append((char) i);

            }


            LinkedList<Product> fileDataCollection = new LinkedList<>();

            //fill array with elements from file
            String[] arrElements;
            String delimeter = "\n";
            arrElements = fileData.toString().split(delimeter);

            if (fileData.toString().equals("") || arrElements.length == 1) {

                collectionManager.setCollection(fileDataCollection);
            } else if (fileData.indexOf(",") != -1) {

                for (int j = 1; j < arrElements.length; j++) {

                    //split elements on product components
                    String[] components = arrElements[j].split(",");
                    //Check for the number of elements
                    if (components.length != 12) {
                        throw new NotEnoughProductFieldsExeption("In product " + j + " not all components are present. Add an element or specify another file");
                    } else {

                        //Add new element in collection and write ID in array

                        try {
                            fileDataCollection.add(new Product(Integer.valueOf(components[0].trim()), components[1],
                                    new Coordinates(Long.parseLong(components[2].trim()), Float.parseFloat(components[3].trim())),
                                    LocalDate.parse(components[4].trim()), Integer.parseInt(components[5].trim()), components[6],
                                    Float.valueOf(components[7].trim()), UnitOfMeasure.valueOf(components[8].trim()), new Person(components[9],
                                    LocalDateTime.parse(components[10].trim()), Double.valueOf(components[11].trim()))));
                            uniqueId.add(Integer.valueOf(components[0].trim()));
                            initCollectionDate = consoleManager.getDate();
                        } catch (NumberFormatException e) {
                            System.err.println("Incorrect numeric data in file");

                        } catch (DateTimeParseException e) {
                            System.err.println("Incorrect creation date format in file");

                        } catch (IllegalArgumentException e) {
                            System.err.println("Incorrect unit of measure in file");

                        }

                    }
                }


                collectionManager.setUniqueId(uniqueId);
                collectionManager.setCollection(fileDataCollection);

            } else {
                throw new NotEnoughProductFieldsExeption("Invalid file data format. Enter other path to file");
            }


        } catch (CommandExeption | IOException e) {
            System.err.println(e.getMessage());

        }


    }

    //time initialization collection
    public LocalDate getInitCollectionDate() {
        return initCollectionDate;
    }

    //Reading script program from file and return script from file as list
    public String[] getFileScript() {
        StringBuilder fileData = new StringBuilder();
        String path = consoleManager.getPathScriptFile();

        File file = new File(path);
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        try {

            if (file.isDirectory()) {
                throw new PathIsDirectoryExeption("The specified path is a directory");
            } else if (!file.isFile()) {
                throw new MissingFileExeption("No such file exists");
            } else if (!Files.isReadable(Paths.get(path))) {
                throw new LackOfPermissionExeption("Not enough permissions to read from the file");
            }

            fileInputStream = new FileInputStream(path);

            //reading script from a file in bloks of 200 bytes
            bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
            int k;
            while ((k = bufferedInputStream.read()) != -1) {
                fileData.append((char) k);
            }


            //fill array with data from file
            System.out.println(fileData);
            String[] scriptFile;
            String delimeter = "\n";
            scriptFile = fileData.toString().replaceAll("\r", "").split(delimeter);


            return scriptFile;
        } catch (FileExeption | FileNotFoundException e) {
            System.err.println(e.getMessage());
            return getFileScript();


        } catch (IOException ex) {
            ex.printStackTrace();
            return getFileScript();
        }
    }
}

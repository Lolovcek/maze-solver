import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindPath {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        boolean isRunning = true;
        scanner = new Scanner(System.in);
        String inputType = "";
        String filename;

        while (isRunning) {
            System.out.println("How do you want to input the map?" +
                    "\n File: 1" +
                    "\n Input from console: 2" +
                    "\n Exit: 3");
            inputType = scanner.nextLine();
            switch (inputType) {
                case "1":
                    System.out.println("Specify the file name:");
                    filename = scanner.nextLine();
                    File file = new File(System.getProperty("user.dir") + "\\mazes\\" + filename);
                    if (file.exists()) {
                        FindPathInputReaderFile findPathInputReaderFile = new FindPathInputReaderFile(file, scanner);
                        System.out.println("File exists");
                    }
                    break;
                case "2":
                    FindPathInputReaderStdIn findPathInputReaderStdIn = new FindPathInputReaderStdIn(scanner);
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindPath {

    public static void main(String[] args) throws FileNotFoundException, AbstractFindPathInputReaderException, InterruptedException {
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        String inputType;
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
                    if (file.exists() && filename.endsWith(".txt") && file.length() != 0) {
                        System.out.println("File exists!");
                        FindPathInputReaderFile findPathInputReaderFile = new FindPathInputReaderFile(file, scanner);
                        findPathInputReaderFile.readFile();
                        findPathInputReaderFile.createMatrix();
                        int[][] mapMatrix = findPathInputReaderFile.getMapMatrix();
                        findPathInputReaderFile.showMap(mapMatrix);
                        boolean[][] processed = new boolean[mapMatrix.length][mapMatrix[0].length];
                        Point[][] points = new Point[mapMatrix.length][mapMatrix[0].length];
                        Point endpoint = findPathInputReaderFile.explore(mapMatrix, processed, points);
                        System.out.println(findPathInputReaderFile.reconstructPath(endpoint));
                    } else {
                        System.out.println("File does not exist, is not a .txt extension or is empty! Going back to menu...");
                    }
                    break;
                case "2":
                    FindPathInputReaderStdIn findPathInputReaderStdIn = new FindPathInputReaderStdIn(scanner);
                    findPathInputReaderStdIn.checkSize();
                    findPathInputReaderStdIn.getStdIn();
                    findPathInputReaderStdIn.createMatrix();
                    int[][] mapMatrix = findPathInputReaderStdIn.getMapMatrix();
                    findPathInputReaderStdIn.showMap(mapMatrix);
                    boolean[][] processed = new boolean[mapMatrix.length][mapMatrix[0].length];
                    Point[][] points = new Point[mapMatrix.length][mapMatrix[0].length];
                    Point endpoint = findPathInputReaderStdIn.explore(mapMatrix, processed, points);
                    System.out.println(findPathInputReaderStdIn.reconstructPath(endpoint));
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

import java.util.ArrayList;
import java.util.Scanner;


public class FindPathInputReaderStdIn extends AbstractFindPathInputReader {
    private int mapLength, mapHeight;
    private final Scanner scanner;
    private final ArrayList<String> map = new ArrayList<>();
    private int[][] mapMatrix;

    /**
     *
     * @param scanner - global scanner being sent to classes
     * @throws InterruptedException - timeout
     * @throws AbstractFindPathInputReaderException - wrong symbol in map
     */
    public FindPathInputReaderStdIn(Scanner scanner) throws InterruptedException, AbstractFindPathInputReaderException {
        this.scanner = scanner;
    }


    /**
     * First the user needs to input the dimensions of the maze
     * so the program will know how long should the next inputs be
     */
    public void checkSize() {
        String line;
        while(true) {
            System.out.println("Specify the dimensions (eg. 5x7):");
            line = this.scanner.nextLine();
            if(line.contains("x")) {
                String[] dimensions = line.split("x");
                if (dimensions.length != 2) {
                    System.out.println("Wrong input!");
                } else {
                    if (isNumber(dimensions[0]) && isNumber(dimensions[1])) {
                        if (Integer.parseInt(dimensions[0]) < 2 && Integer.parseInt(dimensions[0]) < 2) {
                            System.out.println("Map is too small!");
                        } else {
                            this.mapHeight = Integer.parseInt(dimensions[0]);
                            this.mapLength = Integer.parseInt(dimensions[1]);
                            break;
                        }
                    } else {
                        System.out.println("Input is not numeric!");
                    }
                }
            }
        }
    }

    /**
     * Handle user input if the map is being put in manually
     */
    public void getStdIn() {
        String line;
        int lineCounter = 0;
        while (lineCounter < this.mapHeight) {
            line = this.scanner.nextLine();
            if (line.length() != this.mapLength) {
                System.out.println("Wrong length of the map row!");
            } else {
                this.map.add(line);
                lineCounter++;
            }
        }
    }

    /**
     *
     * converts the matrix into a 2D integer array
     * @throws InterruptedException - timeout for the map generation (2 seconds)
     * @throws AbstractFindPathInputReaderException - if an invalid symbol is in the map
     */
    public void createMatrix() throws InterruptedException, AbstractFindPathInputReaderException {
        if (super.validateMap(this.map)) {
            this.mapMatrix = super.parseMap(this.map);
        } else {
            this.map.clear();
        }
    }

    /**
     *
     * @param number - in string format
     * @return if it is a number or not
     */
    private boolean isNumber(String number) {
        if (number == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public int[][] getMapMatrix() {
        return mapMatrix;
    }
}

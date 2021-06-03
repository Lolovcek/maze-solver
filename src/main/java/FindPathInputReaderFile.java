import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FindPathInputReaderFile extends AbstractFindPathInputReader {
    private final File file;
    private final ArrayList<String> map = new ArrayList<>();
    private Scanner scanner;
    private int[][] mapMatrix;

    public FindPathInputReaderFile(File file, Scanner scanner) throws FileNotFoundException, AbstractFindPathInputReaderException, InterruptedException {
        this.file = file;
        this.scanner = scanner;
        readFile();
        createMatrix();
        super.showMap(this.mapMatrix);
        boolean[][] processed = new boolean[mapMatrix.length][mapMatrix[0].length];
        Point[][] points = new Point[mapMatrix.length][mapMatrix[0].length];
        points = super.explore(this.mapMatrix, processed, points);
        System.out.println(super.reconstructPath());
    }

    /**
     * Reads the file line by line and adds it to the temporary arraylist map
     * @throws FileNotFoundException - file error
     */
    private void readFile() throws FileNotFoundException {
        this.scanner = new Scanner(this.file);
        while(this.scanner.hasNextLine()) {
            this.map.add(this.scanner.nextLine());
        }
    }

    /**
     *
     * converts the matrix into a 2D integer array
     * @throws InterruptedException - timeout for the map generation (2 seconds)
     * @throws AbstractFindPathInputReaderException - if an invalid symbol is in the map
     */
    private void createMatrix() throws InterruptedException, AbstractFindPathInputReaderException {
        if (super.validateMap(this.map)) {
            this.mapMatrix = super.parseMap(this.map);
        } else {
            this.map.clear();
        }
    }

}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindPathInputReaderFile extends AbstractFindPathInputReader {
    private final File file;
    private final ArrayList<String> map = new ArrayList<>();
    private Scanner scanner;
    private int[][] mapMatrix;

    public FindPathInputReaderFile(File file, Scanner scanner) {
        this.file = file;
        this.scanner = scanner;
    }

    /**
     * Reads the file line by line and adds it to the temporary arraylist map
     * @throws FileNotFoundException - file error
     */
    public void readFile() throws FileNotFoundException {
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
    public void createMatrix() throws InterruptedException, AbstractFindPathInputReaderException {
        if (super.validateMap(this.map)) {
            this.mapMatrix = super.parseMap(this.map);
        } else {
            this.map.clear();
        }
    }

    public int[][] getMapMatrix() {
        return mapMatrix;
    }

    public ArrayList<String> getMap() {
        return map;
    }
}

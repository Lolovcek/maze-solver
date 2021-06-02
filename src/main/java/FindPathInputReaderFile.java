import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindPathInputReaderFile extends AbstractFindPathInputReader {
    private File file;
    private ArrayList<String[]> map = new ArrayList<>();
    private Scanner scanner;

    public FindPathInputReaderFile(File file, Scanner scanner) throws FileNotFoundException {
        this.file = file;
        this.scanner = scanner;
        readFile();

        if(super.checkMap(map)) {
            System.out.println("Map is valid!");
        } else {
            System.out.println("Map is invalid!");
            map.clear();
        }
    }

    private void readFile() throws FileNotFoundException {
        scanner = new Scanner(file);


        while(scanner.hasNextLine()) {
            map.add(scanner.nextLine().split(""));
        }

        for (String[] strings : map) {
            for (int j = 0; j < map.get(0).length; j++) {
                System.out.print(strings[j]);
            }
            System.out.println();
        }
    }
}

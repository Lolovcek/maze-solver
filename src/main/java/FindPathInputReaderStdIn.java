import java.util.ArrayList;
import java.util.Scanner;

public class FindPathInputReaderStdIn extends AbstractFindPathInputReader {

    private int mapLength, mapHeight;
    private Scanner scanner;
    private ArrayList<String[]> map = new ArrayList<>();


    public FindPathInputReaderStdIn(Scanner scanner) {
        this.scanner = scanner;
        checkSize();
        getStdIn();

        if(super.checkMap(map)) {
            System.out.println("Map is valid!");
        } else {
            System.out.println("Map is invalid!");
            map.clear();
        }

    }

    private void checkSize() {
        String line;
        while(true) {
            System.out.println("Specify the dimensions (eg. 5x7):");
            line = scanner.nextLine();
            //TODO: EXIT
            if(line.contains("x")) {
                String[] temp = line.split("x");
                if (temp.length != 2) {
                    System.out.println("Wrong input!");
                } else {
                    if (isNumber(temp[0]) && isNumber(temp[1])) {
                        if (Integer.parseInt(temp[0]) < 2 && Integer.parseInt(temp[0]) < 2) {
                            System.out.println("Map is too small!");
                        } else {
                            mapHeight = Integer.parseInt(temp[0]);
                            mapLength = Integer.parseInt(temp[1]);
                            break;
                        }
                    } else {
                        System.out.println("Input is not numeric!");
                    }
                }
            }
        }
    }

    private void getStdIn() {
        String line;
        int lineCounter = 0;
        while (lineCounter < mapHeight) {
            line = scanner.nextLine();
            if (line.length() != mapLength) {
                System.out.println("Wrong length of the map row!");
            } else {
                map.add(line.split(""));
                lineCounter++;
            }
        }
    }

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

}

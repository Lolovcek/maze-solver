import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractFindPathInputReader {

    private Scanner scanner;
    private ArrayList<String[]> map = new ArrayList<>();

    public AbstractFindPathInputReader() {

    }


    public boolean checkMap(ArrayList<String[]> map) {
        int xCount = 0, sCount = 0;
        for(String[] mapLine: map) {
            for (String letter: mapLine) {
                switch (letter) {
                    case "X":
                        xCount++;
                        if (xCount > 1) {
                            System.out.println("There is more or less than exactly one end point!");
                            return false;
                        }
                        break;
                    case "S": ;
                        sCount++;
                        if (sCount > 1) {
                            System.out.println("There is more or less than exactly one start point!");
                            return false;
                        }
                        break;
                    case ".":
                        break;
                    case "#":
                        break;
                    default: return false;

                }
            }

        }
        return xCount == 1 && sCount == 1;
    }

}

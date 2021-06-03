import java.util.*;
import java.util.concurrent.TimeUnit;


public abstract class AbstractFindPathInputReader {
    private static Point start, end;

    private boolean checkMap(ArrayList<String> map) {
        int xCount = 0, sCount = 0;
        for(String mapLine: map) {
            for(int symbol = 0; symbol < mapLine.length(); symbol++) {
                char letter = mapLine.charAt(symbol);
                switch (letter) {
                    case 'X':
                        xCount++;
                        if (xCount > 1) {
                            return false;
                        }
                        break;
                    case 'S':
                        sCount++;
                        if (sCount > 1) {
                            return false;
                        }
                        break;
                    case '.':
                        break;
                    case '#':
                        break;
                    default: return false;
                }
            }
        }
        return xCount == 1 && sCount == 1;
    }

    public boolean validateMap(ArrayList<String> map) throws InterruptedException {
        if(checkMap(map)) {
            System.out.println("Map is valid! Printing parsed map to console...\n");
            TimeUnit.SECONDS.sleep(2);
            return true;
        } else {
            System.out.println("Map is invalid! Check your input / file...\n");
            return false;
        }
    }

    public int[][] parseMap(ArrayList<String> parsedMap) throws AbstractFindPathInputReaderException {
        int width = parsedMap.size();
        int height = parsedMap.get(0).length();
        int[][] MAP = new int[width][height];

        for(int x = 0; x < parsedMap.size(); x++) {
            String line = parsedMap.get(x);
            for (int y = 0; y < line.length(); y++) {
                MAP[x][y] = translate(line.charAt(y));
            }
        }
        return MAP.clone();
    }

    private static int translate(char symbol) throws AbstractFindPathInputReaderException {
        switch(symbol) {
            case '#':
                return 1;
            case '.':
                return 0;
            case 'S':
                return 2;
            case 'X':
                return 3;
            default:
                throw new AbstractFindPathInputReaderException("Symbol not recognized!");
        }
    }

    public void showMap(int[][] mapMatrix) {
        if (mapMatrix != null) {
            for (int[] matrix : mapMatrix) {
                for (int y = 0; y < mapMatrix[0].length; y++) {
                    System.out.print(matrix[y] + " ");
                }
                System.out.println();
            }
            System.out.println("\nLegend: 0 - free space, 1 - wall, 2 - start point, 3 - destination point\n");
        }
    }

    public Point[][] explore(int[][] matrix, boolean[][] visited, Point[][] path) {
        getPositions(matrix);


        visited[start.getX()][start.getY()] = true;
        Queue<Point> neighbours = new LinkedList<>();
        neighbours.add(new Point(start.getX(), start.getY(), start.getDistance(), null));


        while(!neighbours.isEmpty()) {
            Point neighbour = neighbours.poll();

            if (matrix[neighbour.getX()][neighbour.getY()] == 3) {
                end = neighbour;
                return path;
            }


            int[] deltaRow = {-1, 0, 1, 0};
            int[] deltaCol = {0, 1, 0, -1};

            for(int i = 0; i <= 3; i++) {
                int neighborRow = neighbour.getX() + deltaRow[i];
                int neighborCol = neighbour.getY() + deltaCol[i];
                if(isValid(neighborRow, neighborCol, matrix, visited)) {
                    neighbours.add(new Point(neighborRow, neighborCol, neighbour.getDistance() + 1, neighbour));
                    visited[neighborRow][neighborCol] = true;
                    path[neighborRow][neighborCol] = neighbour;
                }
            }
        }
        return null;
    }

    public String reconstructPath() {
        ArrayList<Point> finalPath = new ArrayList<>();
        Point temp = end;

        while(temp.getParent() != null) {
            finalPath.add(temp);
            temp = temp.getParent();
        }
        finalPath.add(temp);
        Collections.reverse(finalPath);
        return transformPath(finalPath);
    }

    private String transformPath(ArrayList<Point> path) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < path.size() - 1; i++) {
            int x = path.get(i+1).getX() - path.get(i).getX();
            int y = path.get(i+1).getY() - path.get(i).getY();
            if (x == -1) {
                stringBuilder.append("u,");
            } else if (x == 1) {
                stringBuilder.append("d,");
            }
            if (y == -1) {
                stringBuilder.append("l,");
            }
            else if (y == 1) {
                stringBuilder.append("r,");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }



    /**
     *
     * @param x - x position in matrix
     * @param y - y position in matrix
     * @param matrix - whole matrix
     * @param visited - which points were already visited
     * @return - if the point is within bounds
     */
    private static boolean isValid(int x, int y, int[][] matrix, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length && matrix[x][y] != 1 && !visited[x][y];
    }

    private static void getPositions(int[][] matrix) {
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[0].length; y++) {
                if (matrix[x][y] == 2) {
                    start = new Point(x,y, 0, null);
                }
            }
        }
    }
}

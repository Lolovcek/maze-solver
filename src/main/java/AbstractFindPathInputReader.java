import java.util.*;

public abstract class AbstractFindPathInputReader {


    /**
     * This method checks if all the symbols in the map are valid and if there is exactly one
     * starting point and one end point
     *
     * @param map - ArrayList of the map read from the file or from the input
     * @return - boolean value if the map is valid or not
     */
    public boolean checkMap(ArrayList<String> map) {
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


    /**
     * Method which handles the result of map checking, so the user knows if something is wrong with the map.
     *
     * @param map - ArrayList of the map read from the file or from the input
     * @return - boolean value if the map is valid or not
     */
    public boolean validateMap(ArrayList<String> map) {
        if(checkMap(map)) {
            System.out.println("Map is valid!\n");
            return true;
        } else {
            System.out.println("Map is invalid!\n");
            return false;
        }
    }

    /**
     * Method parses the map from an ArrayList to a 2D representation of the map
     *
     * @param parsedMap - ArrayList of the map read from the file or from the input
     * @return - 2D representation of the map
     * @throws AbstractFindPathInputReaderException - Error exception thrown when the symbol is not one of the expected ones
     */
    public int[][] parseMap(ArrayList<String> parsedMap) throws AbstractFindPathInputReaderException {
        int width = parsedMap.size();
        int height = parsedMap.get(0).length();
        int[][] map = new int[width][height];

        for(int x = 0; x < parsedMap.size(); x++) {
            String line = parsedMap.get(x);
            for (int y = 0; y < line.length(); y++) {
                map[x][y] = translate(line.charAt(y));
            }
        }
        return map;
    }

    /**
     * This method translates the symbols into numbers
     *
     * @param symbol - character which should be translated
     * @return - number representation of the symbol
     * @throws AbstractFindPathInputReaderException - Error exception thrown when the symbol is not one of the expected ones
     */
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

    /**
     * Prints out the map
     *
     * @param mapMatrix - 2D array of the map
     */
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

    /**
     * Method explores all the possible directions it could take from the point its standing on
     * and then adds to the queue if the point wasn't visited. This goes on until the end point
     * was found.
     *
     * @param matrix - 2D array of the map
     * @param visited - 2D array of the map, which saves all the visited points to save time
     * @param path - 2D array of the map, which has all the points, so it can't traverse back to the starting point
     * @return - end position
     */
    public Point explore(int[][] matrix, boolean[][] visited, Point[][] path) {
        Point start = getPositions(matrix);

        if (start != null) {
            visited[start.getX()][start.getY()] = true;
            Queue<Point> neighbours = new LinkedList<>();
            neighbours.add(new Point(start.getX(), start.getY(), start.getDistance(), null));


            while (!neighbours.isEmpty()) {
                Point neighbour = neighbours.poll();

                if (matrix[neighbour.getX()][neighbour.getY()] == 3) {
                    return neighbour;
                }

                int[] deltaRow = {-1, 0, 1, 0};
                int[] deltaCol = {0, 1, 0, -1};

                for (int i = 0; i <= 3; i++) {
                    int neighborRow = neighbour.getX() + deltaRow[i];
                    int neighborCol = neighbour.getY() + deltaCol[i];
                    if (isValid(neighborRow, neighborCol, matrix, visited)) {
                        neighbours.add(new Point(neighborRow, neighborCol, neighbour.getDistance() + 1, neighbour));
                        visited[neighborRow][neighborCol] = true;
                        path[neighborRow][neighborCol] = neighbour;
                    }
                }
            }
            return null;
        }
        return null;
    }

    /**
     * Method checks each point from end to start, checks the parent of that specific point (from where it moved)
     * and adds to the arrayList. At the end reverse the list to get the path from start to end.
     *
     * @param endpoint - Position of endpoint in the matrix
     * @return - String of the path taken from the start to the end in the maze
     */
    public String reconstructPath(Point endpoint) {
        if(endpoint != null) {
            ArrayList<Point> finalPath = new ArrayList<>();
            Point currentPoint = endpoint;

            while (currentPoint.getParent() != null) {
                finalPath.add(currentPoint);
                currentPoint = currentPoint.getParent();
            }
            finalPath.add(currentPoint);
            Collections.reverse(finalPath);
            return transformPath(finalPath);
        } else {
            return "There was no valid path in the maze!";
        }
    }

    /**
     * Method to transform the path from points to a readable format with directions
     *
     * @param path - list of points from start to end
     * @return - String of the path taken to the end
     */
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
     * Check if the next move is valid
     *
     * @param x - x position in matrix
     * @param y - y position in matrix
     * @param matrix - whole matrix (map)
     * @param visited - which points were already visited
     * @return - if the point is within bounds
     */
    private static boolean isValid(int x, int y, int[][] matrix, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length && matrix[x][y] != 1 && !visited[x][y];
    }

    /**
     * Get the position of the starting point located in the matrix
     *
     * @param matrix - whole matrix (map)
     * @return - the position of the starting point, null if there was none
     */
    private static Point getPositions(int[][] matrix) {
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[0].length; y++) {
                if (matrix[x][y] == 2) {
                    return new Point(x,y, 0, null);
                }
            }
        }
        return null;
    }
}

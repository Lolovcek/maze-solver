import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Tests {

    File testFile1 = new File(System.getProperty("user.dir") + "\\mazes\\testMaze1.txt");
    File testFile2 = new File(System.getProperty("user.dir") + "\\mazes\\testMaze2.txt");
    File testFile3 = new File(System.getProperty("user.dir") + "\\mazes\\testMaze3.txt");

    FindPathInputReaderFile findPathInputReaderFile;

    @Test
    @DisplayName("Check if testMaze1 is a valid map")
    void shouldCheckMapTestMap1() throws FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile1, new Scanner(testFile1));
        findPathInputReaderFile.readFile();
        Assertions.assertTrue(findPathInputReaderFile.checkMap(findPathInputReaderFile.getMap()));
    }

    @Test
    @DisplayName("Check if testMaze2 is a valid map")
    void shouldCheckMapTestMap2() throws FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile2, new Scanner(testFile2));
        findPathInputReaderFile.readFile();
        Assertions.assertTrue(findPathInputReaderFile.checkMap(findPathInputReaderFile.getMap()));
    }

    @Test
    @DisplayName("Check if testMaze3 is a valid map")
    void shouldCheckMapTestMap3() throws FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile3, new Scanner(testFile3));
        findPathInputReaderFile.readFile();
        Assertions.assertFalse(findPathInputReaderFile.checkMap(findPathInputReaderFile.getMap()));
    }

    @Test
    @DisplayName("Parsing of testMaze1")
    void shouldParseMapTestMap1() throws AbstractFindPathInputReaderException, FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile1, new Scanner(testFile1));
        findPathInputReaderFile.readFile();
        int[][] correctMap = {
                { 2, 0, 0 },
                { 1, 1, 1 },
                { 0, 0, 3 }};
        int[][] parsedMap = findPathInputReaderFile.parseMap(findPathInputReaderFile.getMap());
        Assertions.assertArrayEquals(correctMap, parsedMap);
    }

    @Test
    @DisplayName("Parsing of testMaze2")
    void shouldParseMapTestMap2() throws AbstractFindPathInputReaderException, FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile2, new Scanner(testFile2));
        findPathInputReaderFile.readFile();
        int[][] correctMap = {
                { 0, 0, 2 },
                { 0, 1, 1 },
                { 0, 0, 3 }};
        int[][] parsedMap = findPathInputReaderFile.parseMap(findPathInputReaderFile.getMap());
        Assertions.assertArrayEquals(correctMap, parsedMap);
    }

    @Test
    @DisplayName("Parsing of testMaze3")
    void shouldParseMapTestMap3() throws AbstractFindPathInputReaderException, FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile3, new Scanner(testFile3));
        findPathInputReaderFile.readFile();
        int[][] correctMap = {
                { 0, 0, 2 },
                { 0, 1, 1 },
                { 0, 3, 3 }};
        int[][] parsedMap = findPathInputReaderFile.parseMap(findPathInputReaderFile.getMap());
        Assertions.assertArrayEquals(correctMap, parsedMap);
    }

    @Test
    @DisplayName("Find the shortest path in testMaze1")
    void shouldFindShortestPathTestMap1() throws FileNotFoundException, AbstractFindPathInputReaderException, InterruptedException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile1, new Scanner(testFile1));
        findPathInputReaderFile.readFile();
        findPathInputReaderFile.createMatrix();
        int[][] map = findPathInputReaderFile.getMapMatrix();
        boolean[][] visited = new boolean[map.length][map[0].length];
        Point[][] points = new Point[map.length][map[0].length];

        Point endPoint = findPathInputReaderFile.explore(map, visited, points);

        Assertions.assertEquals("There was no valid path in the maze!",
                findPathInputReaderFile.reconstructPath(endPoint));
    }

    @Test
    @DisplayName("Find the shortest path in testMaze2")
    void shouldFindShortestPathTestMap2() throws FileNotFoundException, AbstractFindPathInputReaderException, InterruptedException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile2, new Scanner(testFile2));
        findPathInputReaderFile.readFile();
        findPathInputReaderFile.createMatrix();
        int[][] map = findPathInputReaderFile.getMapMatrix();
        boolean[][] visited = new boolean[map.length][map[0].length];
        Point[][] points = new Point[map.length][map[0].length];

        Point endPoint = findPathInputReaderFile.explore(map, visited, points);

        Assertions.assertEquals("l,l,d,d,r,r",
                findPathInputReaderFile.reconstructPath(endPoint));
    }

    @Test
    @DisplayName("Find the shortest path in testMaze3")
    void shouldFindShortestPathTestMap3() throws AbstractFindPathInputReaderException, InterruptedException, FileNotFoundException {
        findPathInputReaderFile = new FindPathInputReaderFile(testFile3, new Scanner(testFile3));
        findPathInputReaderFile.readFile();
        findPathInputReaderFile.createMatrix();
        int[][] map = findPathInputReaderFile.getMapMatrix();
        if (map != null) {
            boolean[][] visited = new boolean[map.length][map[0].length];
            Point[][] points = new Point[map.length][map[0].length];

            Point endPoint = findPathInputReaderFile.explore(map, visited, points);

            Assertions.assertEquals("There was no valid path in the maze!",
                    findPathInputReaderFile.reconstructPath(endPoint));
        }
        else {
            System.out.println("The map is null because the matrix won't be created. It wasn't validated so it\n" +
                               "has illegal symbols or the amount of end and start points is not equal to one.");
            Assertions.assertNull(map);
        }
    }
}
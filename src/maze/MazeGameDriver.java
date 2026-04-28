package maze;

import maze.ui.MazeViewer;

import java.util.Scanner;

public class MazeGameDriver {

    static String filePath = "large.maze";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose maze type: red or blue?");
        String choice = scanner.nextLine().trim().toLowerCase();

        MazeFactory factory = choice.equals("red") ? new RedMazeFactory() : new BlueMazeFactory();

        Maze maze = loadMaze(filePath, factory);
        MazeViewer viewer = new MazeViewer(maze);
        viewer.run();
    }

    public static Maze loadMaze(String path, MazeFactory factory) {
        return factory.loadMaze(path);
    }
}

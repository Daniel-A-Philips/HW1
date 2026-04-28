package maze;

import maze.ui.MazeViewer;

import java.util.Scanner;

public class MazeGameDriver {

    static String filePath = "large.maze";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose maze type: red or blue?");
        String choice = scanner.nextLine().trim().toLowerCase();

        MazeGameCreator factory = choice.equals("red") ? new RedMazeGameCreator() : new BlueMazeGameCreator();

        Maze maze = loadMaze(filePath, factory);
        MazeViewer viewer = new MazeViewer(maze);
        viewer.run();
    }

    public static Maze loadMaze(String path, MazeGameCreator factory) {
        return factory.loadMaze(path);
    }
}

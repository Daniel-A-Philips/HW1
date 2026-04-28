package maze;

public class MazeGameDriver {

    String filePath;

    static void main(String[] args) {
    }

    public Maze loadMaze(String path, MazeFactory factory) {
        return factory.createMaze();
    }
}

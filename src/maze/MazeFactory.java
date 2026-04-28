package maze;

public abstract class MazeFactory {

    public Maze createMaze() {
        // TODO
        return null;
    }

    public Maze loadMaze(String path) {
        // TODO
        return null;
    }

    public abstract Wall makeWall();

    public abstract Door makeDoor(Room r1, Room r2);

    public abstract Room makeRoom(int roomNum);
}

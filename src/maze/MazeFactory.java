package maze;

import java.util.ArrayList;

public abstract class MazeFactory {

    public Maze createMaze() {
        return new Maze();
    }

    public Maze loadMaze(String path) {
        return null;
    }

    public abstract Wall makeWall();

    public abstract Door makeDoor(Room r1, Room r2);

    public abstract Room makeRoom(int roomNum);
}

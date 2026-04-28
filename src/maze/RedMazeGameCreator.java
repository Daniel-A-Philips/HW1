package maze;

public class RedMazeGameCreator extends MazeGameCreator {

    @Override
    public Wall makeWall() {
        return new RedWall();
    }

    @Override
    public Door makeDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }

    @Override
    public Room makeRoom(int roomNum) {
        return new PinkRoom(roomNum);
    }
}

package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class MazeFactory {

    public Maze createMaze() {
        return new Maze();
    }

    public Maze loadMaze(String path) {
        Maze maze = new Maze();
        ArrayList<String> fileData = readFile(path);
        int numRooms = getNumRooms(fileData);

        // Rooms
        ArrayList<Room> rooms = new ArrayList<>();
        for(int i = 0; i < numRooms; i++) {
            rooms.add(makeRoom(i));
        }

        // Doors
        ArrayList<Door> doors = new ArrayList<>();
        for(int i = numRooms; i < fileData.size(); i++) {
            String[] line = fileData.get(i).split(" ");
            Door door = makeDoor(rooms.get(Integer.parseInt(line[2])), rooms.get(Integer.parseInt(line[3])));
            door.setOpen(!line[4].equals("close"));
            doors.add(door);
        }

        // Walls
        for(int i = 0; i < rooms.size(); i++) {
            String[] line = fileData.get(i).split(" ");
            Room room = rooms.get(i);
            parseRoomWall(line[2], room, doors, Direction.North, rooms);
            parseRoomWall(line[3], room, doors, Direction.South, rooms);
            parseRoomWall(line[4], room, doors, Direction.East, rooms);
            parseRoomWall(line[5], room, doors, Direction.West, rooms);
        }

        for (Room room : rooms) maze.addRoom(room);

        maze.setCurrentRoom(0);
        return maze;
    }

    public abstract Wall makeWall();

    public abstract Door makeDoor(Room r1, Room r2);

    public abstract Room makeRoom(int roomNum);

    // HELPERS:
    private ArrayList<String> readFile(final String filename) {
        ArrayList<String> commands = new ArrayList<>();
        File file = new File(filename);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if(data.isEmpty()) continue;
                commands.add(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred during the reading of " + filename);
            e.printStackTrace();
        }

        return commands;
    }

    private static int getNumRooms(ArrayList<String> l) {
        int i = 0;
        while(l.get(i).split(" ")[0].equals("room")) {
            i++;
            if (l.size() == i) break;
        }
        return i;
    }

    private void parseRoomWall(String s, Room room, ArrayList<Door> doors, Direction direction, ArrayList<Room> rooms) {
        if (s.equals("wall")) {
            room.setSide(direction, makeWall()); // makeWall() not new Wall()
        } else if (s.charAt(0) == 'd') {
            room.setSide(direction, doors.get(Integer.parseInt(s.substring(1))));
        } else {
            Door door = makeDoor(room, rooms.get(Integer.parseInt(s))); // makeDoor() not new Door()
            door.setOpen(true);
            room.setSide(direction, door);
        }
    }

}

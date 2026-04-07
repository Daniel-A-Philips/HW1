/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import maze.ui.MazeViewer;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class SimpleMazeGame
{
	/**
	 * Creates a small maze.
	 */
	public static Maze createMaze()
	{
		
		Maze maze = new Maze();
		Room room_0 = new Room(0);
		Room room_1 = new Room(1);

		Door door_0 = new Door(room_0,room_1);

		room_0.setSide(Direction.South, door_0);
		room_0.setSide(Direction.North, new Wall());
		room_0.setSide(Direction.West, new Wall());
		room_0.setSide(Direction.East, new Wall());

		room_1.setSide(Direction.South, new Wall());
		room_1.setSide(Direction.North, door_0);
		room_1.setSide(Direction.West, new Wall());
		room_1.setSide(Direction.East, new Wall());



		maze.addRoom(room_0);
		maze.addRoom(room_1);

		maze.setCurrentRoom(0);

		return maze;
		

	}

	private static ArrayList<String> readFile(final String filename) {
		ArrayList<String> commands = new ArrayList<String>();
		File file = new File(filename);

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				if(data.isEmpty()) continue;
				commands.add(data);
				System.out.println(data);
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
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

	private static ArrayList<Room> createRooms(int numRooms) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for(int i = 0; i < numRooms; i++) {
			Room room = new Room(i);
			rooms.add(room);
		}
		return rooms;
	}

	private static void parseRoomWall(String s, Room room, ArrayList<Door> doors, Direction direction) {
		if(s.charAt(0) != 'd') {
			room.setSide(direction, new Wall());
		} else {
			int doorNum = Integer.parseInt(s.substring(1));
			System.out.println("Added door between " + Integer.toString(room.getNumber()) + " and " + Integer.toString(doors.get(doorNum).getOtherSide(room).getNumber()));
			room.setSide(direction, doors.get(doorNum));
		}
	}

	private static ArrayList<Room> parseWalls(ArrayList<String> fileData, ArrayList<Room> rooms, ArrayList<Door> doors) {
		for(int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
			String[] roomString = fileData.get(i).split(" ");
			parseRoomWall(roomString[2], room, doors, Direction.North);
            parseRoomWall(roomString[3], room, doors, Direction.South);
			parseRoomWall(roomString[4], room, doors, Direction.East);
			parseRoomWall(roomString[5], room, doors, Direction.West);
			rooms.set(i, room);
		}
		return rooms;
	}

	private static ArrayList<Door> parseDoors(ArrayList<String> fileData, ArrayList<Room> rooms) {
		ArrayList<Door> doors = new ArrayList<Door>();
		for(int i = rooms.size(); i < fileData.size(); i++) {
			String[] doorString = fileData.get(i).split(" ");
			int room0_num = Integer.parseInt(doorString[2]);
			int room1_num = Integer.parseInt(doorString[3]);
			String openClose = doorString[4];
			Door door = new Door(rooms.get(room0_num), rooms.get(room1_num));
			door.setOpen(!openClose.equals("close"));

			doors.add(door);
		}
		return doors;
	}

	public static Maze loadMaze(final String path)
	{
		Maze maze = new Maze();
		ArrayList<String> fileData = readFile(path);
		int numRooms = getNumRooms(fileData);

		ArrayList<Room> rooms = createRooms(numRooms);

		ArrayList<Door> doors = parseDoors(fileData, rooms);

        ArrayList<Room> newRooms = parseWalls(fileData, rooms, doors);


        // U D R L

        for (Room room : newRooms) {
            maze.addRoom(room);
			System.out.println("Added Room " + Integer.toString(room.getNumber()) + " To Maze");
        }

		maze.setCurrentRoom(0);

		return maze;
	}

	public static void main(String[] args)
	{
		// Maze maze = createMaze();
		// Input format:
		//room 23 18 wall wall 22
		//room 24 19 wall wall wall
		//door d0 11 6 close
		//door d1 12 17 close
		Maze maze = loadMaze("large.maze");
	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}

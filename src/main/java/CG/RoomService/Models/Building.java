package CG.RoomService.Models;

import java.util.ArrayList;

public class Building {
    private final String name;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public Building(String name, ArrayList<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public Building(ArrayList<Room> rooms) {
        this("", rooms);
    }

    public Building(String name) {
        this.name = name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public void addRoom(String name, int floor, int maxOccupancy, boolean accessible) {
        this.rooms.add(new Room(name, floor, maxOccupancy, accessible));
    }
}

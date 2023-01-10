package CG.RoomService.Models;

import java.util.ArrayList;

public class Building {
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public Building(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public Building() {

    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(int floor, int maxOccupancy, boolean accessible) {
        this.rooms.add(new Room(floor, maxOccupancy, accessible));
    }
}

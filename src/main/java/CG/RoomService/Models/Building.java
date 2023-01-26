package CG.RoomService.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Building() {
    }

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

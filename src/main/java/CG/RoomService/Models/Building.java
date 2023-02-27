package CG.RoomService.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "building")
    private List<Room> rooms = new ArrayList<>();



    public Building() {
    }

    public Building(String name, List<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public Building(List<Room> rooms) {
        this("", rooms);
        this.setName(id.toString());
    }

    public Building(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public void addRoom(String name, int floor, int maxOccupancy, boolean accessible) {
        Room room = new Room(name, floor, maxOccupancy, accessible);
        room.setBuilding(this.name);
        this.rooms.add(room);
    }

    public void addRoom(Room room) {
        room.setBuilding(this.name);
        this.rooms.add(room);
    }


}

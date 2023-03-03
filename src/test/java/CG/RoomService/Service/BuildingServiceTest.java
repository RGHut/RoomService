package CG.RoomService.Service;

import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingServiceTest {

    @BeforeEach
    void setUp() {
        Building building = new Building("TestBuilding1");
        Room room = new Room("testRoom1", 1, 8, true);
        building.addRoom(room);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getBuildings() {
    }

    @Test
    void getRooms() {
    }

    @Test
    void addBuilding() {
    }

    @Test
    void addRoom() {
    }

    @Test
    void getRoomsByBuilding() {
    }

    @Test
    void getRoom() {
    }
}
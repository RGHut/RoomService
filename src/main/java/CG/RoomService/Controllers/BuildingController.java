package CG.RoomService.Controllers;

import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;

import CG.RoomService.Repositories.BuildingRepository;
import CG.RoomService.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a REST controller for building and room management.
 * It defines several endpoint methods that can be accessed via HTTP requests.
 */
@RestController
public class BuildingController {

    @Autowired
    private static BuildingRepository buildingRepository;

    @Autowired
    private RoomRepository roomRepository;
    private final ArrayList<Building> buildings = new ArrayList<Building>();

    @GetMapping("/buildings")
    public static ArrayList<Building> getBuildings() {
        return (ArrayList<Building>) buildingRepository.findAll();
    }

    @PostMapping("/getRooms")
    public ResponseEntity<?> getRooms(@RequestParam String name) {
        if (buildingRepository.existsBuildingByName(name)) {
            Building building = buildingRepository.findByName(name);
            return ResponseEntity.status(200).body(building.getRooms());
        }
        for (Building building: getBuildings()) {
            if (building.getName().equals(name)) {
                return ResponseEntity.status(200).body(building.getRooms());
            }
        }
        return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
    }

    @PostMapping("/addBuilding")
    public ResponseEntity<?> addBuilding(@RequestParam String name) {
        if (buildingRepository.existsBuildingByName(name)) {
            return ResponseEntity.status(400).body("{\"error\":\"Building already exists!\"}");
        }

        for (Building building: getBuildings()) {
            if (building.getName().equals(name)) {
                return ResponseEntity.status(400).body("{\"error\":\"Building already exists!\"}");
            }
        }
        Building building = new Building(name);
        buildings.add(building);
        buildingRepository.save(building);
        return ResponseEntity.status(200).body("{\"created\":\"" + name + "\"}");
    }

    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@RequestParam String buildingName, @RequestParam String roomName) {
//        v placeholder parameters
        int floor = 0;
        int maxOccupancy= 8;
        boolean accessible = true;
//        ^ placeholder parameters
        for (Building building: buildings) {
            if (building.getName().equals(buildingName)) {
                for (Room room: building.getRooms()) {
                    if (room.getName().equals(roomName)) {
                        return ResponseEntity.status(400).body("{\"error\":\"Room already exists!\"}");
                    }
                }
                Room room = new Room(roomName, floor, maxOccupancy, accessible);
                building.addRoom(room);
                roomRepository.save(room);
                buildingRepository.save(building);
                return ResponseEntity.status(200).body("{\"created\":\"" + roomName + "\"}");
            } else {
                return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
            }
        }
        return ResponseEntity.status(200).body("{\"error\":\"Unexpected error!\"}");
    }

    @PostMapping("/getRoom")
    public ResponseEntity<?> getRoom(@RequestParam String buildingName, @RequestParam String roomName) {
        if (buildingRepository.existsBuildingByName(buildingName) && roomRepository.existsRoomByName(roomName)) {
            return ResponseEntity.status(200).body(roomRepository.findByName(roomName));
        }

        for (Building building : buildings) {
            if (building.getName().equals(roomName)) {
                for (Room room : building.getRooms()) {
                    if (room.getName().equals(roomName)) {
                        return ResponseEntity.status(200).body(room);
                    }
                }
                return ResponseEntity.status(400).body("{\"error\":\"room does not exist\"}");
            }
        }
        return ResponseEntity.status(400).body("{\"error\":\"building does not exist\"}");
    }

    @GetMapping("/switchPandemicMode")
    public void switchPandemicMode() {
        for (Building building: buildings) {
            for (Room room: building.getRooms()){
                room.switchPandemicMode();
            }
        }
        List<Room> roomList = roomRepository.findAll();
        for (Room room: roomList) {
            room.switchPandemicMode();
            roomRepository.save(room);
        }
    }


}

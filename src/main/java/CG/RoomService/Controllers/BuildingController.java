package CG.RoomService.Controllers;

import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BuildingController {

    private static final ArrayList<Building> buildings = new ArrayList<Building>();

    @GetMapping("/buildings")
    public static ArrayList<Building> getBuildings() {
        return buildings;
    }

    @PostMapping("/getRooms")
    public ResponseEntity<?> getRooms(@RequestParam String name) {
        for (Building building: buildings) {
            if (building.getName().equals(name)) {
                return ResponseEntity.status(200).body(building.getRooms());
            }
        }
        return ResponseEntity.status(400).body("{\"error\":" + "\"Building does not exists!\"" + "}");
    }

    @PostMapping("/addBuilding")
    public ResponseEntity<?> addBuilding(@RequestParam String name) {

        for (Building building: buildings) {
            if (building.getName().equals(name)) {
                return ResponseEntity.status(400).body("{\"error\":" + "\"Building already exists!\"" + "}");
            }
        }
        Building building = new Building(name);
        buildings.add(building);
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
                        return ResponseEntity.status(400).body("{\"error\":" + "\"Room already exists!\"" + "}");
                    }
                }
                building.addRoom(roomName, floor, maxOccupancy, accessible);
                return ResponseEntity.status(200).body("{\"created\":\"" + roomName + "\"}");
            } else {
                return ResponseEntity.status(400).body("{\"error\":" + "\"Building does not exists!\"" + "}");
            }
        }
        return ResponseEntity.status(200).body("{\"error\":" + "\"Unexpected error!\"" + "}");
    }

    @PostMapping("/getRoom")
    public ResponseEntity<?> getRoom(@RequestParam String buildingName, @RequestParam String roomName) {
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
    }
}

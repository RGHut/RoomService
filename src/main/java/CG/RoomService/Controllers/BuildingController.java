package CG.RoomService.Controllers;

import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;

import CG.RoomService.Repositories.BuildingRepository;
import CG.RoomService.Repositories.RoomRepository;
import CG.RoomService.Service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a REST controller for building and room management.
 * It defines several endpoint methods that can be accessed via HTTP requests.
 */
@RestController
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;


    @GetMapping("/buildings")
    public ArrayList<Building> getBuildings() {
        return buildingService.getBuildings();
    }

    @PostMapping("/getRooms")
    public ResponseEntity<?> getRooms(@RequestParam String name) {
        if (buildingService.isBuildingExist(name)) {
            return ResponseEntity.status(200).body(buildingService.getRooms(name));
        }
        return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
    }

    @PostMapping("/addBuilding")
    public ResponseEntity<?> addBuilding(@RequestParam String name) {
        if (buildingService.addBuilding(name)) {
            return ResponseEntity.status(400).body("{\"error\":\"Building already exists!\"}");
        }
        return ResponseEntity.status(200).body("{\"created\":\"" + name + "\"}");
    }

    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@RequestBody Room room) {
        if (buildingService.isBuildingExist(room.getBuilding())) {
            if (buildingService.isRoomExist(room.getName())) {
                return ResponseEntity.status(400).body("{\"error\":\"Room already exists!\"}");
            }
            buildingService.addRoom(room);
            return ResponseEntity.status(200).body("{\"created\":\"" + room.getName() + "\"}");
        } else {
            return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
        }
//        return ResponseEntity.status(200).body("{\"error\":\"Unexpected error!\"}");
    }

    @PostMapping("/getRoom")
    public ResponseEntity<?> getRoom(@RequestParam String buildingName, @RequestParam String roomName) {
        if (buildingService.isBuildingExist(buildingName)) {
            if (buildingService.isRoomExist(roomName)) {
                return ResponseEntity.status(200).body(buildingService.getRoom(roomName));
            }
            return ResponseEntity.status(400).body("{\"error\":\"room does not exist\"}");
        }
        return ResponseEntity.status(400).body("{\"error\":\"building does not exist\"}");
    }

    @GetMapping("/switchPandemicMode")
    public void switchPandemicMode() {
        buildingService.switchPandemicMode();
    }


}

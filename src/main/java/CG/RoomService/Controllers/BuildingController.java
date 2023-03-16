package CG.RoomService.Controllers;

import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;

import CG.RoomService.Service.BookingService;
import CG.RoomService.Service.BuildingService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/**
 * This class is a REST controller for building and room management.
 * It defines several endpoint methods that can be accessed via HTTP requests.
 */
@RestController
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    private final BookingService bookingService;


    /**
     * API endpoint to get a list of buildings from the database
     * @return a list of buildings
     */
    @GetMapping("/buildings")
    public ArrayList<Building> getBuildings() {
        return buildingService.getBuildings();
    }

    /**
     *API endpoint to get a list of rooms from a given building
     * @param name of the building
     * @return ResponseBody with a list of rooms or an error message
     */
    @PostMapping("/getRooms")
    public ResponseEntity<?> getRooms(@RequestParam String name) {
        if (buildingService.isBuildingExist(name)) {
            return ResponseEntity.status(200).body(buildingService.getRooms(name));
        }
        return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
    }

    /**
     * API endpoint to add a building to the database
     * @param name of the building to add
     * @return response with a succes message or an error message if the building already exists
     */
    @PostMapping("/addBuilding")
    public ResponseEntity<?> addBuilding(@RequestParam String name) {
        if (buildingService.addBuilding(name)) {
            return ResponseEntity.status(400).body("{\"error\":\"Building already exists!\"}");
        }
        return ResponseEntity.status(200).body("{\"created\":\"" + name + "\"}");
    }

    /**
     * API endpoint to add a room to an existing building
     * @param room Room object to be added to the database
     * @return response with an error message if the room already exists or if the building the room needs to be added to already exists, or a succes message.
     */
    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@RequestBody Room room) {
        if (buildingService.isBuildingExist(room.getBuilding().getName())) {
            if (buildingService.isRoomExist(room.getName())) {
                return ResponseEntity.status(400).body("{\"error\":\"Room already exists!\"}");
            }
            buildingService.addRoom(room);
            return ResponseEntity.status(200).body("{\"created\":\"" + room.getName() + "\"}");
        } else {
            return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
        }
    }

    /**
     * API endpoint to delete a room if it has no bookings
     * @param roomName name of the room to be deleted
     * @return response with a succes or error message
     */
    @DeleteMapping("deleteRoom")
    public ResponseEntity<?> deleteRoom(@RequestParam String roomName) {
        bookingService.bookingCleanup();
        if (buildingService.deleteRoom(roomName)) {
            return ResponseEntity.status(200).body("{\"Deleted\":\"" + roomName + "\"}");
        }
        return ResponseEntity.status(400).body("{\"error\":\"room does not exist\"}");
    }

    /**
     * API endpoint to delete a room even if it has bookings
     * @param roomName name of the room to be deleted
     * @return response with a succes or error message
     */
    @DeleteMapping("deleteRoomHard")
    public ResponseEntity<?> deleteRoomHard(@RequestParam String roomName) {
        bookingService.bookingCleanup();
        if (buildingService.deleteRoomHard(roomName)) {
            return ResponseEntity.status(200).body("{\"Deleted\":\"" + roomName + "\"}");
        }
        return ResponseEntity.status(400).body("{\"error\":\"room does not exist\"}");
    }

    /**
     * API endpoint to delete a building
     * @param buildingName name of the building to be deleted
     * @return response with a success or error message
     */
    @DeleteMapping("deleteBuilding")
    public ResponseEntity<?> deleteBuilding(@RequestParam String buildingName) {
        bookingService.bookingCleanup();
        if (buildingService.deleteBuilding(buildingName)) {
            return ResponseEntity.status(200).body("{\"Deleted\":\"" + buildingName + " and all its rooms\"}");
        }
        return ResponseEntity.status(400).body("{\"error\":\"building does not exist\"}");
    }

    /**
     * API endpoint to get a room from the database
     * @param buildingName name of the building that contains the room
     * @param roomName name of the room to be retrieved
     * @return ResponseBody containing either an error message or a Room object.
     */
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

    /**
     * API endpoint to switch all rooms in all buildings to or from pandemic mode, halving or doubling their maximum occupancy.
     */
    @GetMapping("/switchPandemicMode")
    public void switchPandemicMode() {
        buildingService.switchPandemicMode();
    }


}

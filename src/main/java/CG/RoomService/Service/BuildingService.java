package CG.RoomService.Service;

import CG.RoomService.Models.DataModels.Booking;
import CG.RoomService.Models.DataModels.Building;
import CG.RoomService.Models.DataModels.Room;
import CG.RoomService.Models.Responses.ExceptionResponse;
import CG.RoomService.Models.Responses.MessageResponse;
import CG.RoomService.Models.Responses.Response;
import CG.RoomService.Models.Responses.RoomListResponse;
import CG.RoomService.Repositories.BuildingRepository;
import CG.RoomService.Repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    private final RoomRepository roomRepository;

    private final BookingService bookingService;

    public ArrayList<Building> getBuildings() {
        return (ArrayList<Building>) buildingRepository.findAll();
    }

    public ResponseEntity<Response> getRooms(String name) {
        if (buildingRepository.existsBuildingByName(name)) {
            Building building = buildingRepository.findByName(name);
            return ResponseEntity.status(200).body(new RoomListResponse(building.getRooms()));
        }else {
            return ResponseEntity.status(400).body(new ExceptionResponse("Building does not exist"));
        }
    }

    public ResponseEntity<Response> addBuilding(String name) {
        if (buildingRepository.existsBuildingByName(name)){
            return ResponseEntity.status(400).body(new ExceptionResponse("Building already exists"));
        } else {
            Building building = new Building(name);
            buildingRepository.save(building);
            return ResponseEntity.status(200).body(new MessageResponse("Created: " + name));
        }
    }

    public ResponseEntity<Response> addRoom(Room room) {
        if (isBuildingExist(room.getBuilding().getName())) {
            if (isRoomExist(room.getName())) {
                return ResponseEntity.status(400).body(new ExceptionResponse("Room already exists"));
            }
            Building building = buildingRepository.findByName(room.getBuilding().getName());
            building.addRoom(room);
            roomRepository.save(room);
            buildingRepository.save(building);

            return ResponseEntity.status(200).body(new MessageResponse("created:" + room.getName()));
        } else {
            return ResponseEntity.status(400).body(new ExceptionResponse("Building does not exists!"));
        }
    }

    public ResponseEntity<Response> deleteBuilding(String name) {
        if(isBuildingExist(name)) {
            Building building = buildingRepository.findByName(name);
            for (Room room: building.getRooms()) {
                deleteRoomHard(room.getName());
            }
            buildingRepository.delete(building);
            return ResponseEntity.status(200).body(new MessageResponse("Deleted:" + name + " and all its rooms"));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("building does not exist"));
    }


    public ResponseEntity<Response> deleteRoomHard(String name) {
        if(isRoomExist(name)) {
            Room room = roomRepository.findByName(name);
            if (!room.getBookings().isEmpty()) {
                for (Booking booking: room.getBookings()) {
                    bookingService.cancelBooking(booking.getUser().getEmail(), booking.getToken());
                }
            }
            Building building = room.getBuilding();
            building.removeRoom(room);
            roomRepository.delete(room);
            buildingRepository.save(building);
            return ResponseEntity.status(200).body(new MessageResponse("Deleted:" + name));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("error:room does not exist"));
    }

    public ResponseEntity<Response> deleteRoom(String name) {
        if(isRoomExist(name)) {
            Room room = roomRepository.findByName(name);
            if (room.getBookings().isEmpty()) {
                return deleteRoomHard(name);
            }
            return ResponseEntity.status(400).body(new ExceptionResponse("Unable to delete a room that still has active bookings"));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("room does not exist"));
    }

    public boolean isBuildingExist(String name) {
        return buildingRepository.existsBuildingByName(name);
    }

    public boolean isRoomExist(String name) {
        return roomRepository.existsRoomByName(name);
    }

    public void switchPandemicMode() {
        for (Room room: roomRepository.findAll()) {
            room.switchPandemicMode();
            roomRepository.save(room);
        }
    }
    public Room getRoom(String roomName){
        return roomRepository.findByName(roomName);
    }
    public ResponseEntity<?> getRoomFromBuilding(String buildingName, String roomName) {
        if (isBuildingExist(buildingName)) {
            if (isRoomExist(roomName)) {
                return ResponseEntity.status(200).body(roomRepository.findByName(roomName));
            }
            return ResponseEntity.status(400).body(new ExceptionResponse("room does not exist"));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("building does not exist"));
    }
}

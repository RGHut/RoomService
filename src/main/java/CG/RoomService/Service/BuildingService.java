package CG.RoomService.Service;

import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;
import CG.RoomService.Repositories.BuildingRepository;
import CG.RoomService.Repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    private final RoomRepository roomRepository;

    private final BookingService bookingService;

    public ArrayList<Building> getBuildings() {
        return (ArrayList<Building>) buildingRepository.findAll();
    }

    public List<Room> getRooms(String name) {
        if (buildingRepository.existsBuildingByName(name)) {
            Building building = buildingRepository.findByName(name);
            return building.getRooms();
        }else {
            return null;
        }
    }

    public boolean addBuilding(String name) {
        if (buildingRepository.existsBuildingByName(name)){
            return true;
        } else {
            Building building = new Building(name);
            buildingRepository.save(building);
            return false;
        }
    }

    public void addRoom(Room room) {
        Building building = buildingRepository.findByName(room.getBuilding().getName());
        building.addRoom(room);
        roomRepository.save(room);
        buildingRepository.save(building);
    }

    public boolean deleteBuilding(String name) {
        if(isBuildingExist(name)) {
            Building building = buildingRepository.findByName(name);
            for (Room room: building.getRooms()) {
                deleteRoom(room.getName());
            }
            buildingRepository.delete(building);
            return true;
        }
        return false;
    }

    public boolean deleteRoom(String name) {
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
            return true;
        }
        return false;
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

    public List<Room> getRoomsByBuilding(String buildingName) {
        Building building = buildingRepository.findByName(buildingName);
        return roomRepository.findByBuilding(building);
    }

    public Room getRoom(String name) {
        return roomRepository.findByName(name);
    }
}

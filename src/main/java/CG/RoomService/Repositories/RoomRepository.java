package CG.RoomService.Repositories;

import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);

    List<Room> findByBuilding(Building building);

    boolean existsRoomByName(String name);
}

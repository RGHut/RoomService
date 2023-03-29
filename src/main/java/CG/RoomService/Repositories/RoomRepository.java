package CG.RoomService.Repositories;

import CG.RoomService.Models.DataModels.Room;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);

    boolean existsRoomByName(String name);
}

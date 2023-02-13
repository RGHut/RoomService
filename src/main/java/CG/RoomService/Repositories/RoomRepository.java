package CG.RoomService.Repositories;

import CG.RoomService.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);

    boolean existsRoomByName(String name);
}

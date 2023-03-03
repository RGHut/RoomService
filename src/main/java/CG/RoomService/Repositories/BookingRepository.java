package CG.RoomService.Repositories;

import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Room;
import CG.RoomService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
//    Optional<Booking> findById(long id);

    Booking findByToken(String token);

    List<Booking> findByUser(User user);

    List<Booking> findByRoom(Room room);

    boolean existsBookingByToken(String token);

}

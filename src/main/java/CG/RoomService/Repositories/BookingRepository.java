package CG.RoomService.Repositories;

import CG.RoomService.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {
//    Optional<Booking> findById(long id);

    Booking findByToken(String token);

    boolean existsBookingByToken(String token);

}

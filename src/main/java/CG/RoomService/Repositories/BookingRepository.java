package CG.RoomService.Repositories;

import CG.RoomService.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByID(long id);
}

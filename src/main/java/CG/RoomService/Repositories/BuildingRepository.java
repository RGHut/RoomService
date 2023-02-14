package CG.RoomService.Repositories;

import CG.RoomService.Models.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building findByName(String name);

    boolean existsBuildingByName(String name);
}

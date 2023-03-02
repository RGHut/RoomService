package CG.RoomService.Repositories;

import CG.RoomService.Models.Building;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building findByName(String name);

    boolean existsBuildingByName(String name);
}

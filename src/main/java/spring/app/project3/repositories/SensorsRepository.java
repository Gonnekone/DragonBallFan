package spring.app.project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.app.project3.models.Sensor;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}

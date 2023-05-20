package spring.app.project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.app.project3.models.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}

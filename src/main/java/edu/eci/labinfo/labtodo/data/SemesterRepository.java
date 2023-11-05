package edu.eci.labinfo.labtodo.data;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.eci.labinfo.labtodo.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    
    boolean existsById(Long semesterId);
    Optional<Semester> findBySemesterName(String semesterName);

    @Query("SELECT s FROM Semester s WHERE s.startDate <= :date AND s.endDate >= :date")
    Optional<Semester> findByStartDateAndEndDate(@Param("date") LocalDate date);
}

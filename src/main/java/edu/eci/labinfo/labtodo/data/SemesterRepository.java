package edu.eci.labinfo.labtodo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.labinfo.labtodo.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,Long>{
    boolean existsById(Long semesterId);
    
}

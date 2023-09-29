package edu.eci.labinfo.labtodo.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.eci.labinfo.labtodo.model.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    boolean existsById(Long taskId);

}

package edu.eci.labinfo.labtodo.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import edu.eci.labinfo.labtodo.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsById(Long taskId);

    List<Task> findByUsersUserId(Long userId);
    List<Task> findByTypeTask(String typeTask);
    List<Task> findByStatus(String status);

    @Query("SELECT t FROM Task t JOIN t.users u WHERE u.userId = :userId AND t.status = :status")
    List<Task> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);
}

package edu.eci.labinfo.labtodo.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.labinfo.labtodo.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsById(Long commentId);

    List<Comment> findByTaskTaskId(Long taskId);

}

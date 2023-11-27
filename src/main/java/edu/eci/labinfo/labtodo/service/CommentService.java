package edu.eci.labinfo.labtodo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import edu.eci.labinfo.labtodo.data.CommentRepository;
import edu.eci.labinfo.labtodo.model.Comment;
import edu.eci.labinfo.labtodo.model.Task;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getComentsByTask(Task task) {
        return commentRepository.findByTaskTaskId(task.getTaskId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Comment updateComment(Comment comment) {
        if (commentRepository.existsById(comment.getCommentId())) {
            return commentRepository.save(comment);
        }
        return null;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteAllComments() {
        commentRepository.deleteAll();
    }
    
}

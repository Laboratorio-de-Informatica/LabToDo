package edu.eci.labinfo.labtodo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.labinfo.labtodo.data.CommentRepository;
import edu.eci.labinfo.labtodo.model.Comment;

@Service
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
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

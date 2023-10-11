package edu.eci.labinfo.labtodo.model;


import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long commentId;
    private String description;
    @Column(name = "creationDate")
    private LocalDate creationDate;

    @ManyToOne(targetEntity = Task.class)
    private Task task;

    public Comment(){
        this.creationDate = LocalDate.now();
    }

    public Comment(Task task, String description) {
        this.description = description;
        this.creationDate = LocalDate.now();
        this.task = task;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }



    


    
}

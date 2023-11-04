package edu.eci.labinfo.labtodo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta es una entidad que representa una tarea en la base de datos.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;
    private String title;
    private String status;
    @Column(length = 700)
    private String description;
    private String typeTask;
    @Column(name = "creationDate")
    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "taskId"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"))
    List<User> users;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Task() {
        this.creationDate = LocalDate.now();
        this.status = Status.PENDING.getValue();
        this.users = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Task(String title, String description, TypeTask typeTask) {
        this.title = title;
        this.typeTask = typeTask.getValue();
        this.status = Status.PENDING.getValue();
        this.description = description;
        this.creationDate = LocalDate.now();
        this.users = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(new Locale("es", "ES"));
        return creationDate.format(formatter);
    }

    public String getAllUsers() {
        String allUsers = "";
        for (User user : users) {
            allUsers += user.getFullName() + " ";
        }
        return allUsers;
    }

    @Override
    public String toString() {
        return "Task [id=" + taskId + ", title=" + title + ", status=" + status + ", description=" + description
                + ", creationDate=" + creationDate + ", users=" + users + "]";
    }

}

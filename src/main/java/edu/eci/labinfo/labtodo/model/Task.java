package edu.eci.labinfo.labtodo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String title;
    private String status;
    @Column(length = 700)
    private String description;
    private String typeTask;
    private String topicTask;
    @Column(name = "creationDate")
    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "taskId"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"))
    @ToString.Exclude
    private List<User> users;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private Semester semester;

    public Task() {
        this.creationDate = LocalDate.now();
        this.status = Status.PENDING.getValue();
        this.users = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Task(String title, String description, TypeTask typeTask, TopicTask topicTask) {
        this.title = title;
        this.typeTask = typeTask.getValue();
        this.topicTask = topicTask.getValue();
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
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("es", "ES"));
        return creationDate.format(formatter);
    }

    public String getAllUsers() {
        String allUsers = "";
        for (User user : users) {
            allUsers += user.getFullName() + " ";
        }
        return allUsers;
    }
    
}

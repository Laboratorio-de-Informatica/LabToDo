package edu.eci.labinfo.labtodo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Esta es una entidad que representa a un usuario en la base de datos.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String fullName;
    private String userName;
    private String userRole;
    private String userPassword;

    @ManyToMany(mappedBy = "users")
    List<Task> tasks;

    @OneToMany(mappedBy = "creatorUser")
    List<Comment> comments = new ArrayList<>();

    public User(String fullName, String userName, String password, Role role) {
        this.fullName = fullName;
        this.userName = userName;
        this.userPassword = password;
        this.userRole = role.getValue();
        this.tasks = new ArrayList<>();
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void addTask(Task taskToAdd) {
        tasks.add(taskToAdd);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setIdeas(List<Task> task) {
        this.tasks = task;
    }

    @Override
    public String toString() {
        return "Nombre: " + fullName + ", Usuario: " + userName + ", Rol: " + userRole;
    }

}

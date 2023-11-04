package edu.eci.labinfo.labtodo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta es una entidad que representa a un usuario en la base de datos.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String fullName;
    private String userName;
    private String userRole;
    private String accountType;
    private String userPassword;

    @ManyToMany(mappedBy = "users")
    List<Task> tasks;

    @OneToMany(mappedBy = "creatorUser")
    List<Comment> comments = new ArrayList<>();

    public User() {}

    public User(String fullName, String userName, String password, Role role, AccountType accountType) {
        this.fullName = fullName;
        this.userName = userName;
        this.userPassword = password;
        this.userRole = role.getValue();
        this.accountType = accountType.getValue();
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task taskToAdd) {
        tasks.add(taskToAdd);
    }

    @Override
    public String toString() {
        return "Nombre: " + fullName + ", Usuario: " + userName + ", Rol: " + userRole;
    }

}

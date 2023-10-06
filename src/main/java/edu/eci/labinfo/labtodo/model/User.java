package edu.eci.labinfo.labtodo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Esta es una entidad que representa a un usuario en la base de datos.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String userRole;
    private String userEmail;
    private String userPassword;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.REMOVE)
    List<Task> tasks;

    public User(String name, String password, Role role, String email) {
        this.userName = name;
        this.userPassword = password;
        this.userRole = role.getValue();
        this.userEmail = email;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
        return "Usuario: " + userName + ", Rol: " + userRole + ", Email: " + userEmail;
    }

    
    
}

package edu.eci.labinfo.labtodo.bean;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.labinfo.labtodo.model.Task;
import edu.eci.labinfo.labtodo.service.TaskService;

@ManagedBean
@Component
@ApplicationScoped
public class TaskBean {

    @Autowired
    TaskService taskService;

    private List<Task> tasks;
    private List<Task> filteredTasks;

    @PostConstruct
    public void init() {
        this.tasks = taskService.getAllITask();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getFilteredTasks() {
        return filteredTasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setFilteredTasks(List<Task> filteredTasks) {
        this.filteredTasks = filteredTasks;
    }
}

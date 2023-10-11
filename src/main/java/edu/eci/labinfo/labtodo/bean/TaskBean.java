package edu.eci.labinfo.labtodo.bean;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
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
    private Task currentTask;

    @PostConstruct
    public void init() {
        this.tasks = taskService.getAllITask();
    }

    public void openNew() {
        this.currentTask = new Task();
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

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * Method that saves the currently selected Task object to the database. 
     * If the task already exists in the database, it updates the existing task. Otherwise, it creates a new task.
     * If the operation is successful, a success message is displayed to the user via the FacesContext object.
     * If the operation fails, an error message is displayed.
     */
    public void saveTask() {
        FacesContext context = FacesContext.getCurrentInstance();
        String message = "";
        if (this.currentTask.getTaskId() == null) {
            taskService.addTask(currentTask);
            tasks.add(currentTask);
            message = "Tarea creada con exito";   
        }else{
            if(taskService.updateTask(currentTask) != null){
                message = "Tarea actualizada con exito";       
            }else{
                message = "Error al actualizar";     
            }
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));   
        PrimeFaces.current().ajax().update("form:growl");   
    }
    
}      

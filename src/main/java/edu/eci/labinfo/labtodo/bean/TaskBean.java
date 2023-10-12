package edu.eci.labinfo.labtodo.bean;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.labinfo.labtodo.model.Comment;
import edu.eci.labinfo.labtodo.model.Status;
import edu.eci.labinfo.labtodo.model.Task;
import edu.eci.labinfo.labtodo.model.User;
import edu.eci.labinfo.labtodo.service.CommentService;
import edu.eci.labinfo.labtodo.service.PrimeFacesWrapper;
import edu.eci.labinfo.labtodo.service.TaskService;
import edu.eci.labinfo.labtodo.service.UserService;

@ManagedBean
@Component
@ApplicationScoped
public class TaskBean {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    private static FacesContextWrapper facesContextWrapper;

    @Autowired
    private PrimeFacesWrapper primeFacesWrapper;

    private List<Task> tasks;
    private List<User> selectedUsers;
    private List<Task> filteredTasks;
    private Task currentTask;
    private Comment comment;
    private String commentary;

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

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    /**
     * Metodo que crea una nueva tarea.
     */
    public void openNew() {
        this.currentTask = new Task();
    }

    /**
     * Metodo que crea un nuevo comentario.
     */
    public void openComment() {
        this.comment = new Comment();
    }

    /**
     * Method that saves the currently selected Task object to the database.
     * If the task already exists in the database, it updates the existing task.
     * Otherwise, it creates a new task.
     * If the operation is successful, a success message is displayed to the user
     * via the FacesContext object.
     * If the operation fails, an error message is displayed.
     */
    public void saveTask() {
        String message = "";
        if (this.currentTask.getTaskId() == null) {
            this.currentTask.setUsers(selectedUsers);
            taskService.addTask(currentTask);
            message = "Tarea creada con exito";
        } else {
            if (taskService.updateTask(currentTask) != null) {
                message = "Tarea actualizada con exito";
            } else {
                message = "Error al actualizar";
            }
        }
        selectedUsers.clear();
        facesContextWrapper.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
        primeFacesWrapper.current().ajax().update("form:growl");
    }

    /**
     * Metodo que avisa al usuario que la tarea ha sido completada.
     */
    public void completedMessage() {
        String summary = Status.FINISH.getValue();
        if (this.currentTask != null) {
            this.currentTask.setStatus(summary);
            taskService.updateTask(this.currentTask);
            facesContextWrapper.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
            primeFacesWrapper.current().ajax().update("form:growl", "form:dt-task");
        }
    }

    public void changeLoggedTaskView() {
        primeFacesWrapper.current().ajax().update("form:growl", "form:dt-task");
    }

    /**
     * Metodo que carga las tareas de la base de datos para un usuario especifico.
     * 
     * @param userName nombre del usuario que se va a cargar sus tareas.
     */
    public void onDatabaseLoaded(String userName) {
        User user = userService.getUserByUsername(userName);
        this.tasks = taskService.getTasksByUser(user);
    }

    /**
     * Metodo que carga las tareas de la base de datos cuando se va a realizar un
     * control de tareas.
     */
    public void onControlLoaded() {
        this.tasks = taskService.getAllTask();
    }

    /**
     * Metodo que obtiene los comentarios de la tarea actual.
     * 
     * @return lista de comentarios de la tarea actual.
     */
    public List<Comment> getCurrentTaskComments() {
        return commentService.getComentsByTask(this.currentTask);
    }

    /**
     * Metodo que guarda un comentario en la base de datos
     * 
     * @param userName nombre del usuario que realiza el comentario
     */
    public void saveComment(String userName) {
        User userOpinion = userService.getUserByUsername(userName);
        this.comment.setDescription(commentary);
        this.comment.setTask(currentTask);
        this.comment.setCreatorUser(userOpinion);
        commentService.addComment(comment);
        commentary = "";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Comentario agregado"));
        primeFacesWrapper.current().executeScript("PF('manageCommentDialog').hide()");
        primeFacesWrapper.current().ajax().update("form:messages", "form:comments-list");
    }

}

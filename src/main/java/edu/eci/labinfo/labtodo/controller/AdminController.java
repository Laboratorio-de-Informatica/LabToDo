package edu.eci.labinfo.labtodo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.eci.labinfo.labtodo.model.AccountType;
import edu.eci.labinfo.labtodo.model.LabToDoExeption;
import edu.eci.labinfo.labtodo.model.Role;
import edu.eci.labinfo.labtodo.model.Status;
import edu.eci.labinfo.labtodo.model.Task;
import edu.eci.labinfo.labtodo.model.TypeTask;
import edu.eci.labinfo.labtodo.model.User;
import edu.eci.labinfo.labtodo.service.PrimeFacesWrapper;
import edu.eci.labinfo.labtodo.service.TaskService;
import edu.eci.labinfo.labtodo.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;

@Component
@SessionScoped
@Data
public class AdminController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    PrimeFacesWrapper primeFacesWrapper;

    private List<Task> selectedTasks;
    private List<User> selectedUsers;
    private String newState = "";
    private String newRole = "";
    private String newAccountType = "";

    /**
     * Metodo que cambia el estado de las tareas seleccionadas.
     * 
     * @return true si se cambio el estado de las tareas, false de lo contrario.
     */
    public Boolean modifyStateTaks() {
        if (this.newState == null || this.newState.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    LabToDoExeption.NO_STATE_SELECTED, "Error"));
            primeFacesWrapper.current().ajax().update(":form:messages");
            return false;
        }
        try {
            for (Task task : selectedTasks) {
                if (this.newState.equals(Status.FINISH.getValue()) && task.getTypeTask().equals(TypeTask.LABORATORIO.getValue())) {
                    task.setUsers(taskService.getUsersWhoCommentedTask(task.getTaskId()));
                }
                task.setStatus(this.newState);
                taskService.updateTask(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            int size = this.selectedTasks.size();
            String summary = size > 1 ? size + " tareas actualizadas con éxito" : size + " tarea actualizada con éxito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, "Éxito"));
            primeFacesWrapper.current().ajax().update(":form:dt-task", ":form:messages");
            selectedTasks.clear();
        }
        return true;
    }

    /**
     * Metodo que cambia el rol de los usuarios seleccionados.
     * 
     * @return true si se cambio el rol de los usuarios, false de lo contrario.
     */
    public Boolean modifyUserRole() {
        if (this.newRole == null || this.newRole.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    LabToDoExeption.NO_ROLE_SELECTED, "Error"));
            primeFacesWrapper.current().ajax().update("form:messages");
            return false;
        }
        try {
            for (User user : selectedUsers) {
                user.setRole(Role.findByValue(newRole).getValue());
                userService.updateUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            int size = this.selectedUsers.size();
            String summary = size > 1 ? size + " usuarios actualizados con éxito" : size + " usuario actualizado con éxito";
            selectedUsers.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, "Éxito"));
            primeFacesWrapper.current().ajax().update("form:users-list", "form:messages", ":role-label", "form:account-users-button", "form:edit-users-button");
        }
        return true;
    }

    public Boolean modifyUserAccountType() {
        if (this.newAccountType == null || this.newAccountType.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    LabToDoExeption.NO_ACCOUNT_TYPE_SELECTED, "Error"));
            primeFacesWrapper.current().ajax().update("form:messages");
            return false;
        }
        try {
            for (User user : selectedUsers) {
                user.setAccountType(AccountType.findByValue(newAccountType).getValue());
                userService.updateUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            int size = this.selectedUsers.size();
            String summary = size > 1 ? size + " usuarios actualizados con éxito" : size + " usuario actualizado con éxito";
            selectedUsers.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, "Éxito"));
            primeFacesWrapper.current().ajax().update("form:users-list", "form:messages", "form:account-users-button", "form:edit-users-button");
        }
        return true;
    }

    /**
     * Metodo que retorna el mensaje que se muestra en el boton de actualizar
     * para cambiar el estado de las tareas seleccionadas.
     * 
     * @return mensaje que se muestra en el boton de actualizar.
     */
    public String getUpdateButtonMessage() {
        String message = "Cambiar estado de";
        if (hasSelectedTasks()) {
            int size = this.selectedTasks.size();
            return size > 1 ? message + " " + size + " tareas seleccionadas" : message + " 1 tarea seleccionada";
        }
        return message;
    }

    /**
     * Metodo que retorna el mensaje que se muestra en el boton de cambio de rol
     * para cambiar el rol de los usuarios seleccionados.
     * 
     * @return mensaje que se muestra en el boton de cambio de rol.
     */
    public String getRoleButtonMessage() {
        String message = "Cambiar rol de";
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? message + " " + size + " usuarios seleccionados" : message + " 1 usuario seleccionado";
        }
        return message;
    }

    /**
     * Metodo que retorna el mensaje que se muestra en el boton de verificacion
     * para verificar las cuentas de los usuarios seleccionados.
     * @return mensaje que se muestra en el boton de verificacion.
     */
    public String getaccountButtonMessage() {
        String message = "Estado de cuenta de ";
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? message + " " + size + " usuarios seleccionados" : message + " 1 usuario seleccionado";
        }
        return message;
    }

    /**
     * Metodo que retorna true si hay tareas seleccionadas, false de lo contrario.
     * 
     * @return true si hay tareas seleccionadas, false de lo contrario.
     */
    public boolean hasSelectedTasks() {
        return this.selectedTasks != null && !this.selectedTasks.isEmpty();
    }

    /**
     * metodo que retorna true si hay usuarios seleccionados, false de lo contrario.
     * 
     * @return true si hay usuarios seleccionados, false de lo contrario.
     */
    public boolean hasSelectedUsers() {
        return this.selectedUsers != null && !this.selectedUsers.isEmpty();
    }

}


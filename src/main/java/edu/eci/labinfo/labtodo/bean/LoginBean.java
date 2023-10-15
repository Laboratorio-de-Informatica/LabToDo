package edu.eci.labinfo.labtodo.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContextWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.labinfo.labtodo.model.Role;
import edu.eci.labinfo.labtodo.model.User;
import edu.eci.labinfo.labtodo.service.PrimeFacesWrapper;
import edu.eci.labinfo.labtodo.service.UserService;

@Component
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    private static final String LOGIN_FORM_MESSAGES = "login-form:messages";
    private static final String ERROR = "Error";

    @Autowired
    private UserService userService;

    @Autowired
    private static TaskBean taskBean;

    @Autowired
    private static FacesContextWrapper facesContextWrapper;

    @Autowired
    private PrimeFacesWrapper primeFacesWrapper;

    private String userName;
    private String fullName;
    private String password;
    private User newUser;
    private List<String> users;

    public LoginBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public List<String> getUsers() {
        List<String> fullNameusers = new ArrayList<String>();
        userService.getUsers().forEach(user -> fullNameusers.add(user.getFullName()));
        return fullNameusers;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    /**
     * Crea una nueva cuenta de usuario
     */
    public void createUserAccount() {
        this.newUser = new User();
    }

    /**
     * Obtiene el nombre del usuario actual extraido de la base de datos.
     * 
     * @param userName el nombre del usuario a obtener.
     * @return el nombre del usuario actual.
     */
    public String getCurrentUserName(String userName) {
        return userService.getUserByUsername(userName).getUserName();
    }

    /**
     * Obtiene el nombre completo del usuario actual extraido de la base de datos.
     * 
     * @param userName el nombre del usuario a obtener.
     * @return el nombre completo del usuario actual.
     */
    public String getCurrentFullName(String userName) {
        return userService.getUserByUsername(userName).getFullName();
    }

    /**
     * Obtiene el rol del usuario actual extraido de la base de datos.
     * 
     * @param userName el nombre del usuario a obtener.
     * @return el rol del usuario actual.
     */
    public String getCurrentUserProfile(String userName) {
        return userService.getUserByUsername(userName).getUserRole();
    }

    /**
     * Funcion que crea una nueva cuenta de usuario
     * 
     * @return True si se crea la cuenta, de lo contrario False
     */
    public Boolean createAccount() {
        String userName = this.newUser.getUserName();
        try {
            if (userService.getUserByUsername(userName) != null) {
                facesContextWrapper.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "La cuenta ya existe", ERROR));
                primeFacesWrapper.current().ajax().update(LOGIN_FORM_MESSAGES);
                return false;
            }
            this.newUser.setUserRole(Role.MONITOR.getValue());
            userService.addUser(this.newUser);

            facesContextWrapper.getCurrentInstance().addMessage(null, new FacesMessage("Cuenta creada exitosamente"));
            primeFacesWrapper.current().ajax().update(LOGIN_FORM_MESSAGES);
            primeFacesWrapper.current().executeScript("PF('createAccountDialog').hide()");

        } catch (Exception e) {
            facesContextWrapper.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Se produjo un error al crear la cuenta", ERROR));
            primeFacesWrapper.current().ajax().update(LOGIN_FORM_MESSAGES);
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Función que permite el inicio de sesión
     * 
     * @return True si el inicio de sesión es exitoso, de lo contrario False
     */
    public Boolean login() {
        // Verificar que se ingresó un nombre de usuario y una contraseña
        if (password == null || userName == null) {
            facesContextWrapper.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor complete todos los campos", ERROR));
            primeFacesWrapper.current().ajax().update(LOGIN_FORM_MESSAGES);
            return false;
        }
        // Buscar al usuario por correo electrónico
        User userToLogin = userService.getUserByUsername(userName);
        // Si el usuario no existe o la contraseña es incorrecta, mostrar un mensaje de
        // error y salir temprano
        if (userToLogin == null || !password.equals(userToLogin.getUserPassword())) {
            facesContextWrapper.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su cuenta o contraseña no es correcta.", ERROR));
            primeFacesWrapper.current().ajax().update(LOGIN_FORM_MESSAGES);
            return false;
        }
        // Si el usuario está autenticado, redirigirlo a la página correspondiente
        try {
            password = null;
            ExternalContext ec = facesContextWrapper.getCurrentInstance().getExternalContext();
            String redirectPath = "../public/dashboard.xhtml";
            ec.redirect(ec.getRequestContextPath() + redirectPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Función que permite el cierre de sesión
     * 
     * @return True si el cierre de sesión es exitoso, de lo contrario False
     */
    public Boolean logout() {
        userName = null;
        try {
            ExternalContext ec = facesContextWrapper.getCurrentInstance().getExternalContext();
            String redirectPath = "../public/login.xhtml";
            ec.redirect(ec.getRequestContextPath() + redirectPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Función que redirige a la página correspondiente según el rol del usuario
     * 
     * @param user el usuario que se está autenticando
     * @return la ruta de la página a la que se debe redirigir
     */
    public Boolean getRedirectPath(String userName, String sendTo) {
        User user = userService.getUserByUsername(userName);
        ExternalContext ec = facesContextWrapper.getCurrentInstance().getExternalContext();
        String redirectPath = "";
        switch (sendTo) {
            case "control":
                redirectPath = "../public/admindashboard.xhtml";
                break;
            case "config":
                redirectPath = "../public/settings.xhtml";
                break;
            default:
                redirectPath = "../public/dashboard.xhtml";
                break;
        }
        try {
            if (user.getUserRole().equals(Role.ADMINISTRADOR.getValue())) {
                ec.redirect(ec.getRequestContextPath() + redirectPath);
                return true;
            } else {
                ec.redirect(ec.getRequestContextPath() + redirectPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Función que verifica si el usuario es administrador.
     * 
     * @param userName el nombre del usuario a verificar.
     * @return True si el usuario es administrador, de lo contrario False.
     */
    public boolean isAdmin(String userName) {
        boolean isAdminUser = false;
        User user = userService.getUserByUsername(userName);
        if (user.getUserRole().equals(Role.ADMINISTRADOR.getValue())) {
            isAdminUser = true;
        }
        return isAdminUser;
    }

}

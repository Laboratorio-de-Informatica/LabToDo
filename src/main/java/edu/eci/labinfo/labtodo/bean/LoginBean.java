package edu.eci.labinfo.labtodo.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private FacesContextWrapper facesContextWrapper;

    @Autowired
    private PrimeFacesWrapper primeFacesWrapper;

    private String userName;
    private String password;
    private String email;
    private User newUser;

    public LoginBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    /**
     * Create a new user account
     */
    public void createUserAccount() {
        this.newUser = new User();
    }

    /**
     * Function that validates if the email meets validation requirements
     * 
     * @param email entered by user current
     * @return True if it matches special characters otherwise False
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Function that creates a user for the platform
     * 
     * @return True if it complies with security validations otherwise False
     */
    public Boolean createAccount() {
        String userEmail = this.newUser.getUserEmail();
        try {
            if (!isValidEmail(userEmail)) {
                facesContextWrapper.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo electrónico no válido", ERROR));
                primeFacesWrapper.current().ajax().update(LOGIN_FORM_MESSAGES);
                return false;
            }

            if (userService.getUserByEmail(userEmail) != null) {
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

}

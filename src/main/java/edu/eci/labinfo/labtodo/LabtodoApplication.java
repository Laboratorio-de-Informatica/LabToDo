package edu.eci.labinfo.labtodo;

import java.util.Arrays;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import edu.eci.labinfo.labtodo.model.Comment;
import edu.eci.labinfo.labtodo.model.Role;
import edu.eci.labinfo.labtodo.model.Task;
import edu.eci.labinfo.labtodo.model.TypeTask;
import edu.eci.labinfo.labtodo.model.User;
import edu.eci.labinfo.labtodo.service.CommentService;
import edu.eci.labinfo.labtodo.service.TaskService;
import edu.eci.labinfo.labtodo.service.UserService;

@SpringBootApplication
public class LabtodoApplication {

    @Autowired
    UserService myUserService;

    @Autowired
    TaskService myTaskService;

    @Autowired
    CommentService myCommentService;

    public static void main(String[] args) {
        SpringApplication.run(LabtodoApplication.class, args);
    }

    @Bean(name = "database")
    public CommandLineRunner run() {
        return args -> {
            // Deleting tables's content...
            myCommentService.deleteAllComments();
            myTaskService.deleteAllTasks();
            myUserService.deleteAllUsers();

            myUserService.addUser(new User("andres.onate", "mypasswd", Role.MONITOR, "andres.onate@mail.escuelaing.edu.co"));
            myUserService.addUser(new User("labinfo", "myadminpasswd", Role.ADMINISTRADOR, "labinfo@escuelaing.edu.co"));
        };
    }

    @Bean
    @DependsOn({ "database" })
    ServletRegistrationBean<FacesServlet> jsfServletRegistration(ServletContext servletContext) {
        // spring boot only works if this is set
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

        // registration
        ServletRegistrationBean<FacesServlet> srb = new ServletRegistrationBean<>();
        srb.setServlet(new FacesServlet());
        srb.setUrlMappings(Arrays.asList("*.xhtml"));
        srb.setLoadOnStartup(1);

        return srb;
    }

}

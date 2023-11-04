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

import edu.eci.labinfo.labtodo.model.AccountType;
import edu.eci.labinfo.labtodo.model.Role;
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

            myUserService
                    .addUser(new User("Andrés Camilo Oñate", "andres.onate", "mypasswd", Role.MONITOR, AccountType.SIN_VERIFICAR));
            myUserService
                    .addUser(new User("Aurora León", "labinfo", "myadminpasswd", Role.ADMINISTRADOR, AccountType.ACTIVO));
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

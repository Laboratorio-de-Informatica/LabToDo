package edu.eci.labinfo.labtodo;

import java.util.Arrays;
import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import edu.eci.labinfo.labtodo.model.Role;
import edu.eci.labinfo.labtodo.model.Task;
import edu.eci.labinfo.labtodo.model.User;
import edu.eci.labinfo.labtodo.service.TaskService;
import edu.eci.labinfo.labtodo.service.UserService;

@SpringBootApplication
public class Main {

	@Autowired
	UserService myUserService;

	@Autowired
	TaskService myTaskService;


	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@Bean(name = "database")
	public CommandLineRunner run() {
		return args -> {
			//Deleting tables's content...
            myTaskService.deleteAllTasks();

			myUserService.deleteAllUsers();

			//Adding User...
			User user = new User("monitor", "mypasswd", Role.MONITOR, "andres.onate@mail.escuelaing.edu.co");

			myUserService.addUser(user);

			//Adding Task...
            Task task1 = new Task("Tarea de prueba 1", "Prueba1");
            Task task2 = new Task("Tarea de prueba 2", "Prueba2");

            myTaskService.addTask(task1);
            myTaskService.addTask(task2);
            user.addTask(task1);
            user.addTask(task2);
            myUserService.updateUser(user);
			task1.setUser(user);
			myTaskService.updateTask(task1);
			task2.setUser(user);
			myTaskService.updateTask(task2);
		};
    }

	@Bean
	@DependsOn({ "database" })
	ServletRegistrationBean<Servlet> jsfServletRegistration(ServletContext servletContext) {
		// spring boot only works if this is set
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

		// registration
		ServletRegistrationBean<Servlet> srb = new ServletRegistrationBean<>();
		srb.setServlet(new FacesServlet());
		srb.setUrlMappings(Arrays.asList("*.xhtml"));
		srb.setLoadOnStartup(1);
		return srb;
	}
}
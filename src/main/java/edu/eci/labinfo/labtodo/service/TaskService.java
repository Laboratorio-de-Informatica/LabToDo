package edu.eci.labinfo.labtodo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.labinfo.labtodo.data.TaskRepository;
import edu.eci.labinfo.labtodo.model.Task;
import edu.eci.labinfo.labtodo.model.User;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUsersUserId(user.getUserId());
    }

    public List<Task> getTasksByUserAndStatus(User user, String taskStatus) {
        return taskRepository.findByUserIdAndStatus(user.getUserId(), taskStatus);
    }

    public List<Task> getTaskByTypeAndStatus(String typeTask, String taskStatus) {
        return taskRepository.findByTypeAndStatus(typeTask, taskStatus);
    }

    public List<Task> getTasksByStatus(String taskStatus) {
        return taskRepository.findByStatus(taskStatus);
    }

    public List<Task> getTaskByType(String taskType){
        return  taskRepository.findByTypeTask(taskType);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public List<User> getUsersWhoCommentedTask(Long taskId) {
        return taskRepository.findUsersWhoCommented(taskId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Task updateTask(Task task) {
        if (taskRepository.existsById(task.getTaskId())) {
            return taskRepository.save(task);
        }
        return null;
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

}

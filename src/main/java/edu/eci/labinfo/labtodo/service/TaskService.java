package edu.eci.labinfo.labtodo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.labinfo.labtodo.data.TaskRepository;
import edu.eci.labinfo.labtodo.model.Task;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task){
        return taskRepository.save(task);
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    public List<Task> getAllITask() {
        return taskRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Task updateTask(Task task) {
        if (taskRepository.existsById(task.getId())) {
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

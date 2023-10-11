package edu.eci.labinfo.labtodo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.labinfo.labtodo.data.UserRepository;
import edu.eci.labinfo.labtodo.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            return userRepository.findByUserName(username).get();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        if (userRepository.findByUserEmail(email).isPresent()) {
            return userRepository.findByUserEmail(email).get();
        }
        return null;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(String email) {
        userRepository.delete(getUserByEmail(email));
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}

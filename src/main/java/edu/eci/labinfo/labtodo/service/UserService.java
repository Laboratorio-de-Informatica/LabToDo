package edu.eci.labinfo.labtodo.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.labinfo.labtodo.data.UserRepository;
import edu.eci.labinfo.labtodo.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            return userRepository.findByUserName(username).get();
        }
        return null;
    }

    public User getUserByFullName(String fullName) {
        if (userRepository.findByFullName(fullName).isPresent()) {
            return userRepository.findByFullName(fullName).get();
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

    public void deleteUser(String userName) {
        userRepository.delete(getUserByUsername(userName));
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}

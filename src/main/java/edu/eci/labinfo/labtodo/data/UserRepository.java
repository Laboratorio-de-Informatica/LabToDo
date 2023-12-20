package edu.eci.labinfo.labtodo.data;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.eci.labinfo.labtodo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByFullName(String fullName);
    
}

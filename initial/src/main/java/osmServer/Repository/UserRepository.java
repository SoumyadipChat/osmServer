package osmServer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osmServer.Model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByEmail(String email);
    List<User> findByUsername(String username);
}

package bookshop.repository.user;

import bookshop.model.User;
import jakarta.validation.constraints.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(@Email String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(@Email String email);
}

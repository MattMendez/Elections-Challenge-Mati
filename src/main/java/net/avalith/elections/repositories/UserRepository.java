package net.avalith.elections.repositories;

import net.avalith.elections.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(
            value = "SELECT * FROM user u WHERE u.is_fake = 1",
            nativeQuery = true)
    List<User> findFakeUsers();
}

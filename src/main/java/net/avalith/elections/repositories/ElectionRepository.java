package net.avalith.elections.repositories;

import net.avalith.elections.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

    @Query(
            value = "SELECT * FROM election WHERE ?1 BETWEEN start_date AND end_date",
            nativeQuery = true)
    List<Election> findInProgressElections(LocalDateTime localDateTime);
}

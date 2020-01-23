package net.avalith.elections.repositories;

import net.avalith.elections.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(value = "SELECT * FROM vote WHERE user_id = :userid AND election_id = :electionid",nativeQuery = true)
    Optional<Vote> findByUserAndElection(@Param("userid") String userid, @Param("electionid") Integer electionid);
}

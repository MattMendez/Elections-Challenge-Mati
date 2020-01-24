package net.avalith.elections.repositories;

import net.avalith.elections.models.ElectionsCandidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionsCandidatesRepository extends JpaRepository<ElectionsCandidates, Integer> {
}

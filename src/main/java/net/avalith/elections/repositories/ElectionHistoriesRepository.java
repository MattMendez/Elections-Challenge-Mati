package net.avalith.elections.repositories;

import net.avalith.elections.models.ElectionHistories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionHistoriesRepository extends JpaRepository<ElectionHistories, Integer> {
}

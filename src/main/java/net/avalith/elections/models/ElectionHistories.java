package net.avalith.elections.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalith.elections.entities.CandidateWithVotes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ElectionHistories {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer electionId;

    private String fullName;

    private Long votes;

    private Float votePercentage;

    private LocalDateTime localDateTime;
}

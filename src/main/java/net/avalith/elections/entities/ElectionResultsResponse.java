package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ElectionResultsResponse {

    @JsonProperty("id_election")
    private Integer id;

    @JsonProperty("candidates")
    private List<CandidateWithVotes> candidateWithVotes;

    @JsonProperty("total_votes")
    private  Long totalVotes;
}

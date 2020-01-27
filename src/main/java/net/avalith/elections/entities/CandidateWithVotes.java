package net.avalith.elections.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateWithVotes {

    private Integer id;

    private String first_name;

    private String last_name;

    private  Long votes;
}

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

    private String name;

    private String lastname;

    private  Long votes;
}

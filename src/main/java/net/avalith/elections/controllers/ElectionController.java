package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyElections;
import net.avalith.elections.entities.BodyFakeVote;
import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.entities.ElectionResponse;
import net.avalith.elections.entities.ElectionResultsResponse;
import net.avalith.elections.entities.FakeUserResponse;
import net.avalith.elections.entities.VoteResponse;
import net.avalith.elections.services.ElectionService;
import net.avalith.elections.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private VoteService voteService;

    @PostMapping("")
    public ElectionResponse electionResponse(@Valid @RequestBody BodyElections bodyElections){

        return electionService.addElection(bodyElections);
    }

    @PostMapping("/{id_election}/vote")
    public VoteResponse voteResponse(@PathVariable(name = "id_election") Integer electionid,
                                     @RequestHeader("USER_ID") String userid,
                                     @Valid @RequestBody BodyVote bodyVote){

        return voteService.addVote(electionid,userid,bodyVote);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT,
            reason = "Inserting a vote that already exist")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
    }

    @GetMapping("/{id_election}")
    public ElectionResultsResponse getResults(@PathVariable("id_election") Integer id){

        return electionService.getResults(id);
    }

    @PostMapping("/fake/{id_election}")
    public FakeUserResponse createFakeVotes(@PathVariable(name = "id_election") Integer electionid,
                                         @RequestBody BodyFakeVote bodyFakeVote){

        return  voteService.addFakeVotes(electionid,bodyFakeVote);
    }
}

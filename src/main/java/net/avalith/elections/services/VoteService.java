package net.avalith.elections.services;

import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.entities.VoteResponse;
import net.avalith.elections.models.Vote;
import net.avalith.elections.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CandidateService candidateService;

    public VoteResponse addVote(Integer electionid, String userid, BodyVote bodyVote){
        if (electionService.electionInProgress(electionid) && !voteAlreadyExist(userid,electionid)) {
            Vote vote = Vote.builder()
                    .election(electionService.findById(electionid))
                    .user(userService.findById(userid))
                    .candidate(candidateService.findById(bodyVote.getCandidateid()))
                    .build();
            voteRepository.save(vote);
            return new VoteResponse("Voto ingresado con éxito");
        }else
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "La eleccion numero "+ electionid + " ya finalizo");
    }

    public Vote findById(Integer id){

        return  voteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el voto numero  "+ id));
    }

    public Boolean voteAlreadyExist(String userid, Integer electionid){
        if (voteRepository.findByUserAndElection(userid,electionid).isEmpty())
            return false;
        else
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya voto ");
    }
}
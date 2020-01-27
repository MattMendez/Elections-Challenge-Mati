package net.avalith.elections.services;

import net.avalith.elections.entities.BodyElections;
import net.avalith.elections.entities.CandidateWithVotes;
import net.avalith.elections.entities.ElectionResponse;
import net.avalith.elections.entities.ElectionResultsResponse;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionsCandidates;
import net.avalith.elections.repositories.ElectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ElectionsCandidatesService electionsCandidatesService;

    public ElectionResponse addElection(BodyElections bodyElections){
        if(bodyElections.getEndDate().isBefore(bodyElections.getStarDate()) || bodyElections.getEndDate().isEqual(bodyElections.getStarDate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de fin es previa a la de comienzo");
        else {
            List<Candidate> candidates = bodyElections.getCandidateId().stream()
                    .map(it -> candidateService.findById(it))
                    .collect(Collectors.toList());

            Election election = Election.builder()
                    .startDate(bodyElections.getStarDate())
                    .endDate(bodyElections.getEndDate())
                    .build();

            Election finalElection = electionRepository.save(election);
            candidates.stream().forEach(
                    it -> electionsCandidatesService.addelectionscandidates(ElectionsCandidates.builder().election(finalElection).candidate(it).build()));

            return new ElectionResponse(election.getId());
        }
    }

    public Election findById(Integer id){

        return  electionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro la eleecion numero  "+ id));
    }

    public Boolean electionInProgress(Election election){
        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(election.getEndDate()) && now.isAfter(election.getStartDate());
    }

    public ElectionResultsResponse getResults(Integer id){
        Election election = findById(id);

        List<CandidateWithVotes> candidateWithVotes = election.getElectionsCandidates().stream()
                .map(it -> electionsCandidatesService.buildCandidateWithVotes(it))
                .sorted((candidateWithVotes1, it) ->it.getVotes().compareTo(candidateWithVotes1.getVotes()))
                .collect(Collectors.toList());

        return  ElectionResultsResponse.builder()
                .id(id)
                .candidateWithVotes(candidateWithVotes)
                .totalVotes(candidateWithVotes.stream().mapToLong(
                        it -> it.getVotes()
                ).sum())
                .build();
    }
}

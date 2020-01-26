package net.avalith.elections.services;

import net.avalith.elections.entities.CandidateWithVotes;
import net.avalith.elections.models.ElectionsCandidates;
import net.avalith.elections.repositories.ElectionsCandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionsCandidatesService {

    @Autowired
    private ElectionsCandidatesRepository electionsCandidatesRepository;

    public ElectionsCandidates addelectionscandidates(ElectionsCandidates electionsCandidates){

        return electionsCandidatesRepository.save(electionsCandidates);
    }

    public CandidateWithVotes buildCandidateWithVotes(ElectionsCandidates electionsCandidates){
        CandidateWithVotes candidateWithVotes = CandidateWithVotes.builder()
                .id(electionsCandidates.getCandidate().getId())
                .lastname(electionsCandidates.getCandidate().getLastname())
                .name(electionsCandidates.getCandidate().getName())
                .votes(electionsCandidates.getVotes().stream().filter(
                        it -> it.getElectionsCandidates().getCandidate().getId() == electionsCandidates.getCandidate().getId()).count())
                .build();

        return candidateWithVotes;
    }
}

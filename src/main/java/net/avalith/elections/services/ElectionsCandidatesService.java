package net.avalith.elections.services;

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
}

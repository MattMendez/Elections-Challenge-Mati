package net.avalith.elections.tasks;

import net.avalith.elections.entities.CandidateWithVotes;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionHistories;
import net.avalith.elections.repositories.ElectionHistoriesRepository;
import net.avalith.elections.services.ElectionService;
import net.avalith.elections.services.ElectionsCandidatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class HistoricTask {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private ElectionsCandidatesService electionsCandidatesService;

    @Autowired
    private ElectionHistoriesRepository electionHistoriesRepository;

    @Scheduled(fixedRateString = "${fixedRate}")
    public void addHistoric() {
        List<Election> elections = electionService.findElectionsInProgress();

        List<ElectionHistories> electionHistories = elections.stream().map(
                it -> {
                    CandidateWithVotes ec = electionsCandidatesService.findWinningCandidate(it.getElectionsCandidates());
                    return ElectionHistories.builder()
                            .electionId(it.getId())
                            .localDateTime(LocalDateTime.now())
                            .fullName(ec.getFirstName() + ' ' +ec.getLastName())
                            .votes(ec.getVotes())
                            .votePercentage( (ec.getVotes() * 100 ) / ((float) it.getElectionsCandidates().stream().mapToDouble(
                                    x -> (float) x.getVotes().size())
                                    .sum()))
                            .build();}
        ).collect(Collectors.toList());
        electionHistories.stream().forEach(
                eh -> electionHistoriesRepository.save(eh)
        );
    }
}
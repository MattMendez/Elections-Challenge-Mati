package net.avalith.elections.services;

import net.avalith.elections.entities.CandidateWithVotes;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionsCandidates;
import net.avalith.elections.models.Vote;
import net.avalith.elections.repositories.ElectionsCandidatesRepository;
import net.avalith.elections.utilities.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestElectionsCandidatesService {

    @Autowired
    @MockBean
    private Utilities utilities;

    @Autowired
    ElectionsCandidatesService electionsCandidatesService;

    @Autowired
    @MockBean
    ElectionsCandidatesRepository electionsCandidatesRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addelectionscandidatesTest(){

        Election election = Election.builder()
                .id(1)
                .startDate(LocalDateTime.of(2020,01,31,14,00,00))
                .endDate(LocalDateTime.of(42069,02,28,20,00,00))
                .build();

        Candidate candidate =Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build();

        ElectionsCandidates electionsCandidates = ElectionsCandidates.builder()
                .election(election)
                .candidate(candidate)
                .build();

        ElectionsCandidates electionsCandidatesReturn = ElectionsCandidates.builder()
                .id(1)
                .election(election)
                .candidate(candidate)
                .build();

        Mockito.when(electionsCandidatesRepository.save(electionsCandidates)).thenReturn(electionsCandidatesReturn);

        Assert.assertEquals(electionsCandidatesReturn, electionsCandidatesService.addelectionscandidates(electionsCandidates));
    }

    @Test
    public void buildCandidateWithVotesTest(){
        Election election = Election.builder()
                .id(1)
                .startDate(LocalDateTime.of(2020,01,31,14,00,00))
                .endDate(LocalDateTime.of(42069,02,28,20,00,00))
                .build();

        Candidate candidate =Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build();

        Vote vote = Vote.builder()
                .id(1)
                .electionsCandidates(ElectionsCandidates.builder()
                        .id(1)
                        .candidate(candidate)
                        .election(election)
                        .build())
                .build();

        ElectionsCandidates electionsCandidates = ElectionsCandidates.builder()
                .id(1)
                .candidate(candidate)
                .election(election)
                .votes(List.of(vote))
                .build();

        CandidateWithVotes candidateWithVotes = CandidateWithVotes.builder()
                .id(1)
                .firstName("pepe")
                .lastName("pig")
                .votes(1L)
                .build();

        Assert.assertEquals(candidateWithVotes, electionsCandidatesService.buildCandidateWithVotes(electionsCandidates));
    }

    @Test
    public void findWinningCandidateTest(){
        Election election = Election.builder()
                .id(1)
                .startDate(LocalDateTime.of(2020,01,31,14,00,00))
                .endDate(LocalDateTime.of(42069,02,28,20,00,00))
                .build();

        Candidate candidate =Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build();

        Vote vote = Vote.builder()
                .id(1)
                .electionsCandidates(ElectionsCandidates.builder()
                        .id(1)
                        .candidate(candidate)
                        .election(election)
                        .build())
                .build();

        ElectionsCandidates electionsCandidates = ElectionsCandidates.builder()
                .id(1)
                .candidate(candidate)
                .election(election)
                .votes(List.of(vote))
                .build();

        CandidateWithVotes candidateWithVotes = CandidateWithVotes.builder()
                .id(1)
                .firstName("pepe")
                .lastName("pig")
                .votes(1L)
                .build();

        Assert.assertEquals(candidateWithVotes, electionsCandidatesService.findWinningCandidate(List.of(electionsCandidates)));
    }
}

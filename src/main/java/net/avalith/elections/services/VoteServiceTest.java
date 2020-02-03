package net.avalith.elections.services;

import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.entities.VoteResponse;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionsCandidates;
import net.avalith.elections.models.User;
import net.avalith.elections.models.Vote;
import net.avalith.elections.repositories.VoteRepository;
import net.avalith.elections.utilities.Utilities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
public class VoteServiceTest {

    @Autowired
    @MockBean
    private Utilities utilities;

    @Autowired
    VoteService voteService;

    @Autowired
    @MockBean
    VoteRepository voteRepository;

    @Autowired
    @MockBean
    ElectionService electionService;

    @Autowired
    @MockBean
    UserService userService;


    @Test
    public void addVoteTest(){
        Integer electionId = 1;
        String userId = "e1938076-ec51-4b41-a5af-da58e57b578f";
        BodyVote bodyVote = BodyVote.builder()
                .candidateid(1)
                .build();

        Candidate candidate =Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build();

        Election election = Election.builder()
                .id(1)
                .startDate(LocalDateTime.of(2020,01,31,14,00,00))
                .endDate(LocalDateTime.of(42069,02,28,20,00,00))
                .build();

        ElectionsCandidates electionsCandidates = ElectionsCandidates.builder()
                .id(1)
                .election(election)
                .candidate(candidate)
                .build();

        election.setElectionsCandidates(List.of(electionsCandidates));

        Mockito.when(electionService.findById(electionId)).thenReturn(election);

        User testUser = User.builder()
                .id("e1938076-ec51-4b41-a5af-da58e57b578f")
                .name("pepe")
                .lastName("argento")
                .email("elpepe@argento.com")
                .vote(List.of())
                .build();

        Mockito.when(userService.findById(userId)).thenReturn(testUser);
        Mockito.when(electionService.electionInProgress(election)).thenReturn(true);

        Vote vote = Vote.builder()
                .electionsCandidates(electionsCandidates)
                .user(testUser)
                .build();

        Vote voteReturn = Vote.builder()
                .id(1)
                .electionsCandidates(electionsCandidates)
                .user(testUser)
                .build();

        Mockito.when(voteRepository.save(vote)).thenReturn(voteReturn);

        Assert.assertEquals(new VoteResponse("Voto ingresado con éxito"), voteService.addVote(electionId, userId, bodyVote));
    }

    @Test
    public void findByIdTest(){
        Integer id =1;

        Vote voteReturn = Vote.builder()
                .id(1)
                .build();

        Mockito.when(voteRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(voteReturn));

        Assert.assertEquals(voteReturn, voteService.findById(id));
    }

    @Test
    public void didNotVoteTest(){
        Integer electionId=1;

        User testUser = User.builder()
                .id("e1938076-ec51-4b41-a5af-da58e57b578f")
                .name("pepe")
                .lastName("argento")
                .email("elpepe@argento.com")
                .vote(List.of())
                .build();

        Assert.assertEquals(true, voteService.didNotVote(electionId,testUser));
    }

    @Test
    public void getTotalVotesTest(){
        Double resp = 1D;
        Vote vote = Vote.builder()
                .id(1)
                .build();

        ElectionsCandidates electionsCandidates = ElectionsCandidates.builder()
                .votes(List.of(vote))
                .build();

        List<ElectionsCandidates> electionsCandidatesList = List.of(electionsCandidates);

        Assert.assertEquals(resp, voteService.getTotalVotes(electionsCandidatesList));
    }

    @Test
    public void addFakeVotesTest(){
        Integer electionId = 1;
        Candidate candidate =Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build();

        Election election = Election.builder()
                .id(1)
                .startDate(LocalDateTime.of(2020,01,31,14,00,00))
                .endDate(LocalDateTime.of(42069,02,28,20,00,00))
                .build();

        ElectionsCandidates electionsCandidates = ElectionsCandidates.builder()
                .id(1)
                .election(election)
                .candidate(candidate)
                .build();

        election.setElectionsCandidates(List.of(electionsCandidates));

        Mockito.when(electionService.findById(electionId)).thenReturn(election);
        Mockito.when(electionService.electionInProgress(election)).thenReturn(true);



    }
}



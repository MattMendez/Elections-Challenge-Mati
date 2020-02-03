package net.avalith.elections.services;

import net.avalith.elections.entities.CandidateIdResponse;
import net.avalith.elections.entities.CandidateInfoResponse;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.repositories.CandidateRepository;
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

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestCandidateService {

    @Autowired
    CandidateService candidateService;

    @Autowired
    @MockBean
    CandidateRepository candidateRepository;

    @Autowired
    @MockBean
    Utilities utilities;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addResponseTest(){
        Candidate testCandidate = Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .build();

        Candidate testCandidateReturn = Candidate.builder()
                .id(1)
                .name("pepe")
                .lastName("pig")
                .build();

        CandidateIdResponse expected = new CandidateIdResponse(testCandidateReturn.getId());

        Mockito.when(candidateRepository.save(testCandidate)).thenReturn(testCandidateReturn);

        Assert.assertEquals(expected,candidateService.addResponse(testCandidate));
    }

    @Test
    public void findByIdTest(){

        Optional<Candidate> testCandidate = Optional.ofNullable(Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build());

        Integer id = 1;

        Mockito.when(candidateRepository.findById(id)).thenReturn(testCandidate);

        Assert.assertEquals(testCandidate,candidateRepository.findById(id));
    }

    @Test
    public void showCandidate(){
        Integer id = 1;
        Optional<Candidate> testCandidate = Optional.ofNullable(Candidate.builder()
                .name("pepe")
                .lastName("pig")
                .id(1)
                .build());

        CandidateInfoResponse testReponse = CandidateInfoResponse.builder()
                .id(1)
                .firstname("pepe")
                .lastname("pig")
                .build();

        Mockito.when(candidateRepository.findById(id)).thenReturn(testCandidate);

        Assert.assertEquals(testReponse, candidateService.showCandidate(id));
    }
}


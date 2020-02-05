package net.avalith.elections.controllers;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import net.avalith.elections.entities.CandidateIdResponse;
import net.avalith.elections.entities.CandidateInfoResponse;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("")
    public CandidateIdResponse addCandidate(@Valid @RequestBody Candidate candidate){

        return candidateService.addResponse(candidate);
    }

    @GetMapping("")
    public CandidateInfoResponse getCandidate(@RequestParam Integer id){

        return  candidateService.showCandidate(id);
    }
}

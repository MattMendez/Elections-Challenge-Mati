package net.avalith.elections.controllers;

import net.avalith.elections.entities.CandidateResponse;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @PostMapping("")
    public CandidateResponse addCandidate(@Valid @RequestBody Candidate candidate){

        return candidateService.addResponse(candidate);
    }
}

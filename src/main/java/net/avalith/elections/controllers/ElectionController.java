package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyElections;
import net.avalith.elections.entities.ElectionResponse;
import net.avalith.elections.models.Election;
import net.avalith.elections.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    ElectionService electionService;

    @PostMapping("")
    public ElectionResponse electionResponse(@Valid @RequestBody BodyElections bodyElections){

        return electionService.addElection(bodyElections);
    }
}

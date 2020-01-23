package net.avalith.elections.controllers;

import net.avalith.elections.entities.UserAddResponse;
import net.avalith.elections.models.User;
import net.avalith.elections.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public UserAddResponse AddUser(@Valid @RequestBody User user) {
        
        return userService.addUser(user);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT,
            reason = "Data integrity violation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
    }
}

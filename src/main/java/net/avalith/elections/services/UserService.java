package net.avalith.elections.services;

import net.avalith.elections.entities.UserAddResponse;
import net.avalith.elections.models.User;
import net.avalith.elections.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserAddResponse addUser(User user){
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        
        return new UserAddResponse(user.getId());
    }

    public User findById(String id){

        return  userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+ id));
    }
}

package net.avalith.elections.services;

import net.avalith.elections.configurations.Config;
import net.avalith.elections.entities.FakeUser;
import net.avalith.elections.entities.FakeUserResponse;
import net.avalith.elections.entities.UserAddResponse;
import net.avalith.elections.models.User;
import net.avalith.elections.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Value("${fakeUserUrl}")
    private String fakeUserUrl;

    @Autowired
    private RestTemplate restTemplate;

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

    public FakeUserResponse addFakeUsers(Integer quantity){
        FakeUser fakeUser = restTemplate.getForObject(fakeUserUrl + "?results=" + quantity, FakeUser.class);
        fakeUser.getFakeUserResults().parallelStream().forEach(
                fake -> userRepository.save( User.builder()
                        .id(UUID.randomUUID().toString())
                        .email(fake.getEmail())
                        .name(fake.getFakeUserName().getFirstName())
                        .lastName(fake.getFakeUserName().getLastName())
                        .isFake(true)
                        .build())
        );
        return new FakeUserResponse(quantity + " Usuarios creados correctamente");
    }

    public List<User> findAllFakeUsers(){

        return userRepository.findFakeUsers();
    }
}

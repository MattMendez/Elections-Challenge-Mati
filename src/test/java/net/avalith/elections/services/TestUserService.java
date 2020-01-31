package net.avalith.elections.tests;

import net.avalith.elections.entities.FakeUser;
import net.avalith.elections.entities.FakeUserLogin;
import net.avalith.elections.entities.FakeUserName;
import net.avalith.elections.entities.FakeUserResponse;
import net.avalith.elections.entities.FakeUserResults;
import net.avalith.elections.entities.UserAddResponse;
import net.avalith.elections.models.User;
import net.avalith.elections.repositories.UserRepository;
import net.avalith.elections.services.UserService;
import net.avalith.elections.utilities.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestUserService {

    @InjectMocks
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RestTemplate restTemplate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUserTest(){

        //Primero hacer los datos que van a existir o va a encontrar, o se le van a pasar
        User testUser = User.builder()
                .name("pepe")
                .lastName("argento")
                .email("elpepe@argento.com")
                .build();

        String id = "e1938076-ec51-4b41-a5af-da58e57b578f";


        //Test
        //Cuando haga algo, va a recibir lo otro, simula el proceso
        Mockito.when(Utilities.getRandomUuid()).thenReturn(id);

        Mockito.when(userRepository.save(testUser)).thenReturn(testUser);
        //Creo la respuesta, llamando a el metodo que me la deberia dar y es el metodo que estoy probando
        UserAddResponse userAddResponse = userService.addUser(testUser);
        //analizo la respuesta que quiero, con la que obtengo
        Assert.assertEquals(id, userAddResponse.getId() );
    }

    @Test
    public void findByIdTest(){

        User testUser = User.builder()
                .id("e1938076-ec51-4b41-a5af-da58e57b578f")
                .name("pepe")
                .lastName("argento")
                .email("elpepe@argento.com")
                .build();

        String id = "e1938076-ec51-4b41-a5af-da58e57b578f";

        Mockito.when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(testUser));

        User user = userService.findById(id);
        Assert.assertEquals(testUser, user);
    }


    @Test
    public void addFakeUsersTest(){

        List<FakeUserResults> fakeUserResults = List.of(FakeUserResults.builder()
                .fakeUserName(FakeUserName.builder()
                        .firstName("Pepe")
                        .lastName("Pig")
                        .build())
                .email("pepe@pig.com")
                .fakeUserLogin(FakeUserLogin.builder()
                        .id("e1938076-ec51-4b41-a5af-da58e57b578f")
                        .build())
                .build(), FakeUserResults.builder()
                .fakeUserName(FakeUserName.builder()
                        .firstName("Pepa")
                        .lastName("Pig")
                        .build())
                .email("pepa@pig.com")
                .fakeUserLogin(FakeUserLogin.builder()
                        .id("a1938076-ec51-4b41-a5af-da58e57b578f")
                        .build())
                .build());

        FakeUser fakeUser = FakeUser.builder()
                .fakeUserResults(fakeUserResults)
                .build();

        System.out.println(fakeUser.toString());

        User pepe = User.builder()
                .id("e1938076-ec51-4b41-a5af-da58e57b578f")
                .name("Pepe")
                .lastName("Pig")
                .email("pepe@pig.com")
                .isFake(true)
                .build();

        User pepa = User.builder()
                .id("a1938076-ec51-4b41-a5af-da58e57b578f")
                .name("Pepa")
                .lastName("Pig")
                .email("pepa@pig.com")
                .isFake(true)
                .build();

        String fakeUserUrl ="https://randomuser.me/api/";
        Integer quantity = 2;

        FakeUserResponse testResponse =  new FakeUserResponse(quantity + " Usuarios creados correctamente");

        Mockito.when(restTemplate.getForObject(fakeUserUrl + "?results=" + quantity, FakeUser.class)).thenReturn(fakeUser);

        Mockito.when(userRepository.save(pepe)).thenReturn(pepe);

        Mockito.when(userRepository.save(pepa)).thenReturn(pepa);

        FakeUserResponse fakeUserResponse = userService.addFakeUsers(quantity);

        Assert.assertEquals(testResponse, fakeUserResponse);
    }
}



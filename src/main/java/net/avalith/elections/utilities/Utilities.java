package net.avalith.elections.utilities;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utilities {

    public String getRandomUuid(){

        return UUID.randomUUID().toString();
    }
}

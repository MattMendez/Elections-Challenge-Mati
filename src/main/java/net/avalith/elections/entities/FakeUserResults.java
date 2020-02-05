package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FakeUserResults {

    @JsonProperty("name")
    private FakeUserName fakeUserName;

    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("login")
    private FakeUserLogin fakeUserLogin;
}

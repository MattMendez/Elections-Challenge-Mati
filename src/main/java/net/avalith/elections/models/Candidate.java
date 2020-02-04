package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("first_name")
    @NotBlank(message = "El nombre esta vacio")
    @Size(max = 30, message = "El nombre debe tener como maximo 30 caracteres")
    private String name;

    @JsonProperty("last_name")
    @NotBlank(message = "El apellido esta vacio")
    @Size(max = 30, message = "El apellido debe tener como maximo 30 caracteres")
    private String lastName;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "candidate")
    private List<ElectionsCandidates> elections;
}

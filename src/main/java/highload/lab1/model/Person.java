package highload.lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import highload.lab1.model.enums.Rarity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "`PERSON`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private UUID personId;

    @NotBlank(message = "first name cannot be empty")
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "last name cannot be empty")
    @Column(name = "lastname")
    private String lastname;

    @NotBlank(message = "team cannot be empty")
    @Column(name = "team")
    private String team;

    @Enumerated(EnumType.STRING)
    @Column(name = "rarity")
    private Rarity rarity;

    @Min(value = 0)
    @Column(name = "balance")
    private Long balance;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = {CascadeType.MERGE}, mappedBy = "person")
    @JsonManagedReference
    private Card card;

}


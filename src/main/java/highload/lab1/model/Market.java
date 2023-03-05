package highload.lab1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "`MARKET`")
public class Market implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "market_id")
    private UUID marketId;

    @Min(value = 0)
    @Column
    private Long cost;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "`PERSON_MARKET`",
            joinColumns = @JoinColumn(name = "market_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Collection<Person> persons;
}


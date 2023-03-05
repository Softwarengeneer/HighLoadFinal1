package highload.lab1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "`CAR`")
public class Card implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "card_id")
    private UUID cardId;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.ALL})
    @JoinTable(name = "`CARD_CONTENT`",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id"))
    @NotEmpty
    private Collection<Content> details;

}

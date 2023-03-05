package highload.lab1.model.dto;

import highload.lab1.model.enums.Rarity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonDto {
    private String firstname;
    private String lastname;
    private String team;
    private Rarity rarity;
    @Min(value = 0)
    private Long salary;
}


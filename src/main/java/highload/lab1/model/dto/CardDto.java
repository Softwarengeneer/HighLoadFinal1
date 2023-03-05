package highload.lab1.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class CardDto {
    @NotNull
    PersonDto person;
    @NotEmpty
    Collection<ContentDto> details;
}


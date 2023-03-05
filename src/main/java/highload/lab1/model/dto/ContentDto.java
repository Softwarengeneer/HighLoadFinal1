package highload.lab1.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class ContentDto {
    @NotBlank
    private String name;

    @Min(value = 0)
    private Long cost;
}


package highload.lab1.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotEmpty
    private Collection<RoleDto> roles;
}


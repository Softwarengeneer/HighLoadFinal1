package highload.lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "`ROLE`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID roleId;
    @Column(name = "rolename")
    private String rolename;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return rolename;
    }
}

package highload.lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "`USER`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    @JsonIgnore
    private UUID userId;

    @NotBlank(message = "username cannot be empty")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 6)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "`USER_ROLE`",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

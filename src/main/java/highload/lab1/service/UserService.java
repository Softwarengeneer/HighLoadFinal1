package highload.lab1.service;

import highload.lab1.exception.NoSuchEntityException;
import highload.lab1.exception.PermissionDeniedException;
import highload.lab1.exception.UserExistException;
import highload.lab1.model.User;
import highload.lab1.model.dto.RoleDto;
import highload.lab1.model.dto.UserDto;
import highload.lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Repository
@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public List<UserDto> getAllUsers(Integer pageSize, Integer pageNum) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        return userRepository
                .findAll(page)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    public UserDto getUser(UUID userId) {
        return modelMapper.map(getUserById(userId), UserDto.class);
    }



    @SneakyThrows
    public User getUserById(UUID userId) {
        return userRepository
                .findUserByUserId(userId)
                .orElseThrow(() -> new NoSuchEntityException("User with id " + userId + " not found"));
    }
    @SneakyThrows
    public Optional<UserDto> signUpUser(UserDto userDto) {
        Optional<User> userDb = userRepository.findByUsername(userDto.getUsername());
        if (userDb.isPresent()) {
            throw new UserExistException("User already exists");
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return Optional.of(modelMapper
                .map(userRepository
                        .save(modelMapper
                                .map(userDto, User.class)), UserDto.class));
    }

    @SneakyThrows
    public UserDto updateUser(UserDto userDto, UUID userId, UserDetails userAuth) {
        getUserById(userId);
        if (userAuth.getAuthorities()
                .stream()
                .noneMatch(grantedAuthority -> List
                        .of("SUPER_ADMIN", "ADMIN")
                        .contains(grantedAuthority.getAuthority()))
                || !userAuth
                .getUsername()
                .equals(userDto.getUsername())
                || userAuth
                .getAuthorities()
                .stream()
                .noneMatch(grantedAuthority -> userDto
                        .getRoles()
                        .stream()
                        .map(RoleDto::getRolename)
                        .toList()
                        .contains(grantedAuthority.getAuthority()))) {
            throw new PermissionDeniedException("You haven't rights for updating this user");
        }
        return modelMapper
                .map(userRepository
                        .save(modelMapper
                                .map(userDto, User.class)), UserDto.class);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.deleteUserByUserId(userId);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username = " + username + " not found"));
    }
}


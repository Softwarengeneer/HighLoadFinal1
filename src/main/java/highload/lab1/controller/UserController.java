package highload.lab1.controller;

import highload.lab1.model.dto.UserDto;
import highload.lab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(name = "page_size") Integer pageSize,
                                                     @RequestParam(name = "page") Integer pageNum) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers(pageSize, pageNum));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "user_id") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userId));
    }

    @PatchMapping("/{user_id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable(name = "user_id") UUID userId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(userDto, userId, userDetails));
    }

    @DeleteMapping("/{user_id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "user_id") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("user successfully deleted!");
    }
}

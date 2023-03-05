package highload.lab1.controller;

import highload.lab1.model.dto.PersonDto;
import highload.lab1.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons(@RequestParam(name = "page_size") Integer pageSize,
                                                         @RequestParam(name = "page") Integer pageNum) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.getAllPersons(pageSize, pageNum));
    }

    @GetMapping("/{person_id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable(name = "person_id") UUID personId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.getPerson(personId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PersonDto> insertPerson(@Valid @RequestBody PersonDto personDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.insertPerson(personDto));
    }

    @PostMapping("/buy/{person_id}")
    public ResponseEntity<String> buyContent(@RequestParam(value = "content_id") UUID contentId,
                                            @PathVariable("person_id") UUID personId) {
        personService.buyContent(contentId, personId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Success purchase!");
    }


    @PatchMapping("/{person_id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PersonDto personDto,
                                                  @PathVariable(name = "person_id") UUID personId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.updatePerson(personDto, personId));
    }

    @DeleteMapping("/{person_id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public void deletePerson(@PathVariable(name = "person_id") UUID personId) {
        personService.deletePerson(personId);
    }
}

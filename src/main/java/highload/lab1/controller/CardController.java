package highload.lab1.controller;

import highload.lab1.model.dto.CardDto;
import highload.lab1.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardDto>> getAllCards(@RequestParam(name = "page_size") Integer pageSize,
                                                   @RequestParam(name = "page") Integer pageNum) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardService.getAllCards(pageSize, pageNum));
    }

    @GetMapping("/{card_id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable(name = "card_id") UUID cardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardService.getCard(cardId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN,SUPER_ADMIN')")
    public ResponseEntity<CardDto> insertCard(@Valid @RequestBody CardDto cardDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardService.insertCard(cardDto));
    }

    @PatchMapping("/{card_id}")
    @PreAuthorize("hasAuthority('ADMIN,SUPER_ADMIN')")
    public ResponseEntity<CardDto> updateCard(@Valid @RequestBody CardDto cardDto,
                                            @PathVariable(name = "card_id") UUID cardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardService.updateCard(cardDto, cardId));
    }

    @DeleteMapping("/{card_id}")
    @PreAuthorize("hasAuthority('ADMIN,SUPER_ADMIN')")
    public ResponseEntity<String> deleteCard(@PathVariable(name = "card_id") UUID cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("card successfully deleted!");
    }
}

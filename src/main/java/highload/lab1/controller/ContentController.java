package highload.lab1.controller;

import highload.lab1.model.dto.ContentDto;
import highload.lab1.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/details")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping
    public ResponseEntity<List<ContentDto>> getAllDetails(@RequestParam(name = "page_size") Integer pageSize,
                                                         @RequestParam(name = "page") Integer pageNum) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contentService.getAllDetails(pageSize, pageNum));
    }

    @GetMapping("/{content_id}")
    public ResponseEntity<ContentDto> getContentById(@PathVariable(name = "content_id") UUID contentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contentService.getContent(contentId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN,SUPER_ADMIN')")
    public ResponseEntity<ContentDto> insertContent(@Valid @RequestBody ContentDto contentDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contentService.insertContent(contentDto));
    }

    @PatchMapping("/{content_id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<ContentDto> updateContent(@Valid @RequestBody ContentDto contentDto,
                                                  @PathVariable(name = "content_id") UUID contentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contentService.updateContent(contentDto, contentId));
    }

    @DeleteMapping("/{content_id}")
    @PreAuthorize("hasAuthority('ADMIN,SUPER_ADMIN')")
    public ResponseEntity<String> deleteContent(@PathVariable(name = "content_id") UUID contentId) {
        contentService.deleteContent(contentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("content successfully deleted!");
    }
}

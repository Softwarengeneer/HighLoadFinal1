package highload.lab1.service;

import highload.lab1.exception.NoSuchEntityException;
import highload.lab1.model.Content;
import highload.lab1.model.dto.ContentDto;
import highload.lab1.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Repository
@Component
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ModelMapper modelMapper;

    public List<ContentDto> getAllDetails(Integer pageSize, Integer pageNum) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        return contentRepository
                .findAll(page)
                .stream()
                .map(v -> modelMapper.map(v, ContentDto.class))
                .toList();
    }

    public ContentDto getContent(UUID contentId) {
        return modelMapper.map(getContentById(contentId), ContentDto.class);
    }

    @SneakyThrows
    public Content getContentById(UUID contentId) {
        return contentRepository
                .findContentByContentId(contentId)
                .orElseThrow(() -> new NoSuchEntityException("No such content"));
    }

    public ContentDto insertContent(ContentDto contentDto) {
        return modelMapper.map(
                contentRepository
                        .save(modelMapper
                                .map(contentDto, Content.class)), ContentDto.class);
    }

    @Transactional
    public void deleteContent(UUID contentId) {
        contentRepository.deleteContentByContentId(contentId);
    }

    public ContentDto updateContent(ContentDto contentDto, UUID contentId) {
        Content oldContent = getContentById(contentId);
        modelMapper.map(modelMapper.map(contentDto, Content.class), oldContent);
        return modelMapper.map(contentRepository.save(oldContent), ContentDto.class);
    }
}

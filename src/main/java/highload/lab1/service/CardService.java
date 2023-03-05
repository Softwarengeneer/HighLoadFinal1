package highload.lab1.service;

import highload.lab1.exception.NoSuchEntityException;
import highload.lab1.model.Card;
import highload.lab1.model.dto.CardDto;
import highload.lab1.repository.CardRepository;
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
public class CardService {
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    public List<CardDto> getAllCards(Integer pageSize, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return cardRepository
                .findAll(page)
                .stream()
                .map(v -> modelMapper.map(v, CardDto.class))
                .toList();
    }

    public CardDto getCard(UUID cardId) {
        return modelMapper.map(getCardById(cardId), CardDto.class);
    }

    @SneakyThrows
    public Card getCardById(UUID cardId) {
        return cardRepository
                .findCardByCardId(cardId)
                .orElseThrow(() -> new NoSuchEntityException("No such card with given ID"));
    }

    public CardDto insertCard(CardDto cardDto) {
        return modelMapper.map(
                cardRepository
                        .save(modelMapper
                                .map(cardDto, Card.class)), CardDto.class);
    }

    public void insertCard(Card card) {
        cardRepository.save(card);
    }

    @Transactional
    public void deleteCard(UUID cardId) {
        cardRepository.deleteCardByCardId(cardId);
    }

    public CardDto updateCard(CardDto cardDto, UUID cardId) {
        Card oldCard = getCardById(cardId);
        modelMapper.map(modelMapper.map(cardDto, Card.class), oldCard);
        return modelMapper.map(cardRepository.save(oldCard), CardDto.class);
    }
}

package highload.lab1.service;

import highload.lab1.exception.NoSuchEntityException;
import highload.lab1.model.Card;
import highload.lab1.model.Content;
import highload.lab1.model.Person;
import highload.lab1.model.Market;
import highload.lab1.model.dto.PersonDto;
import highload.lab1.repository.PersonRepository;
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
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final ContentService contentService;
    private final CardService cardService;
    private final MarketService marketService;

    public List<PersonDto> getAllPersons(Integer pageSize, Integer pageNum) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        return personRepository
                .findAll(page)
                .stream()
                .map(v -> modelMapper.map(v, PersonDto.class))
                .toList();
    }

    public PersonDto getPerson(UUID personId) {
        return modelMapper.map(getPersonById(personId), PersonDto.class);
    }

    @SneakyThrows
    public Person getPersonById(UUID personId) {
        return personRepository
                .findPersonByPersonId(personId)
                .orElseThrow(() -> new NoSuchEntityException("No such person"));
    }

    public PersonDto insertPerson(PersonDto personDto) {
        return modelMapper
                .map(personRepository
                        .save(modelMapper
                                .map(personDto, Person.class)), PersonDto.class);
    }

    @Transactional
    public void deletePerson(UUID personId) {
        personRepository.deletePersonByPersonId(personId);
    }

    public void winCompetition(UUID id, long salary) {
        Person person = getPersonById(id);
        person.setBalance(person.getBalance() + salary);
        personRepository.save(person);
    }

    @Transactional
    public void buyContent(UUID contentId, UUID personId) {
        Content content = contentService.getContentById(contentId);
        Person person = getPersonById(personId);
        person.setBalance(person.getBalance() - content.getCost());
        personRepository.save(person);
        Card card = person.getCard();
        card.getDetails().add(content);
        cardService.insertCard(card);
    }

    @Transactional
    public void joinCompetition(UUID marketId, UUID personId) {
        Market market = marketService.getMarketById(marketId);
        Person person = getPersonById(personId);
        person.setBalance(person.getBalance() - market.getCost());
        personRepository.save(person);
        market.getPersons().add(person);
        marketService.insertMarket(market);
    }

    public PersonDto updatePerson(PersonDto personDto, UUID personId) {
        Person oldPerson = getPersonById(personId);
        modelMapper.map(modelMapper.map(personDto, Person.class), oldPerson);
        return modelMapper.map(personRepository.save(oldPerson), PersonDto.class);
    }
}

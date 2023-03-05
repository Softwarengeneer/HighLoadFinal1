package highload.lab1.service;

import highload.lab1.exception.NoSuchEntityException;
import highload.lab1.model.Market;
import highload.lab1.model.dto.MarketDto;
import highload.lab1.repository.MarketRepository;
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
public class MarketService {
    private final MarketRepository marketRepository;
    private final ModelMapper modelMapper;

    public List<MarketDto> getAllMarkets(Integer pageSize, Integer pageNum) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        return marketRepository
                .findAll(page)
                .stream()
                .map(market -> modelMapper.map(market, MarketDto.class))
                .toList();
    }

    public MarketDto getMarket(UUID marketId) {
        return modelMapper.map(getMarketById(marketId), MarketDto.class);
    }

    @SneakyThrows
    public Market getMarketById(UUID marketId) {
        return marketRepository
                .findById(marketId)
                .orElseThrow(() -> new NoSuchEntityException("Market with id " + marketId + " not found"));
    }

    public MarketDto insertMarket(MarketDto marketDto) {
        return modelMapper
                .map(insertMarket(modelMapper.map(marketDto, Market.class)), MarketDto.class);
    }

    public Market insertMarket(Market market) {
        return marketRepository.save(market);
    }

    public MarketDto updateMarket(MarketDto marketDto, UUID marketId) {
        Market oldMarket = getMarketById(marketId);
        modelMapper.map(modelMapper.map(marketDto, Market.class), oldMarket);
        return modelMapper.map(marketRepository.save(oldMarket), MarketDto.class);
    }

    @Transactional
    public void deleteMarket(UUID marketId) {
        marketRepository.deleteMarketByMarketId(marketId);
    }
}
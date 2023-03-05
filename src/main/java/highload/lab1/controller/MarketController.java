package highload.lab1.controller;

import highload.lab1.model.dto.MarketDto;
import highload.lab1.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/markets")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping
    public ResponseEntity<List<MarketDto>> getAllMarkets(@RequestParam(name = "page_size") Integer pageSize,
                                                     @RequestParam(name = "page") Integer pageNum) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(marketService.getAllMarkets(pageSize, pageNum));
    }

    @GetMapping("/{market_id}")
    public ResponseEntity<MarketDto> getMarketById(@PathVariable(name = "market_id") UUID marketId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(marketService.getMarket(marketId));
    }

    @PostMapping
    public ResponseEntity<MarketDto> insertMarket(@Valid @RequestBody MarketDto market) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(marketService.insertMarket(market));
    }

    @PutMapping("/{market_id}")
    public ResponseEntity<MarketDto> updateMarket(@Valid @RequestBody MarketDto market,
                                              @PathVariable(name = "market_id") UUID marketId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(marketService.updateMarket(market, marketId));
    }

    @DeleteMapping("/{market_id}")
    public ResponseEntity<String> deleteMarket(@PathVariable(name = "market_id") UUID marketId) {
        marketService.deleteMarket(marketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("market successfully deleted!");
    }
}

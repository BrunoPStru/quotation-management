package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping
    public List<StockQuoteDto> getStockQuote(StockQuoteDto stockQuoteDto) {

        if (stockQuoteDto.getStockId().isEmpty()) {
            List<StockQuoteDto> stocksQuotesDto = stockService.findAll();

            return stocksQuotesDto;
        } else {
            Optional<Stock> opStock = stockService.findByStockId(stockQuoteDto.getStockId());

            Stock stock = new Stock();
            if (opStock.isPresent()){
                Stock stockAux = opStock.get();
                stock.setId(stockAux.getId());
            }

            List<StockQuoteDto> stocksQuotesDto = new ArrayList<>();
            return stocksQuotesDto;
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StockQuoteDto> postStockQuote(@RequestBody StockQuoteDto stockQuoteDto,
                                                        UriComponentsBuilder uriBuilder) {
        Stock stock = stockQuoteDto.convertToStock();
        stock = stockService.saveStockAndQuotes(stock);

        URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
        return ResponseEntity.created(uri).body(new StockQuoteDto(stock));
    }

}

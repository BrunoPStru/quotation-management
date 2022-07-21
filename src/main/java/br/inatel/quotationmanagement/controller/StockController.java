package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;

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

    StockService stockService;

//    @GetMapping
//    public List<StockQuoteDto> list(StockQuoteDto stockQuoteDto) {
//
//        if (stockQuoteDto.getStockId() == null) {
//            List<StockQuoteDto> stocks = stockService.findAll();
//            return stocks.convert();
//        } else {
//            List<StockQuoteDto> stock = stockService.findByStockId(stockQuoteDto.getStockId());
//            return stock.convert();
//        }
//
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StockQuoteDto> register(@RequestBody StockQuoteDto stockQuoteDto,
                                                  UriComponentsBuilder uriBuilder) {
        Stock stock = stockQuoteDto.convert();
        StockService stockService = new StockService();
        stockService.saveStockAndQuotes(stock);

        URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
        return ResponseEntity.created(uri).body(new StockQuoteDto(stock));
    }

}

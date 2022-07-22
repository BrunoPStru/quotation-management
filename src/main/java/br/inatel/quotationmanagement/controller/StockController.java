package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<List<StockQuoteDto>> getStockQuote(@RequestParam(required = false) String stockId) {
    	if (stockId == null) {
    		List<StockQuoteDto> liststockQuoteDto = stockService.findAll();
    		return ResponseEntity.ok(liststockQuoteDto);
    	} else {
    		Optional<Stock> optStock = stockService.findByStockId(stockId);
    		List<StockQuoteDto> liststockQuoteDto = new ArrayList<>();
    		liststockQuoteDto.add(new StockQuoteDto(optStock.get()));
    		return ResponseEntity.ok(liststockQuoteDto);
    	}
        
    }
    
//    @GetMapping
//	public ResponseEntity<List<StockQuoteDto>> getStockQuote() {
//		List<StockQuoteDto> liststockQuoteDto = stockService.findAll();
//		return ResponseEntity.ok(liststockQuoteDto);
//
//	}
//
//	@GetMapping("/{stockId}")
//	public ResponseEntity<StockQuoteDto> getOneStockQuote(@PathVariable String stockId) {
//		Optional<Stock> optStock = stockService.findByStockId(stockId);
//		return ResponseEntity.ok(new StockQuoteDto(optStock.get()));
//	}

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

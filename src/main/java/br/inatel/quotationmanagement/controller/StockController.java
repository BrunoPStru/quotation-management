package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

//	@Autowired
//	private StockRepository stockRepository;
//
//	@GetMapping
//	public List<StockQuoteDto> list(String stockId) {
//
//		if (stockId == null) {
//			List<Stock> stocks = stockRepository.findAll();
//			return StockQuoteDto.convert(stocks);
//		} else {
//			List<Stock> stock = stockRepository.findByStockId(stockId);
//			return StockQuoteDto.convert(stock);
//		}
//
//	}

	@PostMapping
	public ResponseEntity<StockQuoteDto> register(@RequestBody StockQuoteDto stockQuoteDto,
			UriComponentsBuilder uriBuilder) {
		Stock stock = stockQuoteDto.convert();
		StockService stockService = new StockService();
		stockService.saveStockAndQuotes(stock, stock.getQuotes());

		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockQuoteDto(stock));
	}

}

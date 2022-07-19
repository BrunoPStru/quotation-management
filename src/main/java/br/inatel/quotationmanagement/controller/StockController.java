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

import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.controller.form.StockForm;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.repository.StockRepository;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@GetMapping
	public List<StockDto> list(String stockId) {

		if (stockId == null) {
			List<Stock> stocks = stockRepository.findAll();
			return StockDto.convert(stocks);
		} else {
			List<Stock> stock = stockRepository.findByStockId(stockId);
			return StockDto.convert(stock);
		}

	}

	@PostMapping
	public ResponseEntity<StockDto> register(@RequestBody StockForm form, UriComponentsBuilder uriBuilder) {
		Stock stock = form.convert(quoteRepository);
		stockRepository.save(stock);

		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockDto(stock));
	}

}

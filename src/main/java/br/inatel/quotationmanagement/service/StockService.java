package br.inatel.quotationmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.quotationmanagement.adapter.WebClientAdapter;
import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.repository.StockRepository;

@Service
@Transactional
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	WebClientAdapter webClientAdapter;

	public List<StockQuoteDto> findAll() {
		List<Stock> listStock = stockRepository.findAll();
		return StockQuoteDto.convertToListDto(listStock);
	}

	public Optional<Stock> findByStockId(String stockId) {
		Stock stock = stockRepository.findByStockId(stockId);

		if (stock != null) {
			stock.getListQuote().size();
		}

		return Optional.ofNullable(stock);
	}

	public Stock saveStockAndQuotes(Stock stock) {
		
		if (stock.getStockId().isBlank() || stock.getStockId() == null) {
			throw new NoSuchElementException("stockId needs to have a value.");
		}

		List<Quote> listQuote = new ArrayList<>(stock.getListQuote());

		validateStock(stock);

		stock = saveStock(stock);

		saveQuotes(listQuote, stock);

		return stock;
	}

	private Stock saveStock(Stock stock) {
		Optional<Stock> optStock = findByStockId(stock.getStockId());

		if (optStock.isPresent()) {
			Stock stockAux = optStock.get();
			stock.setId(stockAux.getId());
		}

		stockRepository.save(stock);

		return stock;
	}

	private void validateStock(Stock stock) {
		Boolean validate = false;

		validate = webClientAdapter
				.getFlux()
				.stream()
				.anyMatch(s -> s.getStockId().equals(stock.getStockId()));

		if (validate) {
			return;
		}

		throw new NoSuchElementException("Stock not found.");
	}

	private void saveQuotes(List<Quote> listQuote, Stock stock) {
		listQuote.forEach(q -> q.setStock(stock));

		quoteRepository.saveAll(listQuote);
	}

}

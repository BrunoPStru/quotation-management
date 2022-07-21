package br.inatel.quotationmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.repository.StockRepository;

@Service
@Transactional
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	public List<Stock> findAll(){
		List<Stock> stocks = stockRepository.findAll();
		
		return stocks;
	}

	public Optional<Stock> findByStockId(String stockId){
		Stock stock = stockRepository.findByStockId(stockId);
		
		return Optional.ofNullable(stock);
	}

	public Stock saveStockAndQuotes(Stock stock) {
		List<Quote> quotes = new ArrayList<>(stock.getQuotes());
		
		stock = saveStock(stock);
		
		saveQuotes(quotes, stock);

		return stock;
	}

	private Stock saveStock(Stock stock) {
		Optional<Stock> opStock = findByStockId(stock.getStockId());
		
		if (opStock.isPresent()){
			Stock stockAux = opStock.get();
			stock.setId(stockAux.getId());
		}
		
		stockRepository.save(stock);
		
		return stock;
	}
	
	private void saveQuotes(List<Quote> quotes, Stock stock) {
		quotes.forEach(q -> q.setStock(stock));
		
		quoteRepository.saveAll(quotes);
	}

}

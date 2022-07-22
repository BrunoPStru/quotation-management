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
	
	public List<StockQuoteDto> findAll(){
		List<Stock> listStock = stockRepository.findAll();
		return StockQuoteDto.convertToListDto(listStock);
	}

	public Optional<Stock> findByStockId(String stockId){
		Stock stock = stockRepository.findByStockId(stockId);
		
		if (stock != null) {
			stock.getListQuote().size();
		}
		
		return Optional.ofNullable(stock);
	}

	public Stock saveStockAndQuotes(Stock stock) {
		List<Quote> listQuote = new ArrayList<>(stock.getListQuote());

		stock = saveStock(stock);

		saveQuotes(listQuote, stock);

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

	private void saveQuotes(List<Quote> listQuote, Stock stock) {
		listQuote.forEach(q -> q.setStock(stock));

		quoteRepository.saveAll(listQuote);
	}



}

package br.inatel.quotationmanagement.service;

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

//	public List<StockQuoteDto> findAll(){
//		List<Stock> stockQuoteDtos = stockRepository.findAll();
//		return stockQuoteDtos;
//	}

	public Optional<Stock> findByStockId(String stockId){
		Stock stock = stockRepository.findByStockId(stockId);
		return Optional.ofNullable(stock);
	}

	public Stock saveStockAndQuotes(Stock stock) {
		Optional<Stock> findStock = findByStockId(stock.getStockId());

		if (findStock == null){
			stockRepository.saveAndFlush(stock);
		} else {
			quoteRepository.saveAllAndFlush(stock.getQuotes());
		}

		return stock;
	}

}

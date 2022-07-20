package br.inatel.quotationmanagement.service;

import java.util.List;

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
	
	public Stock saveStockAndQuotes(Stock stock, List<Quote> quotes) {
		System.out.println(stockRepository.findByStockId(stock.getStockId()));
		return stock;
	}
	
}

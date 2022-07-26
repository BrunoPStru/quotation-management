package br.inatel.quotationmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.inatel.quotationmanagement.adapter.WebClientAdapter;
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
	
	@Autowired
	WebClientAdapter webClientAdapter;
	
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

		Boolean validate = validateStock(stock); 

		if (validate) {
			Optional<Stock> optStock = findByStockId(stock.getStockId());
			
			if (optStock.isPresent()){
				Stock stockAux = optStock.get();
				stock.setId(stockAux.getId());
			}
			
			stockRepository.save(stock);
			
			return stock;
		}
		
		return stock;
	}
	
	private Boolean validateStock(Stock stock) {
		Boolean validate = false;
		
		//chama a API externa para verificar se o stock existe na API, se existir pode salvar, se não lança uma exception
		
		return validate;
	}

	private void saveQuotes(List<Quote> listQuote, Stock stock) {
		listQuote.forEach(q -> q.setStock(stock));

		quoteRepository.saveAll(listQuote);
	}

}

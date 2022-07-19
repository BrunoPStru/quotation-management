package br.inatel.quotationmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.quotationmanagement.controller.dto.StockDto;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.repository.StockRepository;

@RestController
public class StockController {

	@Autowired
	private StockRepository stockRepository;

	@RequestMapping("/stocks")
	public List<StockDto> list(String stockId) {

		if (stockId == null) {
			List<Stock> stocks = stockRepository.findAll();
			return StockDto.convert(stocks);
		} else {
			List<Stock> stock = stockRepository.findByStockId(stockId);
			return StockDto.convert(stock);
		}

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate quoteDate = LocalDate.parse("01/01/2019", formatter);
//		LocalDate quoteDate2 = LocalDate.parse("02/01/2019", formatter);
//		LocalDate quoteDate3 = LocalDate.parse("03/01/2019", formatter);
//		
//		Quote quote = new Quote(quoteDate, 10.0);
//		Quote quote2 = new Quote(quoteDate2, 11.0);
//		Quote quote3 = new Quote(quoteDate3, 14.0);
//		
//		List<Quote> quotes = new ArrayList<Quote>();
//		quotes.add(quote);
//		quotes.add(quote2);
//		quotes.add(quote3);
//		
//		Stock stock = new Stock("PETR3", quotes);
//		
//		return StockDto.convert(Arrays.asList(stock));

	}

}

package br.inatel.quotationmanagement.controller.form;

import java.time.LocalDate;
import java.util.Map;

import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.repository.QuoteRepository;

public class StockForm {

	private String stockId;
	private Map<LocalDate, Double> quotes;
//	private LocalDate quoteDate;
//	private Double price;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

//	public LocalDate getQuoteDate() {
//		return quoteDate;
//	}
//
//	public void setQuoteDate(LocalDate quoteDate) {
//		this.quoteDate = quoteDate;
//	}
//
//	public Double getPrice() {
//		return price;
//	}
//
//	public void setPrice(Double price) {
//		this.price = price;
//	}
	
	public Map<LocalDate, Double> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, Double> quotes) {
		this.quotes = quotes;
	}

	public Stock convert(QuoteRepository quoteRepository) {
//		Quote quote = quoteRepository.findByStock(stockId);
//		quote.setQuoteDate(quoteDate);
//		quote.setPrice(price);
		return new Stock(stockId, quotes);
	}

}

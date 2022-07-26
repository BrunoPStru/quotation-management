package br.inatel.quotationmanagement.controller.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;

public class StockQuoteDto {

	private String id;
	private String stockId;
	private Map<LocalDate, Double> quotes = new HashMap<>();

	public StockQuoteDto() {
	}

	public StockQuoteDto(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		this.quotes = stock.getListQuote()
				.stream()
				.collect(Collectors.toMap(Quote::getQuoteDate, Quote::getPrice));
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public void setQuotes(Map<LocalDate, Double> quotes) {
		this.quotes = quotes;
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<LocalDate, Double> getQuotes() {
		return quotes;
	}

	public static List<StockQuoteDto> convertToListDto(List<Stock> listStock) {
		return listStock.stream().map(StockQuoteDto::new).collect(Collectors.toList());
	}

	public Stock convertToStock() {
		Stock stock = new Stock();
		stock.setId(id);
		stock.setStockId(stockId);
		stock.setListQuote(
				quotes.entrySet().stream()
					.map(e -> new Quote(e.getKey(), e.getValue()))
					.collect(Collectors.toList())
				);
		return stock;
	}

}

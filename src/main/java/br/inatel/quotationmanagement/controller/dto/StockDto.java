package br.inatel.quotationmanagement.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;

public class StockDto {

	private String id;
	private String stockId;
	private List<Quote> quotes;
	
	public StockDto(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		this.quotes = stock.getQuotes();
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public static List<StockDto> convert(List<Stock> stocks) {
		return stocks.stream().map(StockDto::new).collect(Collectors.toList());
	}

}

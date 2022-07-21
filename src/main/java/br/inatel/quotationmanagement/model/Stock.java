package br.inatel.quotationmanagement.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class Stock {

	@Id
	private String id;
	
	@NotNull
	private String stockId;
	
	@OneToMany(mappedBy = "stock")
	private List<Quote> quotes;

	public Stock() {
	}

	public Stock(String stockId, List<Quote> quotes) {
		this.stockId = stockId;
		this.quotes = quotes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	@PrePersist
	private void createId() {
		this.id = UUID.randomUUID().toString();
	}

}

package br.inatel.quotationmanagement.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate quoteDate;
	private Double price;
	@ManyToOne
	private Stock stock;

//	public Quote(LocalDate quoteDate, Double price) {
//		this.quoteDate = quoteDate;
//		this.price = price;
//	}

	public Long getId() {
		return id;
	}

	public LocalDate getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(LocalDate quoteDate) {
		this.quoteDate = quoteDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}

package br.inatel.quotationmanagement.controller.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AdapterStockDto {

	@JsonAlias("id")
	private String stockId;

	private String description;

	public AdapterStockDto() {
	}

	public AdapterStockDto(String stockId, String description) {
		this.stockId = stockId;
		this.description = description;
	}

	public String getStockId() {
		return stockId;
	}

	public String getDescription() {
		return description;
	}

}

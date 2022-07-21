package br.inatel.quotationmanagement.repository;

import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

	List<Stock> findAll();
	
	Stock findByStockId(String stockId);

}

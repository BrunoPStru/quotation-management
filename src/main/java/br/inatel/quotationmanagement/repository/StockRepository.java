package br.inatel.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {

//	List<Stock> findByStockId(String stockId);
	
	Stock findByStockId(String stockId);

}

package br.inatel.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.quotationmanagement.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

	Stock findByStockId(String stockId);

}

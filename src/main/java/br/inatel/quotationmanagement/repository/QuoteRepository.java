package br.inatel.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.model.Quote;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

}

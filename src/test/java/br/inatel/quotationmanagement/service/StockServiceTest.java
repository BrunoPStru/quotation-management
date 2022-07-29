package br.inatel.quotationmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import br.inatel.quotationmanagement.model.Quote;
import br.inatel.quotationmanagement.model.Stock;

@ActiveProfiles("test")
@SpringBootTest
class StockServiceTest {

	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Autowired
	private StockService service;

	@Test
	void givenNoParameter_WhenFindAllMethod_ThenMustReturnListOfStockAndQuotes() {

		List<StockQuoteDto> listStockQuoteDto = service.findAll();

		assertTrue(!listStockQuoteDto.isEmpty());
	}

	@Test
	void givenValidStockId_WhenFindByStockIdMethodCall_ThenMustReturnStock() {

		String stockId = "petr4";
		Optional<Stock> optStock = service.findByStockId(stockId);

		assertEquals(optStock.get().getStockId(), stockId);
	}

	@Test
	void givenNullStockId_WhenFindByStockIdMethodCall_ThenMustReturnNull() {

		Optional<Stock> optStock = service.findByStockId(null);

		assertTrue(optStock.isEmpty());
	}
	
	@Test
	void givenInvalidStockId_WhenFindByStockIdMethodCall_ThenMustReturnNull() {

		String stockId = "petr45";
		Optional<Stock> optStock = service.findByStockId(stockId);

		assertTrue(optStock.isEmpty());
	}
	
	@Test
	void givenValidStockId_WhenSaveStockAndQuotesMethodCall_ThenMustReturnStock() {
		
		List<Quote> listQuote = new ArrayList<>();
		Quote quote = new Quote(LocalDate.parse("2019-01-02", DTF), 10.0);
		listQuote.add(quote);
		Stock stock = new Stock("petr4", listQuote);
		
		Stock savedStock = service.saveStockAndQuotes(stock);
		
		assertEquals(savedStock.getStockId(), stock.getStockId());
		assertEquals(savedStock.getListQuote().get(0).getPrice(), stock.getListQuote().get(0).getPrice());
	}
	
	@Test
	void givenInvalidDate_WhenSaveStockAndQuotesMethodCall_ThenMustReturnDateTimeParseException() {
		
		assertThrows(DateTimeParseException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			Quote quote = new Quote(LocalDate.parse("2019-15-50", DTF), 10.0);
			listQuote.add(quote);
			Stock stock = new Stock("petr4", listQuote);
			
			Stock savedStock = service.saveStockAndQuotes(stock);
		});
		
	}
	
	@Test
	void givenInvalidPrice_WhenSaveStockAndQuotesMethodCall_ThenMustReturnConstraintViolationException() {
		
		assertThrows(ConstraintViolationException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			Quote quote = new Quote(LocalDate.parse("2019-01-01", DTF), -10.0);
			listQuote.add(quote);
			Stock stock = new Stock("petr4", listQuote);
			
			Stock savedStock = service.saveStockAndQuotes(stock);
		});
		
	}
	
	@Test
	void givenBlankStockId_WhenSaveStockAndQuotesMethodCall_ThenMustReturnNoSuchElementException() {
		
		assertThrows(NoSuchElementException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			Quote quote = new Quote(LocalDate.parse("2019-01-01", DTF), 10.0);
			listQuote.add(quote);
			Stock stock = new Stock("", listQuote);
			
			Stock savedStock = service.saveStockAndQuotes(stock);
		});
		
	}
	
	@Test
	void givenNullStockId_WhenSaveStockAndQuotesMethodCall_ThenMustReturnNullPointerException() {
		
		assertThrows(NullPointerException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			Quote quote = new Quote(LocalDate.parse("2019-01-01", DTF), 10.0);
			listQuote.add(quote);
			Stock stock = new Stock(null, listQuote);
			
			Stock savedStock = service.saveStockAndQuotes(stock);
		});
		
	}
	
	@Test
	void givenInvalidStockId_WhenSaveStockAndQuotesMethodCall_ThenMustReturnNoSuchElementException() {
		
		assertThrows(NoSuchElementException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			Quote quote = new Quote(LocalDate.parse("2019-01-01", DTF), 10.0);
			listQuote.add(quote);
			Stock stock = new Stock("petr45", listQuote);
			
			Stock savedStock = service.saveStockAndQuotes(stock);
		});
		
	}
	
	@Test
	void givenEmptyQuote_WhenSaveStockAndQuotesMethodCall_ThenMustReturnConstraintViolationException() {
		
		assertThrows(ConstraintViolationException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			Quote quote = new Quote();
			listQuote.add(quote);
			Stock stock = new Stock("petr4", listQuote);
			
			Stock savedStock = service.saveStockAndQuotes(stock);
		});
		
	}

}

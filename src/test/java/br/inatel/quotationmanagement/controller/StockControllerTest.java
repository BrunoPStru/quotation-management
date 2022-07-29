package br.inatel.quotationmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.inatel.quotationmanagement.adapter.WebClientAdapter;
import br.inatel.quotationmanagement.controller.dto.AdapterStockDto;
import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class StockControllerTest {
	
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private WebClientAdapter webClientAdapter;
	
	@Test
	void givenNoParameter_WhenGetStockQuoteMethodCalled_ThenMustReturnAListOfStockDtos() {
		webTestClient.get()
			.uri("/stock")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(StockQuoteDto.class)
			.returnResult();
	}
	
	@Test
	void givenValidStockId_WhenGetStockQuoteMethodCalled_ThenMustReturnAListOfStockDtoWithRespectiveStockId() {
		webTestClient.get()
			.uri("/stock?stockId=petr4")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(StockQuoteDto.class)
			.returnResult();
	}
	
	@Test
	void givenInvalidStockId_WhenGetStockQuoteMethodCalled_ThenMustReturnNotFound() {
		webTestClient.get()
			.uri("/stock?stockId=petr45")
			.exchange()
			.expectStatus()
			.isNotFound();
	}
	
	@Test
	void givenValidStock_WhenPostStockQuoteMethodCalled_ThenMustReturnCreated() {
		List<AdapterStockDto> listAdapterStockDto = new ArrayList<>();
		listAdapterStockDto.add(new AdapterStockDto("petr4", "description petr"));
		
		Mockito.when(webClientAdapter.getFlux())
			.thenReturn(listAdapterStockDto);
		
		StockQuoteDto stockQuoteDto = new StockQuoteDto();
		stockQuoteDto.setStockId("petr4");
		stockQuoteDto.setQuotes(new HashMap<>(Map.of(LocalDate.parse("2019-01-10", DTF), 10.0)));
		
		webTestClient.post()
			.uri("/stock/")
			.body(Mono.just(stockQuoteDto), StockQuoteDto.class)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectBody(StockQuoteDto.class);
	}
	
	@Test
	void givenInexistentStockId_WhenPostStockQuoteMethodCalled_ThenMustReturnBadRequest() {
		List<AdapterStockDto> listAdapterStockDto = new ArrayList<>();
		listAdapterStockDto.add(new AdapterStockDto("petr4", "description petr"));
		
		Mockito.when(webClientAdapter.getFlux())
			.thenReturn(listAdapterStockDto);
		
		StockQuoteDto stockQuoteDto = new StockQuoteDto();
		stockQuoteDto.setStockId("petr45");
		stockQuoteDto.setQuotes(new HashMap<>(Map.of(LocalDate.parse("2019-01-10", DTF), 10.0)));
		
		webTestClient.post()
			.uri("/stock/")
			.body(Mono.just(stockQuoteDto), StockQuoteDto.class)
			.exchange()
			.expectStatus()
			.isBadRequest();
	}
	
	@Test
	void givenInvalidDate_WhenPostStockQuoteMethodCalled_ThenMustReturnDateTimeParseException() {
		List<AdapterStockDto> listAdapterStockDto = new ArrayList<>();
		listAdapterStockDto.add(new AdapterStockDto("petr4", "description petr"));
		
		Mockito.when(webClientAdapter.getFlux())
			.thenReturn(listAdapterStockDto);
		
		assertThrows(DateTimeParseException.class, () -> {
			StockQuoteDto stockQuoteDto = new StockQuoteDto();
			stockQuoteDto.setStockId("petr4");
			stockQuoteDto.setQuotes(new HashMap<>(Map.of(LocalDate.parse("2019-01-35", DTF), 10.0)));
			
			webTestClient.post()
			.uri("/stock/")
			.body(Mono.just(stockQuoteDto), StockQuoteDto.class)
			.exchange()
			.expectStatus()
			.isBadRequest();
		});
	}
	
	@Test
	void givenInvalidPrice_WhenPostStockQuoteMethodCalled_ThenMustReturnConstraintViolationException() {
		List<AdapterStockDto> listAdapterStockDto = new ArrayList<>();
		listAdapterStockDto.add(new AdapterStockDto("petr4", "description petr"));
		
		Mockito.when(webClientAdapter.getFlux())
			.thenReturn(listAdapterStockDto);
		
			StockQuoteDto stockQuoteDto = new StockQuoteDto();
			stockQuoteDto.setStockId("petr4");
			stockQuoteDto.setQuotes(new HashMap<>(Map.of(LocalDate.parse("2019-01-10", DTF), -10.0)));
			
			webTestClient.post()
			.uri("/stock/")
			.body(Mono.just(stockQuoteDto), StockQuoteDto.class)
			.exchange()
			.expectStatus()
			.isBadRequest();
	}

}

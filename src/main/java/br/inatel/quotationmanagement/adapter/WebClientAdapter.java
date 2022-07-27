package br.inatel.quotationmanagement.adapter;

import java.util.ArrayList;
import java.util.List;

import br.inatel.quotationmanagement.controller.dto.WebClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.quotationmanagement.model.Stock;
import reactor.core.publisher.Flux;

@Service
public class WebClientAdapter {

	@Value("${stock.management.url}")
	private String stockManagementUrl;
	
    public List<Stock> getFlux(){
    	
        List<Stock> listStock = new ArrayList<Stock>();

        Flux<Stock> fluxStock = WebClient.create(stockManagementUrl)
                .get()
                .uri("/stock")
                .retrieve()
                .bodyToFlux(Stock.class);

        fluxStock.subscribe(w -> listStock.add(w));
        fluxStock.blockLast();

        return listStock;
    }

}

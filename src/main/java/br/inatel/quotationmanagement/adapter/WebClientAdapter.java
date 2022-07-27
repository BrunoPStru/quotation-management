package br.inatel.quotationmanagement.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.quotationmanagement.controller.dto.AdapterStockDto;
import reactor.core.publisher.Flux;

@Service
public class WebClientAdapter {

	@Value("${stock.management.url}")
	private String STOCKMANAGEMENTURL;
	
	@Cacheable(value = "stockCache")
    public List<AdapterStockDto> getFlux(){
    	
        List<AdapterStockDto> listAdapterStock = new ArrayList<>();

        try {
			Flux<AdapterStockDto> fluxStock = WebClient.create(STOCKMANAGEMENTURL)
			        .get()
			        .uri("/stock")
			        .retrieve()
			        .bodyToFlux(AdapterStockDto.class);

			fluxStock.subscribe(a -> listAdapterStock.add(a));
			fluxStock.blockLast();

		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return listAdapterStock;
    }
	
//	@PostConstruct
//	postMono http://localhost:8080/notification

}

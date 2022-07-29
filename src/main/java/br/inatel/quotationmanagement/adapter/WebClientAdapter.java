package br.inatel.quotationmanagement.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.quotationmanagement.controller.dto.AdapterStockDto;
import br.inatel.quotationmanagement.controller.dto.NotificationDto;
import reactor.core.publisher.Flux;

@Service
public class WebClientAdapter {

	@Value("${stock.management.url}")
	private String STOCKMANAGEMENTURL;
	
	@Value("${quotation.management.host}")
	private String HOST;
	
	@Value("${quotation.management.port}")
	private Integer PORT;
	
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
	
	@PostConstruct
	public void registerApiForNotification() {
		NotificationDto notificationDto = new NotificationDto(HOST, PORT);
		
		WebClient.create(STOCKMANAGEMENTURL)
			.post()
			.uri("/notification")
			.bodyValue(notificationDto)
			.retrieve()
			.toBodilessEntity()
			.block();
	}

}

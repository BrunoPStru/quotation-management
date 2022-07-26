package br.inatel.quotationmanagement.controller;

import br.inatel.quotationmanagement.model.Stock;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebClientController {

    public List<Stock> getFlux(){
        List<Stock> listStock = new ArrayList<Stock>();

        Flux<Stock> fluxStock = WebClient.create("http://localhost:8080")
                .get()
                .uri("/stock")
                .retrieve()
                .bodyToFlux(Stock.class);

        fluxStock.subscribe(s -> listStock.add(s));
        fluxStock.blockLast();

        return listStock;
    }

}

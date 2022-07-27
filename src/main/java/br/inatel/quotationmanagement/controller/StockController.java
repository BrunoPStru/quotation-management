package br.inatel.quotationmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.quotationmanagement.controller.dto.StockQuoteDto;
import br.inatel.quotationmanagement.model.Stock;
import br.inatel.quotationmanagement.service.StockService;

import javax.validation.Valid;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;
   
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockQuoteDto> getStockQuote(@RequestParam(required = false) String stockId) {
    	try{
            if (stockId == null) {
                List<StockQuoteDto> listStockQuoteDto = stockService.findAll();

                return listStockQuoteDto;
            } else {
                Optional<Stock> optStock = stockService.findByStockId(stockId);
                List<StockQuoteDto> listStockQuoteDto = new ArrayList<>();

                listStockQuoteDto.add(new StockQuoteDto(optStock.get()));

                return listStockQuoteDto;
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockQuoteDto postStockQuote(@RequestBody @Valid StockQuoteDto stockQuoteDto) {
        try{
            Stock stock = stockQuoteDto.convertToStock();
            stock = stockService.saveStockAndQuotes(stock);

            return new StockQuoteDto(stock);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

}

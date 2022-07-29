package br.inatel.quotationmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

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

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;

    /**
     * This is a method that lists the stocks and quotes
     *
     * @param "stockId" is the name of the "stockId" column
     * @return a list of stockDto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockQuoteDto> getStockQuote(@RequestParam(required = false) String stockId) {
        try {
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

    /**
     * This is the method that makes the post for stock and quote
     * 
     * @param "stockQuoteDto" is the name of the "StockQuoteDto" class
     * @return returns the stock that was sent to the post method
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockQuoteDto postStockQuote(@RequestBody @Valid StockQuoteDto stockQuoteDto) {
        try {
            Stock stock = stockQuoteDto.convertToStock();
            stock = stockService.saveStockAndQuotes(stock);

            return new StockQuoteDto(stock);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

}

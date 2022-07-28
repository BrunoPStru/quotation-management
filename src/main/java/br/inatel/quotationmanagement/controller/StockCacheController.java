package br.inatel.quotationmanagement.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockcache")
public class StockCacheController {

	@DeleteMapping
	@CacheEvict(value = "stockCache", allEntries = true)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void clearCache() {
	}

}

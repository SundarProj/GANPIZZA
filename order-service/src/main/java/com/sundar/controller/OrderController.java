package com.sundar.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundar.bean.GANPizza;
import com.sundar.bean.Topping;
import com.sundar.service.BakingService;
import com.sundar.service.OrderService;
import com.sundar.util.BakedServerResponse;

@RestController
public class OrderController {

	private final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private BakingService bakingService;

	@GetMapping("/toppings")
	public ResponseEntity<List<Topping>> getToppings() {
		return ResponseEntity.ok(orderService.getIngredients());
	}

	@PostMapping(path = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BakedServerResponse> submit(@RequestBody GANPizza pizza) throws Exception {

		long start = System.currentTimeMillis();

		BakedServerResponse response = new BakedServerResponse();

		CompletableFuture<BakedServerResponse> result = bakingService.executeOrder(pizza).thenApply(ganPizza -> {
			response.setPizza(ganPizza);
			response.setTimeMs(System.currentTimeMillis() - start);
			response.setCompletingThread(Thread.currentThread().getName());
			return response;
		});
		
		 ResponseEntity<BakedServerResponse> responseEntity = new ResponseEntity<>(result.get(), HttpStatus.OK);
		 LOG.debug("RESponseEntity SSS {}", responseEntity);
		
		return responseEntity;
	}
}

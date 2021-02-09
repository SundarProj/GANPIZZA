package com.sundar.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sundar.bean.GANPizza;
import com.sundar.bean.OrderStatus;

@Service
public class BakingService {

	private final Logger LOG = LoggerFactory.getLogger(BakingService.class);

	@Value("${bake.oven.pizza.time}")
	private int pizzaBakeTime;

	@Autowired
	OrderService orderService;

	@Async("asyncExecutor")
	public CompletableFuture<GANPizza> executeOrder(GANPizza pizza) throws InterruptedException {
		GANPizza pizzaValidated = orderService.validateOrder(pizza);
		if (OrderStatus.OK == pizzaValidated.getStatus()) {
			LOG.debug("Baking Started for {}", pizza);
			orderService.adjustQuantity(pizza.getToppings());
			LOG.debug("Toppings quantity updated for {}", pizza);
			Thread.sleep(pizzaBakeTime);
			LOG.debug("Pizza Ready {}", pizza);
			pizza.setMessage("Pizza is ready to EAT :)");
		}
		
		LOG.debug("PizzaValidated {}", pizzaValidated);
		
		return CompletableFuture.completedFuture(pizza);
	}
}

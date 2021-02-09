package com.sundar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.sundar.bean.AvailableTopping;
import com.sundar.bean.BakedServerResponse;
import com.sundar.bean.Crust;
import com.sundar.bean.GANPizza;
import com.sundar.bean.Order;
import com.sundar.bean.Sauce;
import com.sundar.bean.Topping;

import reactor.core.publisher.Mono;

@Controller
@SessionAttributes("order")
public class ClientController {

	private final Logger LOG = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/")
	public String home(ModelMap model) {
		return "index";
	}

	@RequestMapping("/returnHome")
	public String returnHome(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		return "redirect:";
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String addToCart(@ModelAttribute("pizza") GANPizza pizza, @ModelAttribute("order") Order order,
			ModelMap model) {
		pizza.setNumber(order.getGanPizzaList().isEmpty() ? 1 : order.getGanPizzaList().size() + 1);
		order.getGanPizzaList().add(pizza);
		LOG.debug("Pizza in the log {} and {}", pizza, order);
		return "cart";
	}

	@RequestMapping("/buildpizza")
	public String buildPizza(@ModelAttribute("order") Order order, ModelMap model) {
		AvailableTopping[] availableToppings = restTemplate
				.getForEntity("http://ORDER-SERVICE/toppings", AvailableTopping[].class).getBody();
		List<Topping> toppings = convertToClientTopping(availableToppings);
		GANPizza pizza = new GANPizza();
		pizza.setCrust(Crust.THIN_AND_CRISPY);
		pizza.setSauce(Sauce.CLASSIC_MARINARA);
		pizza.setToppings(toppings);
		model.addAttribute("pizza", pizza);
		model.addAttribute("crusts", Crust.values());
		model.addAttribute("sauces", Sauce.values());
		model.addAttribute("order", order);
		return "toppings";
	}

	private List<Topping> convertToClientTopping(AvailableTopping[] availableToppings) {
		List<Topping> toppings = new ArrayList<>();
		for (AvailableTopping availableTopping : availableToppings) {
			Topping newInstance = new Topping();
			newInstance.setIngredient(availableTopping.getIngredient());
			newInstance.setQuantity(0L);
			toppings.add(newInstance);
		}

		return toppings;
	}

	@RequestMapping(value = "/toppings", method = RequestMethod.GET)
	@ResponseBody
	public String[] topppings() {
		String[] result = restTemplate.getForObject("http://ORDER-SERVICE/toppings", String[].class);
		return result;
	}

	@RequestMapping("/submitOrder")
	public ModelAndView submitOrderAsync(@ModelAttribute("order") Order order)
			throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		BakedServerResponse bakedClientResponse = new BakedServerResponse();
		List<BakedServerResponse> bakedServerResponseList = new ArrayList<BakedServerResponse>();
		Map<String, Object> models = new HashMap<>();
		for (GANPizza pizza : order.getGanPizzaList()) {
			Mono<BakedServerResponse> bakedPizza = WebClient.create("http://localhost:8060").post().uri("/submit")
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).bodyValue(pizza).retrieve()
					.bodyToMono(BakedServerResponse.class);

			bakedPizza.subscribe(result -> {
				LOG.debug("PIZZA RESPONSE {}", result.getPizza());
				bakedClientResponse.setPizza(result.getPizza());
				bakedClientResponse.setCompletingThread(Thread.currentThread().getName());
				bakedClientResponse.setTimeMs(System.currentTimeMillis() - start);
			}, error -> {
				error.printStackTrace();
				bakedClientResponse.setError(true);
				bakedClientResponse.setCompletingThread(Thread.currentThread().getName());
				bakedClientResponse.setTimeMs(System.currentTimeMillis() - start);
			});
			CompletableFuture<BakedServerResponse> serverResponseFuture = bakedPizza.toFuture();
			bakedServerResponseList.add(serverResponseFuture.join());
		}

		LOG.debug("bakedServerResponse {}", bakedServerResponseList);
		models.put("bakedServerResponseList", bakedServerResponseList);
		return new ModelAndView("thankyou", models);

	}

	@ModelAttribute("order")
	public Order getOrder() {
		Order order = new Order();
		order.setGanPizzaList(new ArrayList<GANPizza>());
		return order;
	}
}

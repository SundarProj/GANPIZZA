package com.sundar.service;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.sundar.bean.GANPizza;
import com.sundar.bean.OrderStatus;
import com.sundar.bean.Topping;

@Service
public class OrderService {

	private final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	private List<Topping> ingredients;

	private static String FILE_NAME = "ingredients.csv";

	@PostConstruct
	private void init() {
		setIngredients(loadObjectList(Topping.class, FILE_NAME));
	}

	@SuppressWarnings("deprecation")
	private <T> List<T> loadObjectList(Class<T> type, String fileName) {
		try {
			CsvSchema schema = CsvSchema.emptySchema().withHeader();
			CsvMapper mapper = new CsvMapper();
			File file = new ClassPathResource(fileName).getFile();
			MappingIterator<T> readValues = mapper.reader(type).with(schema).readValues(file);
			return readValues.readAll();
		} catch (Exception e) {

			return Collections.emptyList();
		}
	}

	public List<Topping> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Topping> ingredients) {
		this.ingredients = ingredients;
	}

	
	public void adjustQuantity(List<Topping> toppingList) {
		for (Topping ing : toppingList) {
			adjustQuantity(ing.getIngredient(), ing.getQuantity());
		}
	}

	public void adjustQuantity(String ingredientName, long newValue) {
		for (Topping ing : getIngredients()) {
			if (ing.getIngredient().equalsIgnoreCase(ingredientName)) {
				ing.setQuantity(ing.getQuantity() - newValue);
				break;
			}
		}
	}

	public long getCurrentQuantity(String ingredientName) {
		long result = 0;
		for (Topping ing : getIngredients()) {
			if (ing.getIngredient().equalsIgnoreCase(ingredientName)) {
				result = ing.getQuantity();
				break;
			}
		}
		return result;
	}

	public synchronized GANPizza validateOrder(GANPizza pizza) {
		List<Topping> toppings = pizza.getToppings();
		for (Topping topping : toppings) {
			long currentQuantity = getCurrentQuantity(topping.getIngredient());
			LOG.debug("currentQuantity {} and toppingQuantity = {}",currentQuantity, topping.getQuantity());
			if (currentQuantity < topping.getQuantity()) {
				pizza.setStatus(OrderStatus.CANCEL);
				pizza.setMessage(String.format("Insufficient quantity %s", topping.getIngredient()));
				return pizza;
			}
		}
		pizza.setStatus(OrderStatus.OK);
		pizza.setMessage("Validation Successful");

		return pizza;
	}
}

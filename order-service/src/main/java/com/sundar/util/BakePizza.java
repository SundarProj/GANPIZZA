package com.sundar.util;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sundar.bean.GANPizza;

public class BakePizza implements Callable<GANPizza>{
	 
	private final Logger LOG = LoggerFactory.getLogger(BakePizza.class);
	
    private GANPizza pizza;
    private int bakeTime;
     
    public BakePizza(GANPizza pizza, int bakeTime) {
        this.pizza = pizza;
        this.bakeTime = bakeTime;
    }
     
    @Override
    public GANPizza call() throws Exception {
    	try {
    		Thread.sleep(bakeTime);
    	}catch (InterruptedException e) {
           LOG.error("Error while baking pizza:", e);
        }
        
        return pizza;
    }
     
}
 
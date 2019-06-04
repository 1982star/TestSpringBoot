package com.test.calculate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;

import geektime.demo.Calculator;

public class UserCalculator {
	private static Logger logger = LoggerFactory.getLogger(UserCalculator.class);
	
	public static void main(String[] args) {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();		

		Calculator calculator = new Calculator();
		Calculator.Producer producer = calculator.new Producer(queue);
		new Thread(producer).start();
		String input;
		try {
			while((input = (String)queue.take()) != null) {
				String output = calculator.calculate(input);
				System.out.println(output);
			}			
		} catch (InterruptedException e) {
			logger.error("Runtime error",e);
		}
	}

}

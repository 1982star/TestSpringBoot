package geektime.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
	private static Logger logger = LoggerFactory.getLogger(Calculator.class);
	
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	private Stack stack = new Stack();

	private static final String reg = "^\\d+$";
	
	private static final String WARNINFO1= "operator {} (position: {}): insufficient parameters";

	private Stack[] cache = new Stack[3];

	private int latest = -1;
	
//	private boolean isStop = false;

	private HashSet twoNumberSymbol = new HashSet() {
		{
			add("+");
			add("-");
			add("*");
			add("/");
		}
	};
	private HashSet oneNumberSymbol = new HashSet() {
		{
			add("sqrt");
		}
	};
	private HashSet noNumberSymbol = new HashSet() {
		{
			add("undo");
			add("clear");
		}
	};


	public String calculate(String input) throws InterruptedException {
		String[] elements = null;
		input = input.trim();
		elements = input.split("\\s+");
		for (int i = 0; i < elements.length; i++) {
			String element = elements[i];
			if (twoNumberSymbol.contains(element)) {
				if(stack.size()<2) {
					logger.warn(WARNINFO1,element,i+1);
					break;
				}
				BigDecimal second = new BigDecimal((String) stack.pop());				
				BigDecimal first = new BigDecimal((String) stack.pop());
				if (element.equals("+")) {
					first = first.add(second);
				} else if (element.equals("-")) {
					first = first.subtract(second);
				} else if (element.equals("*")) {
					first = first.multiply(second);
				} else {
					first = first.divide(second);
				}
				first = first.setScale(10);
				stack.push(first.stripTrailingZeros().toPlainString());
			} else if (oneNumberSymbol.contains(element)) {
				if (element.equals("sqrt")) {
					if (stack.isEmpty()) {
						logger.warn(WARNINFO1,element,i+1);
						break;
					}
					BigDecimal first = new BigDecimal((String) stack.pop());
					first = new BigDecimal(Math.sqrt(first.doubleValue()));
					first = first.setScale(10, RoundingMode.DOWN);
					stack.push(first.stripTrailingZeros().toPlainString());
				} else {
					if (latest == 0) {
						stack = cache[0];
					} else if (latest == 1) {
						stack = cache[1];
					}
				}
			} else if (noNumberSymbol.contains(element)) {
				if (element.equals("clear")) {
					stack.clear();
					if (latest == 0) {
						latest = 1;
						continue;
					}
					if (latest == 1) {
						latest = 2;
						continue;
					}
					if (latest == 2) {
						latest = 0;
						continue;
					}
					
				}else {
					// To-Do, null, set null
					if (latest == 0) {
						stack = (Stack) cache[2].clone();
						latest = 2;
						continue;
					}
					if (latest == 1) {
						stack = (Stack) cache[0].clone();
						latest = 0;
						continue;
					}					
					if (latest == 2) {
						stack = (Stack) cache[1].clone();
						latest = 1;
						continue;
					}
				}
			} else {
				//To-Do,support float, 15 precision
				if (element.matches(reg)) {
					stack.push(element);
				} else {
					throw new RuntimeException("Not valid input " + element + ".");
				}
			}
			if (latest == -1 || latest == 2) {
				latest = 0;
				cache[0] = (Stack) stack.clone();
			} else if (latest == 0){
				latest = 1;
				cache[1] = (Stack) stack.clone();
			} else {
				latest = 2;
				cache[2] = (Stack) stack.clone();
			}
		}

		StringBuilder sb = new StringBuilder();
		Enumeration items = stack.elements();
		while (items.hasMoreElements())
			sb.append(items.nextElement() + " ");
		return sb.toString().trim();
	}
	
	public class Producer implements Runnable {
		private BlockingQueue<String> queue;

		public Producer(BlockingQueue<String> q) {
			this.queue = q;
		}

		@Override
		public void run() {
			while (true) {
				try {
					String[] elements = null;
					Scanner scanner = new Scanner(System.in);
					String input = scanner.nextLine().trim();
					queue.put(input);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}




package com.test.calculate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import geektime.demo.Calculator;
import junit.framework.Assert;

public class TestCalculator {
	@Rule
    public TestName name= new TestName();
	
	@Before
	public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod " + name.getMethodName());
    }
	
	@After
	public void runAfterTestMethod() {
        System.out.println("@After - runAfterTestMethod " + name.getMethodName());
    }
	
	@Test
	public void testExample5() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("7 6", calculator.calculate("7 12 2 /"));
		Assert.assertEquals("42", calculator.calculate("*"));
		Assert.assertEquals("10.5", calculator.calculate("4 /"));
	}
	
	@Test
	public void testExample6() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("1 2 3 4 5", calculator.calculate("1 2 3 4 5"));
		Assert.assertEquals("1 2 3 20", calculator.calculate("*"));
		Assert.assertEquals("-1", calculator.calculate("clear 3 4 -"));
	}
	
	@Test
	public void testExample7() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("1 2 3 4 5", calculator.calculate("1 2 3 4 5"));
		Assert.assertEquals("120", calculator.calculate("* * * *"));
	}
	
	@Test
	public void testExample1() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("5 2", calculator.calculate("5 2"));
	}
	
	@Test
	public void testExample2() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("1.4142135623", calculator.calculate("2 sqrt"));
		Assert.assertEquals("3", calculator.calculate("clear 9 sqrt"));
	}
	
	@Test
	public void testExample3() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("3", calculator.calculate("5 2 -"));
		Assert.assertEquals("0", calculator.calculate("3 -"));
		Assert.assertEquals("", calculator.calculate("clear"));
	}
	
	@Test
	public void testExample4() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("5 4 3 2", calculator.calculate("5 4 3 2"));
		Assert.assertEquals("20", calculator.calculate("undo undo *"));
		Assert.assertEquals("100", calculator.calculate("5 *"));
		Assert.assertEquals("20 5", calculator.calculate("undo"));
	}
	
	@Test
	public void testExample8() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("11", calculator.calculate("1 2 3 * 5 + * * 6 5"));
	}
	
	@Test
	public void testExample9() throws InterruptedException {
		Calculator calculator = new Calculator();
		Assert.assertEquals("5 4 3 2", calculator.calculate("5 4 3 2"));
		Assert.assertEquals("5 4 6", calculator.calculate("clear undo clear undo *"));
		Assert.assertEquals("5 4 30", calculator.calculate("5 *"));
	}
}

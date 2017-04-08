package edu.jyu.core;

import org.junit.Test;

import edu.jyu.bean.A;
import edu.jyu.bean.B;

public class TestApplicationContext {

	@Test
	public void test() {
		BeanFactory ac = new ClassPathXmlApplicationContext("/applicationContext.xml");
		A a = (A) ac.getBean("A");
		A a1 = (A) ac.getBean("A");
		B b = (B) ac.getBean("B");
		B b1 = (B) ac.getBean("B");
		System.out.println(b.getAName() + ":" + b.getAge());
		System.out.println("a==a1 : "+(a==a1));
		System.out.println("b==b1 : "+(b==b1));
	}
}

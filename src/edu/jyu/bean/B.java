package edu.jyu.bean;

public class B {
	private A a;
	private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public void setA(A a) {
		this.a = a;
	}
	public String getAName(){
		return a.getName();
	}
}

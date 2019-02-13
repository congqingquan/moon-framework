package org.moon.framework.context.test;

import org.moon.framework.beans.annotation.*;
import org.moon.framework.beans.annotation.functional.*;
import org.moon.framework.core.enums.ScopeSelector;

@Scope(scope = ScopeSelector.SINGLETON)
@Alias(aliases = {"beanAliases1", "beanAliases2", "beanAliases3"})
@LazyLoad
@Component
public class Person {

	@Inject("sf")
	private Foot foot;

	@Inject
	private Hand hand;

	public Person() {
		System.out.println("Person constructor method");
	}

	private String name;
	
	private Integer phone;
	
	private char sex;
	
//	@InitMethod
	public void m1() {
		System.out.println("init method: m1");
	}
//	@InitMethod
	public void m2() {
		System.out.println("init method: m2");
	}

	@DestroyMethod
	public void m3() {
		System.out.println("person destroy method m3 exec");
	}
	@DestroyMethod
	public void m4() {
		System.out.println("person destroy method m4 exec");
	}
	
	public void study() {}
	public void sleep() {}

	public Foot getFoot() {
		return foot;
	}

	public Hand getHand() {
		return hand;
	}

	@Override
	public String toString() {
		return "Person";
	}
}
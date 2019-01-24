package org.moon.framework.beans.test;

import org.moon.framework.core.annotation.Alias;
import org.moon.framework.core.annotation.Component;
import org.moon.framework.core.annotation.DestroyMethod;
import org.moon.framework.core.annotation.InitMethod;
import org.moon.framework.core.annotation.LazyLoad;
import org.moon.framework.core.annotation.Scope;

//@Scope(scope = ScopeSelector.PROTOTYPE)
@Scope
@Alias(aliases = {"beanAliases1", "beanAliases2", "beanAliases3"})
@LazyLoad
@Component
public class Person {
	
	private String name;
	
	private Integer phone;
	
	private char sex;
	
	@InitMethod
	public void m1() {}
	@InitMethod
	public void m2() {}
	
	@DestroyMethod
	public void m3() {}
	@DestroyMethod
	public void m4() {}
	
	public void study() {}
	public void sleep() {}
}
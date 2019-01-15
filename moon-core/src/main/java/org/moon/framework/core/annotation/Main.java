package org.moon.framework.core.annotation;

@MComponent
class Parent {

}

class Son extends Parent {

}

@MApi("testApi")
public class Main {
	public static void main(String[] args) {
		MApi mApi = Main.class.getDeclaredAnnotation(MApi.class);
		MComponent moonComponent = Son.class.getAnnotation(MComponent.class);

		System.out.println(moonComponent);
		System.out.println(mApi.value());
	}
}

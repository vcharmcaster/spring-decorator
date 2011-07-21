package com.schlimm.decorator.resolver.longtwochains;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class MyDelegate implements MyServiceInterface {

	@Override
	public MyServiceInterface getDelegateObject() {
		return null;
	}

	@Override
	public String sayHello() {
		return "Hello";
	}

}
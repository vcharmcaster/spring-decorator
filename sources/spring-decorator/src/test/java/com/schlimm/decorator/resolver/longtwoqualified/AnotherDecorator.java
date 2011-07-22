package com.schlimm.decorator.resolver.longtwoqualified;

import javax.decorator.Decorator;
import javax.decorator.Delegate;

import org.springframework.beans.factory.annotation.Qualifier;

import com.schlimm.decorator.resolver.longsinglechain.MyServiceInterface;

@Decorator
@Qualifier("another")
public class AnotherDecorator implements MyServiceInterface {
	
	@Delegate @Qualifier("another")
	private MyServiceInterface delegateInterface;

	@Override
	public MyServiceInterface getDelegateObject() {
		return delegateInterface;
	}

	@Override
	public String sayHello() {
		return delegateInterface.sayHello();
	}

}

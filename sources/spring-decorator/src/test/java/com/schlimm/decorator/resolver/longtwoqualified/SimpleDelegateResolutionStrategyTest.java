package com.schlimm.decorator.resolver.longtwoqualified;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.schlimm.decorator.DelegateResolutionStrategy;
import com.schlimm.decorator.SimpleDelegateResolutionStrategy;
import com.schlimm.decorator.resolver.DecoratorInfo;


@ContextConfiguration("/test-context-decorator-resolver-long-two-qualified-chains.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleDelegateResolutionStrategyTest {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private DelegateResolutionStrategy delegateResolutionStrategy;
	
	@Before
	public void setUp() {
		delegateResolutionStrategy = new SimpleDelegateResolutionStrategy();
	}
	
	@Test
	public void testChaining_MyDecoratorLeadsToMyDelegate() {
		DecoratorInfo decoratorInfo = null;
		try {
			decoratorInfo = new DecoratorInfo("myDecorator", beanFactory.getBeanDefinition("myDecorator"), Class.forName("com.schlimm.decorator.resolver.longtwoqualified.MyDecorator"));
		} catch (NoSuchBeanDefinitionException e) {
			TestCase.fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			TestCase.fail(e.getMessage());
		}
		Assert.isTrue(delegateResolutionStrategy.getRegisteredDelegate(beanFactory, decoratorInfo).equals("myDelegate"));
	}

	@Test
	public void testChaining_MyDecorator2LeadsToMyDelegate() {
		DecoratorInfo decoratorInfo = null;
		try {
			decoratorInfo = new DecoratorInfo("myDecorator2", beanFactory.getBeanDefinition("myDecorator2"), Class.forName("com.schlimm.decorator.resolver.longtwoqualified.MyDecorator2"));
		} catch (NoSuchBeanDefinitionException e) {
			TestCase.fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			TestCase.fail(e.getMessage());
		}
		Assert.isTrue(delegateResolutionStrategy.getRegisteredDelegate(beanFactory, decoratorInfo).equals("myDelegate"));
	}
	
	@Test
	public void testChaining_AnotherDecoratorLeadsToAnotherDelegate() {
		DecoratorInfo decoratorInfo = null;
		try {
			decoratorInfo = new DecoratorInfo("anotherDecorator", beanFactory.getBeanDefinition("anotherDecorator"), Class.forName("com.schlimm.decorator.resolver.longtwoqualified.AnotherDecorator"));
		} catch (NoSuchBeanDefinitionException e) {
			TestCase.fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			TestCase.fail(e.getMessage());
		}
		Assert.isTrue(delegateResolutionStrategy.getRegisteredDelegate(beanFactory, decoratorInfo).equals("anotherDelegate"));
	}
	
	@Test
	public void testChaining_AnotherDecorator2LeadsToAnotherDelegate() {
		DecoratorInfo decoratorInfo = null;
		try {
			decoratorInfo = new DecoratorInfo("anotherDecorator", beanFactory.getBeanDefinition("anotherDecorator"), Class.forName("com.schlimm.decorator.resolver.longtwoqualified.AnotherDecorator2"));
		} catch (NoSuchBeanDefinitionException e) {
			TestCase.fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			TestCase.fail(e.getMessage());
		}
		Assert.isTrue(delegateResolutionStrategy.getRegisteredDelegate(beanFactory, decoratorInfo).equals("anotherDelegate"));
	}
	
}

package com.schlimm.springcdi.decorator.strategies.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ClassUtils;

import com.schlimm.springcdi.decorator.DecoratorAwareBeanFactoryPostProcessorException;
import com.schlimm.springcdi.decorator.strategies.DelegateResolutionStrategy;
import com.schlimm.springcdi.model.DecoratorInfo;
import com.schlimm.springcdi.model.DelegateField;
import com.schlimm.springcdi.resolver.rules.DelegateDependencyDescriptorTag;
import com.schlimm.springcdi.resolver.rules.RuleUtils;

public class SimpleDelegateResolutionStrategy implements DelegateResolutionStrategy {

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public String getRegisteredDelegate(ConfigurableListableBeanFactory beanFactory, DecoratorInfo decoratorInfo) {
		DelegateField arbitraryDelegateField = decoratorInfo.getDelegateFields().get(0);
		DependencyDescriptor desc = RuleUtils.createRuleBasedDescriptor(arbitraryDelegateField.getDeclaredField(), new Class[] { DelegateDependencyDescriptorTag.class });
		List<String> registeredDelegates = new ArrayList<String>();
		String[] candidateNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, arbitraryDelegateField.getDeclaredField().getType(), true, false);
		for (String candidate : candidateNames) {
			BeanDefinition bd = beanFactory.getBeanDefinition(candidate);
			// Annotierte Bean aus dem Classpath
			if (bd instanceof AnnotatedBeanDefinition) {
				AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) bd;
				// Kein @Decorator
				if (!DecoratorInfo.isDecorator(abd)) {
					Class decoratorClass = null;
					try {
						decoratorClass = ClassUtils.forName(abd.getBeanClassName(), this.getClass().getClassLoader());
					} catch (Exception e) {
						throw new DecoratorAwareBeanFactoryPostProcessorException("Could not find decorator class: " + abd.getBeanClassName(), e);
					}
					// Wenn es ein target object ist, dann die proxy bean definition ziehen, die auch als delegate injected werden
					// soll
					if (candidate.startsWith("scopedTarget.")) {
						candidate = candidate.replace("scopedTarget.", "");
					}
					// Die aktuelle bean definition muss auf den delegate dependency descriptor passen
					if ((((DefaultListableBeanFactory) beanFactory).isAutowireCandidate(candidate, desc))) {
						registeredDelegates.add(candidate);
					}
				}
			}
		}
		if (registeredDelegates.size() > 1) {
			throw new DecoratorAwareBeanFactoryPostProcessorException("Could not find unique delegate for decorator info: " + decoratorInfo.toString());
		}
		return registeredDelegates.get(0);
	}

}
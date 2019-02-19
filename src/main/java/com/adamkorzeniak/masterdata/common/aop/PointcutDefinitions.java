package com.adamkorzeniak.masterdata.common.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutDefinitions {
	
	@Pointcut("execution(* com.adamkorzeniak..*Controller.*(..))")
	public void controllers() {}
	
	@Pointcut("execution(* com.adamkorzeniak.masterdata.configuration..*(..))")
	public void configurations() {}
	
	@Pointcut("execution(* com.adamkorzeniak..*(..))")
	public void customMethods() {}
	
	@Pointcut("customMethods() && !controllers() && !configurations()")
	public void other() {}
	
	@Pointcut("execution(* *.GlobalResponseEntityExceptionHandler.*(..))")
	public void exceptionHandlers() {}
}

package com.adamkorzeniak.masterdata.common.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutDefinitions {
	
	@Pointcut("execution(* com.adamkorzeniak..*Controller.*(..))")
	public void controllers() {}
	
	@Pointcut("execution(* com.adamkorzeniak..*Service*.*(..))")
	public void services() {}
	
	@Pointcut("execution(* com.adamkorzeniak..*Repository*.*(..))")
	public void repositories() {}
	
	@Pointcut("execution(* com.adamkorzeniak..*Helper*.*(..))")
	public void helpers() {}
	
	@Pointcut("services() || repositories() || helpers()")
	public void logic() {}
	
	@Pointcut("execution(* com.adamkorzeniak..*.*(..))")
	public void custom() {}
	
	@Pointcut("execution(* com.adamkorzeniak..GlobalResponseEntityExceptionHandler.*(..))")
	public void exceptionHandlers() {}
}

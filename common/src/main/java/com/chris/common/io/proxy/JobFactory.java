package com.chris.common.io.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JobFactory {

	public static Job getJob() {
		Worker worker = new Worker();// 这是代理，可以代理接口指定的工作

		if (!(worker instanceof Job)) {// 判断是否有资格当这些工作的代理
			return null;
		}

		InvocationHandler handler = new Handler(worker); // 这是实际工作的人
		
		Class[] interfaces = new Class[] { Job.class };
		ClassLoader loader = JobFactory.class.getClassLoader();
		Job proxy = (Job) Proxy.newProxyInstance(loader, interfaces, handler);
		return proxy;
	}

	public static void main(String[] args) throws Exception {
		Job job = JobFactory.getJob();
		//job.doSomething();
		job.play();
	}
}

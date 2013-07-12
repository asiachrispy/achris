package com.chris.common.io.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {

	private Worker worker;

	public Handler(Worker worker) {
		this.worker = worker;
	}

	public Object invoke(Object obj, Method mtd, Object[] objs)
			throws Throwable {
		Object res = null;
		System.out.println("check permission");
		res = mtd.invoke(worker, objs);
		System.out.println("get the saraly");
		return res;
	}

}

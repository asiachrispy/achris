package com.chris.common.io.proxy;

public class StaticProxy implements Job {
	private Worker worker;

	public StaticProxy(Worker worker) {
		this.worker = worker;
	}

	public void doSomething() {

		worker.doSomething();
	}

	public static void main(String[] args) {
		Job job = new StaticProxy(new Worker()); // 劳工使用方找到代理，让它进行code的工作
		job.doSomething();
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}
}

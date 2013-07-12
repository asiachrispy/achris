package com.chris.common.design;

public class StateClient {

	public static void main(String[] args) {
		State state = new ConcreteStateA();
		Context context = new Context(state);
		context.request();
		context.request();
		context.request();
		context.request();

	}

}

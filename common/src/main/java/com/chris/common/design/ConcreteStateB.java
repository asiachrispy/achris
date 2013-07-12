package com.chris.common.design;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 11-12-1
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
class ConcreteStateB implements State {

	public void handle(Context ctx) {
		System.out.println("handle by ConcreteStateB");
		if (ctx != null) {
			ctx.ChangeState(new ConcreteStateA());
		}

	}

}

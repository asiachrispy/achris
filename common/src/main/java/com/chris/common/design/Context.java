package com.chris.common.design;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 11-12-1
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
class Context {
	private State _state;

	public Context(State state) {
		_state = state;
	}

	public void request() {
		if (_state != null) {
			_state.handle(this);
		}
	}

	public void ChangeState(State s) {
		if (_state != null) {
			_state = null;
		}
		_state = s;
	}
}

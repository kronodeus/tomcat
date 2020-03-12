package com.vc.transaction;

public class ServletTransaction extends Transaction {
	protected ServletTransaction(Thread thread) {
		super(thread, Type.SERVLET);
	}

	@Override
	protected void onBegin() {
		System.out.printf("%s transaction started on thread: %s\n", getType(), getThread());
	}

	@Override
	protected void onEnd() {
		System.out.printf("%s transaction ended on thread: %s\n", getType(), getThread());
	}
}

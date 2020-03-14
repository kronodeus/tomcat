package com.vc.transaction;

import com.vc.session.Session;

public class ServletGetTransaction extends Transaction {
	public ServletGetTransaction(Session session, Thread thread, Type type, String url) {
		super(session, thread, type, url);
	}

	@Override
	protected void onBegin() {
	}

	@Override
	protected void onEnd() {
	}
}

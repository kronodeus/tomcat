package com.vc.transaction;

import com.vc.session.Session;

public interface TransactionConstructor {
	Transaction construct(Session session, Thread thread, Transaction.Type type, String url);
}

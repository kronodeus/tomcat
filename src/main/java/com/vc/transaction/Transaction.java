package com.vc.transaction;

import java.util.HashMap;
import java.util.Map;

import com.vc.log.Log;
import com.vc.session.Session;

public abstract class Transaction {
	public enum Type {
		SERVLET_GET(ServletGetTransaction::new),
		SERVLET_POST(ServletPostTransaction::new),
		DEFAULT(DefaultTransaction::new),
		LOGIN(LoginTransaction::new);

		private final TransactionConstructor constructor;

		Type(TransactionConstructor constructor) {
			this.constructor = constructor;
		}

		public Transaction create(Session session, Thread thread, String url) {
			return constructor.construct(session, thread, this, url);
		}
	}

	private static final Map<Thread, Transaction> TRANSACTIONS = new HashMap<>();

	private final Session session;
	private final Thread thread;
	private final Type type;
	private final String url;

	protected Transaction(Session session, Thread thread, Type type, String url) {
		this.session = session;
		this.thread = thread;
		this.type = type;
		this.url = url;
	}

	protected abstract void onBegin();

	protected abstract void onEnd();

	@Override
	public int hashCode() {
		return thread.hashCode();
	}

	@Override
	public String toString() {
		return String.format(
				"%s (Type: %s, Thread: %s, URL: %s, Session: %s)",
				getClass().getName(),
				type,
				thread.getName(),
				url,
				session
		);
	}

	public static void run(Session session, Type type, String url, TransactionRunnable runnable) throws Exception {
		try {
			begin(session, type, url);
			runnable.run();
		} finally {
			end();
		}
	}

	public static synchronized Transaction get() {
		return TRANSACTIONS.get(Thread.currentThread());
	}

	private static synchronized void begin(Session session, Type type, String url) throws TransactionException {
		Thread thread = Thread.currentThread();

		if (TRANSACTIONS.containsKey(thread))
			throw new TransactionException("Attempted to start new transaction before previous had finished");

		Transaction transaction = type.create(session, thread, url);
		TRANSACTIONS.put(thread, transaction);
		transaction.onBegin();

		Log.info("%s transaction started [%s]", transaction.type, transaction.url);
	}

	private static synchronized void end() throws TransactionException {
		Thread thread = Thread.currentThread();
		Transaction transaction = TRANSACTIONS.get(thread);

		if (transaction == null)
			throw new TransactionException("Attempted to end a non-existent transaction");

		transaction.onEnd();
		TRANSACTIONS.remove(thread);

		Log.info("%s transaction ended [%s]", transaction.type, transaction.url);
	}
}

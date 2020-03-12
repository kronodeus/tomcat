package com.vc.transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class Transaction {
	public enum Type {
		SERVLET(ServletTransaction::new);

		private final Function<Thread, Transaction> transactionCreator;

		Type(Function<Thread, Transaction> transactionCreator) {
			this.transactionCreator = transactionCreator;
		}

		public Transaction create(Thread thread) {
			return transactionCreator.apply(thread);
		}
	}

	private static final Map<Thread, Transaction> TRANSACTIONS = new HashMap<>();

	private final Thread thread;
	private final Type type;

	protected Transaction(Thread thread, Type type) {
		this.thread = thread;
		this.type = type;
	}

	protected abstract void onBegin();

	protected abstract void onEnd();

	protected final Thread getThread() {
		return thread;
	}

	protected final Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return thread.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s (Type: %s, Thread: %s)", getClass().getName(), type, thread.getName());
	}

	public static void run(Type type, TransactionRunnable runnable) throws Exception {
		try {
			begin(type);
			runnable.run();
		} finally {
			end();
		}
	}

	public static synchronized Transaction get() {
		return TRANSACTIONS.get(Thread.currentThread());
	}

	private static synchronized void begin(Type type) throws TransactionException {
		Thread thread = Thread.currentThread();

		if (TRANSACTIONS.containsKey(thread))
			throw new TransactionException(String.format("Attempted to start new transaction before previous had finished on thread: %s", thread));

		Transaction transaction = type.create(thread);
		TRANSACTIONS.put(thread, transaction);
		transaction.onBegin();
	}

	private static synchronized void end() throws TransactionException {
		Thread thread = Thread.currentThread();
		Transaction transaction = TRANSACTIONS.get(thread);

		if (transaction == null)
			throw new TransactionException(String.format("Attempted to end a transaction, but it was not found on thread: %s", thread));

		transaction.onEnd();
		TRANSACTIONS.remove(thread);
	}
}

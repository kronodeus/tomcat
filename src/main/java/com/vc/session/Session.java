package com.vc.session;

import com.vc.log.Log;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Session {
	private static final String GUEST_ID = "guest";
	private static final int MAX_INACTIVE_TIME = 5;

	private final String id;
	private final boolean isActive;

	protected Session(HttpSession session) {
		this.isActive = session != null;
		this.id = isActive ? session.getId() : GUEST_ID;
	}

	public String getId() {
		return id;
	}

	public boolean isActive() {
		return isActive;
	}

	@Override
	public String toString() {
		return id;
	}

	public static Session get(HttpServletRequest request) {
		return new Session(request.getSession(false));
	}

	public static Session getNew(HttpServletRequest request) {
		invalidate(request);
		return create(request);
	}

	private static void invalidate(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			Log.info("Session invalidated: %s", session.getId());
		}
	}

	private static Session create(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(MAX_INACTIVE_TIME);
		Log.info("Session created: %s", session.getId());

		return new Session(session);
	}
}

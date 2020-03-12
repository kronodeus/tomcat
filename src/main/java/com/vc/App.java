package com.vc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.vc.bootstrap.ServletLoader;

public class App {
	private static final String HOST_NAME = "localhost";
	private static final String CONTEXT_PATH = "";
	private static final String APP_BASE = ".";
	private static final int PORT = 8080;

	public static void main(String[] args) {
		Tomcat tomcat = new Tomcat();
		tomcat.setHostname(HOST_NAME);
		tomcat.setPort(PORT);
		tomcat.getHost().setAppBase(APP_BASE);
		tomcat.setConnector(tomcat.getConnector());

		ServletLoader.load(tomcat.addWebapp(CONTEXT_PATH, APP_BASE));

		try {
			tomcat.start();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		} finally {
			try {
				tomcat.stop();
			} catch (LifecycleException e) {
				e.printStackTrace();
			}
		}
	}
}

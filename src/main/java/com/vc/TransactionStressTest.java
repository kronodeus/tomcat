package com.vc;


import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class TransactionStressTest {
	@Test
	public void stressTest() {
		for (int i = 0; i < 100; i++)
			new Thread(this::makeManyRequests).start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException ignore) {
		}
	}

	private void makeManyRequests() {
		for (int i = 0; i < 10000; i++)
			makeSingleRequest();
	}

	private void makeSingleRequest() {
		try {
			URL obj = new URL("http://localhost:8080/transaction");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			assertEquals(HttpURLConnection.HTTP_OK, responseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

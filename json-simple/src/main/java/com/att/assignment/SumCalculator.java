package com.att.assignment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SumCalculator {

	public static void main(String[] args) {
		int totalSum = 0;
		try {

			String uri = args[0];
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			InputStream in = connection.getInputStream();
			JsonParser jsonParser = new JsonParser();
			JsonArray jArray = (JsonArray) jsonParser.parse(new InputStreamReader(in, "UTF-8"));
			int docCnt = 1;
			for (JsonElement jsonArrayElement : jArray) {
				System.out.println("Printing details of JSON Document " + docCnt++);
				System.out.println("--------------------------------");
				JsonObject jsonObject = (JsonObject) jsonArrayElement;
				System.out.println("Keys are:: ");
				int sum = 0;
				for (Map.Entry<String, JsonElement> m : jsonObject.entrySet()) {

					if (m.getKey().equals("numbers")) {
						JsonElement jsonElement = jsonObject.get("numbers");
						JsonArray jsonArray = jsonElement.getAsJsonArray();
						for (int i = 0; i < jsonArray.size(); i++) {
							sum += jsonArray.get(i).getAsInt();
						}
						totalSum += sum;
					}
					System.out.println(m.getKey());
				}
				System.out.println("Sum for the document is ::" + sum);
				System.out.println("--------------------------------");
			}
			System.out.println("Total Sum for all the Documents is ::" + totalSum);

			in.close();
			connection.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

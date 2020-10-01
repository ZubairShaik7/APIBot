import org.jibble.pircbot.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class APIBot extends PircBot {
	
	public APIBot () {
		this.setName("Weather Bot");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equalsIgnoreCase("time")) {
             String time = new java.util.Date().toString();
             sendMessage(channel, sender + ": The time is now " + time);
             getWeather();
            }
        if (message.equalsIgnoreCase("weather")) {
            String weather = getWeather();
            sendMessage(channel, sender + weather);
            
           }
     }
	
	public String getWeather() {
		String output = null;
		HttpURLConnection connection;
		BufferedReader reader;
		StringBuffer response = new StringBuffer();
		String line;
		
		try {
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Austin&appid=4ab13b70934a801d39847a85bf9918d8");
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
			output = jsonConverter(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//connection.disconnect();
		}
		return output;
	}
	
	public String jsonConverter(String response) {
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(response);
		JsonObject jsonObject = jsonTree.getAsJsonObject();
		JsonElement weather = jsonObject.get("weather");
		JsonArray weatherArray = weather.getAsJsonArray();
		JsonObject weatherObject = (weatherArray.get(0)).getAsJsonObject();
		JsonElement main = weatherObject.get("main");
		JsonElement description = weatherObject.get("description");
		
		String output = new String(" It looks like it's going to be " + main.toString() + " with " + description.toString());
		return output;
	}
	
}

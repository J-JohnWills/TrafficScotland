package jjwilliams.trafficscotland.data;

// Jamie Williams : S2029548

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import jjwilliams.trafficscotland.models.TrafficScotlandFeed;

public class Connector {
  private String url;
  private TrafficScotlandFeed trafficScotlandFeed;

  public Connector(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public TrafficScotlandFeed getTrafficScotlandFeed() {

    InputStream inputStream = null;

    try {
      URL url = new URL(getUrl());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setReadTimeout(15000);
      connection.setConnectTimeout(15000);
      connection.setRequestMethod("GET");
      connection.setDoInput(true);
      connection.connect();
      inputStream = connection.getInputStream();

      TrafficScotlandPullParser pullParser = new TrafficScotlandPullParser();
      pullParser.setInputStream(inputStream);
      trafficScotlandFeed = pullParser.parseTrafficScotlandFeed();

      inputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return trafficScotlandFeed;
  }
}

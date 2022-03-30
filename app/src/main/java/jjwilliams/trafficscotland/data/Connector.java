package jjwilliams.trafficscotland.data;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connector {
  private String url;

  public Connector(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public InputStream getTrafficScotlandFeed() {

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
    } catch (Exception e) {
      e.printStackTrace();
    }

    return inputStream;
  }
}

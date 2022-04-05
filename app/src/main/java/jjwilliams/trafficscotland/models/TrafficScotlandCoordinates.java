package jjwilliams.trafficscotland.models;

// Jamie Williams : S2029548

import com.google.android.gms.maps.model.LatLng;

public class TrafficScotlandCoordinates{
  private double latitude;
  private double longitude;

  public TrafficScotlandCoordinates() {
    this.latitude = 0;
    this.longitude = 0;
  }

  public TrafficScotlandCoordinates(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public LatLng getCoordinates() {
    return new LatLng(latitude, longitude);
  }

  @Override
  public String toString() {
    return "TrafficScotlandCoordinates{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
  }
}

package jjwilliams.trafficscotland.models;

// Jamie Williams : S2029548

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class TrafficScotlandItem {

  private String title;
  private String description;
  private String link;
  private TrafficScotlandCoordinates coordinates;
  private Date datePublished;
  private Date startDate;
  private Date endDate;
  private long duration;
  private TrafficScotlandType type;

  // Constructors
  public TrafficScotlandItem() {
    this.title = "";
    this.description = "";
    this.link = "";
    this.coordinates = new TrafficScotlandCoordinates();

    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    datePublished = today.getTime();
    startDate = today.getTime();
    endDate = today.getTime();
    duration = 0;
    type = TrafficScotlandType.ROADWORKS;
  }

  public TrafficScotlandItem(String title, String description, String link,
                             TrafficScotlandCoordinates coordinates, Date datePublished,
                             Date startDate, Date endDate, long duration, TrafficScotlandType type) {
    this.title = title;
    this.description = description;
    this.link = link;
    this.coordinates = coordinates;
    this.datePublished = datePublished;
    this.startDate = startDate;
    this.endDate = endDate;
    this.duration = duration;
    this.type = type;
  }

  // Getters and setters
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  // TODO: Refactor Coordinates
  public LatLng getCoordinates() {
    return coordinates.getCoordinates();
  }

  public void setCoordinates(double latitude, double longitude) {
    this.coordinates = new TrafficScotlandCoordinates(latitude, longitude);
  }

  public Date getDatePublished() {
    return datePublished;
  }

  public void setDatePublished(Date datePublished) {
    this.datePublished = datePublished;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public TrafficScotlandType getType() {
    return type;
  }

  public void setType(TrafficScotlandType type) {
    this.type = type;
  }

  // Debug
  @Override
  public String toString() {
    return "TrafficScotlandItem{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", link='" + link + '\'' +
            ", coordinates=" + coordinates +
            ", datePublished=" + datePublished +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", type=" + type +
            '}';
  }
}


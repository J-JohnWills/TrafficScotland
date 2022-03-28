package jjwilliams.trafficscotland.models;

// Jamie Williams : S2029548

import java.util.Calendar;
import java.util.Date;

public class TrafficScotlandItem {

  private String item;
  private String description;
  private String link;
  private TrafficScotlandCoordinates coordinates;
  private Date datePublished;
  private Date startDate;
  private Date endDate;
  private TrafficScotlandItemType type;

  // Constructors
  public TrafficScotlandItem() {
    this.item = "";
    this.description = "";
    this.link = "";
    this.coordinates = new TrafficScotlandCoordinates();

    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    datePublished = today.getTime();
    startDate = today.getTime();
    endDate = today.getTime();
    type = TrafficScotlandItemType.ROADWORKS;
  }

  public TrafficScotlandItem(String item, String description, String link,
                             TrafficScotlandCoordinates coordinates, Date datePublished,
                             Date startDate, Date endDate, TrafficScotlandItemType type) {
    this.item = item;
    this.description = description;
    this.link = link;
    this.coordinates = coordinates;
    this.datePublished = datePublished;
    this.startDate = startDate;
    this.endDate = endDate;
    this.type = type;
  }

  // Getters and setters
  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
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
  public TrafficScotlandCoordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(TrafficScotlandCoordinates coordinates) {
    this.coordinates = coordinates;
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

  public TrafficScotlandItemType getType() {
    return type;
  }

  public void setType(TrafficScotlandItemType type) {
    this.type = type;
  }

  // Debug
  @Override
  public String toString() {
    return "TrafficScotlandItem{" +
        "item='" + item + '\'' +
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

enum TrafficScotlandItemType {
  CURRENT_INCIDENT,
  ROADWORKS,
  PLANNED_ROADWORKS
}

package jjwilliams.trafficscotland.models;

// Jamie Williams : S2029548

import java.util.ArrayList;

public class TrafficScotlandFeed {

  private String title;
  private String description;
  private String link;
  private int ttl;
  private TrafficScotlandType type;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;

  // Constructors
  public TrafficScotlandFeed() {
    this.title = "";
    this.description = "";
    this.link = "";
    this.ttl = 0;
    this.type = TrafficScotlandType.ROADWORKS;
    this.trafficScotlandItems = new ArrayList<>();
  }

  public TrafficScotlandFeed(String title, String description, String link,
                             int ttl, TrafficScotlandType type,
                             ArrayList<TrafficScotlandItem> trafficScotlandItems) {

    this.title = title;
    this.description = description;
    this.link = link;
    this.ttl = ttl;
    this.type = type;
    this.trafficScotlandItems = trafficScotlandItems;
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

  public int getTtl() {
    return ttl;
  }

  public void setTtl(int ttl) {
    this.ttl = ttl;
  }

  public TrafficScotlandType getType() {
    return type;
  }

  public void setType(TrafficScotlandType type) {
    this.type = type;
  }

  public ArrayList<TrafficScotlandItem> getTrafficScotlandItems() {
    return trafficScotlandItems;
  }

  public void setTrafficScotlandItems(ArrayList<TrafficScotlandItem> trafficScotlandItems) {
    this.trafficScotlandItems = trafficScotlandItems;
  }

  // Methods
  public void addItems(TrafficScotlandItem item) {
    trafficScotlandItems.add(item);
  }

  // Debug

  @Override
  public String toString() {
    return "TrafficScotlandFeed{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", link='" + link + '\'' +
            ", ttl=" + ttl +
            ", type=" + type +
            ", trafficScotlandItems=" + trafficScotlandItems +
            '}';
  }
}


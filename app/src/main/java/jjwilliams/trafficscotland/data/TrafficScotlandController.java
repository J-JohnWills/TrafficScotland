package jjwilliams.trafficscotland.data;

// Jamie Williams : S2029548

import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;
import jjwilliams.trafficscotland.models.TrafficScotlandType;

public class TrafficScotlandController {

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();

  public TrafficScotlandFeed getRoadworks() {
    String roadworks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    Connector connector = new Connector(roadworks);
    trafficScotlandFeed = connector.getTrafficScotlandFeed();
    trafficScotlandFeed.setType(TrafficScotlandType.ROADWORKS);

    for (TrafficScotlandItem item : trafficScotlandFeed.getTrafficScotlandItems()) {
      item.setType(TrafficScotlandType.ROADWORKS);
    }

    return trafficScotlandFeed;
  }

  public TrafficScotlandFeed getPlannedRoadworks() {
    String plannedRoadworks = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    Connector connector = new Connector(plannedRoadworks);
    trafficScotlandFeed = connector.getTrafficScotlandFeed();
    trafficScotlandFeed.setType(TrafficScotlandType.PLANNED_ROADWORKS);

    for (TrafficScotlandItem item : trafficScotlandFeed.getTrafficScotlandItems()) {
      item.setType(TrafficScotlandType.PLANNED_ROADWORKS);
    }

    return trafficScotlandFeed;
  }

  public TrafficScotlandFeed getCurrentIncidents() {
    String currentIncidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    Connector connector = new Connector(currentIncidents);
    trafficScotlandFeed = connector.getTrafficScotlandFeed();
    trafficScotlandFeed.setType(TrafficScotlandType.CURRENT_INCIDENT);

    for (TrafficScotlandItem item : trafficScotlandFeed.getTrafficScotlandItems()) {
      item.setType(TrafficScotlandType.CURRENT_INCIDENT);
    }
    return trafficScotlandFeed;
  }
}

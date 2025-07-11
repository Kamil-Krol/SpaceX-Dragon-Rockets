package spacex.summary;

import spacex.model.MissionStatus;
import spacex.model.RocketStatus;

import java.util.List;

public record MissionSummary(String name, MissionStatus status, int rocketCount, List<RocketInfo> rockets) {
    public record RocketInfo(String name, RocketStatus status) {}
}

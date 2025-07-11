package spacex.summary;

import spacex.model.RocketStatus;

import java.util.List;

public record MissionSummary(String name, int rocketCount, List<RocketInfo> rockets) {
    public record RocketInfo(String name, RocketStatus status) {}
}

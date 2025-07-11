package spacex.repository;

import spacex.model.Mission;
import spacex.model.Rocket;
import spacex.model.RocketStatus;

public interface RocketRepository {
    Rocket addRocket(String name);
    Rocket getRocket(String name);
    void assignToMission(String rocketName, Mission mission);
    void changeRocketStatus(String name, RocketStatus status);
}

package spacex.repository;

import spacex.model.Mission;
import spacex.model.MissionStatus;
import spacex.model.Rocket;
import spacex.model.RocketStatus;

import java.util.HashMap;
import java.util.Map;

public class RocketRepositoryImpl implements RocketRepository {

    private final Map<String, Rocket> rockets = new HashMap<>();

    @Override
    public Rocket addRocket(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Rocket name cannot be empty");
        }
        if (rockets.containsKey(name)) {
            throw new IllegalArgumentException("Rocket already exists");
        }

        Rocket rocket = new Rocket(name);
        rockets.put(name, rocket);
        return rocket;
    }

    @Override
    public Rocket getRocket(String name) {
        Rocket rocket = rockets.get(name);
        if (rocket == null) {
            throw new IllegalArgumentException("Rocket not found");
        }
        return rocket;
    }

    @Override
    public void assignToMission(String rocketName, Mission mission) {
        Rocket rocket = getRocket(rocketName);

        if (rocket.getAssignedMission() != null) {
            throw new IllegalStateException("Rocket already assigned");
        }
        if (mission.getStatus() == MissionStatus.ENDED) {
            throw new IllegalStateException("Mission ended");
        }

        rocket.assignMission(mission);
        mission.addRocket(rocket);
    }

    @Override
    public void changeRocketStatus(String name, RocketStatus status) {
        if (status == null) throw new NullPointerException("Rocket status cannot be null");
        Rocket rocket = getRocket(name);
        rocket.setStatus(status);

        Mission mission = rocket.getAssignedMission();
        if (mission != null) {
            mission.updateStatus();
        }
    }
}

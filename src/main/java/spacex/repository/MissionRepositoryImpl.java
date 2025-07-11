package spacex.repository;

import spacex.model.Mission;
import spacex.model.MissionStatus;

import java.util.HashMap;
import java.util.Map;

public class MissionRepositoryImpl implements MissionRepository{
    private final Map<String, Mission> missions = new HashMap<>();

    @Override
    public Mission addMission(String name) {
        if (missions.containsKey(name)) throw new IllegalArgumentException("Mission already exists");
        Mission mission = new Mission(name);
        missions.put(name, mission);
        return mission;
    }

    @Override
    public Mission getMission(String name) {
        Mission mission = missions.get(name);
        if (mission == null) throw new IllegalArgumentException("Mission not found");
        return mission;
    }

    @Override
    public void changeStatus(String name, MissionStatus status) {
        Mission mission = getMission(name);
        mission.setStatus(status);
    }

}

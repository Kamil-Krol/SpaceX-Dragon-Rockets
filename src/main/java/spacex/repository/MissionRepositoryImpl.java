package spacex.repository;

import spacex.model.Mission;
import spacex.model.MissionStatus;
import spacex.summary.MissionSummary;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionRepositoryImpl implements MissionRepository{
    private final Map<String, Mission> missions = new HashMap<>();

    @Override
    public Mission addMission(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Mission name cannot be null or empty");
        }
        if (missions.containsKey(name)) {
            throw new IllegalArgumentException("Mission already exists");
        }

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
        if (status == null) throw new NullPointerException("Mission status cannot be null");
        Mission mission = getMission(name);
        mission.setStatus(status);
    }

    @Override
    public List<MissionSummary> getSummary() {
        return missions.values().stream()
                .map(m -> new MissionSummary(
                        m.getName(),
                        m.getStatus(),
                        m.getRockets().size(),
                        m.getRockets().stream()
                                .map(r -> new MissionSummary.RocketInfo(r.getName(), r.getStatus()))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator
                        .comparingInt(MissionSummary::rocketCount).reversed()
                        .thenComparing(MissionSummary::name, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
    }

}

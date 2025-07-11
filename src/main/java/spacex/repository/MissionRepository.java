package spacex.repository;

import spacex.model.Mission;
import spacex.model.MissionStatus;

public interface MissionRepository {
    Mission addMission(String name);
    Mission getMission(String name);
    void changeStatus(String name, MissionStatus status);
}

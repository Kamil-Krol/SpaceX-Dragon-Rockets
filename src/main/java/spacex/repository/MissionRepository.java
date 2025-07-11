package spacex.repository;

import spacex.model.Mission;
import spacex.model.MissionStatus;
import spacex.summary.MissionSummary;

import java.util.List;

public interface MissionRepository {
    Mission addMission(String name);
    Mission getMission(String name);
    void changeStatus(String name, MissionStatus status);
    List<MissionSummary> getSummary();
}

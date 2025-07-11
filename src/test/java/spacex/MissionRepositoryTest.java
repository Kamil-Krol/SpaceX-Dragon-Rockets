package spacex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissionRepositoryTest {

    private IMissionRepository missionRepo;

    @BeforeEach
    void setup() {
        missionRepo = new MissionRepository();
    }

    @Test
    void addMission_setsInitialStatusScheduled() {
        Mission mission = missionRepo.addMission("Mars");
        assertEquals("Mars", mission.getName());
        assertEquals(MissionStatus.SCHEDULED, mission.getStatus());
    }

    @Test
    void changeMissionStatus_updatesStatus() {
        missionRepo.addMission("Apollo");
        missionRepo.changeStatus("Apollo", MissionStatus.ENDED);
        Mission mission = missionRepo.get("Apollo");
        assertEquals(MissionStatus.ENDED, mission.getStatus());
    }
}

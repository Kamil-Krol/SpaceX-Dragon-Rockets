package spacex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spacex.model.Mission;
import spacex.model.MissionStatus;
import spacex.repository.MissionRepository;
import spacex.repository.MissionRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissionRepositoryTest {

    private MissionRepository missionRepo;

    @BeforeEach
    void setup() {
        missionRepo = new MissionRepositoryImpl();
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
        Mission mission = missionRepo.getMission("Apollo");
        assertEquals(MissionStatus.ENDED, mission.getStatus());
    }
}

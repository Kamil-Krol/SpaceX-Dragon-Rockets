package spacex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spacex.model.Mission;
import spacex.model.MissionStatus;
import spacex.model.RocketStatus;
import spacex.repository.MissionRepository;
import spacex.repository.MissionRepositoryImpl;
import spacex.repository.RocketRepository;
import spacex.repository.RocketRepositoryImpl;
import spacex.summary.MissionSummary;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissionRepositoryTest {

    private MissionRepository missionRepo;
    private RocketRepository rocketRepo;

    @BeforeEach
    void setup() {
        missionRepo = new MissionRepositoryImpl();
        rocketRepo = new RocketRepositoryImpl();
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

    @Test
    void getSummary_returnsMissionsSortedByRocketCountDescAndNameDesc() {
        Mission m1 = missionRepo.addMission("Beta");
        Mission m2 = missionRepo.addMission("Alpha");
        Mission m3 = missionRepo.addMission("Gamma");

        rocketRepo.addRocket("R1");
        rocketRepo.addRocket("R2");
        rocketRepo.addRocket("R3");

        rocketRepo.assignToMission("R1", m1); // 1 rocket
        rocketRepo.assignToMission("R2", m2); // 1 rocket
        rocketRepo.assignToMission("R3", m1); // 2 total in m1

        List<MissionSummary> summary = missionRepo.getSummary();

        assertEquals("Beta", summary.get(0).name());   // 2 rockets
        assertEquals("Alpha", summary.get(1).name());  // 1 rocket
        assertEquals("Gamma", summary.get(2).name());  // 0 rockets
    }

    @Test
    void getSummary_includesCorrectRocketDetails() {
        Mission mission = missionRepo.addMission("Transit");

        rocketRepo.addRocket("Red Dragon");
        rocketRepo.addRocket("Dragon XL");
        rocketRepo.addRocket("Falcon Heavy");

        rocketRepo.assignToMission("Red Dragon", mission);
        rocketRepo.assignToMission("Dragon XL", mission);
        rocketRepo.assignToMission("Falcon Heavy", mission);

        rocketRepo.changeRocketStatus("Red Dragon", RocketStatus.ON_GROUND);
        rocketRepo.changeRocketStatus("Dragon XL", RocketStatus.IN_SPACE);
        rocketRepo.changeRocketStatus("Falcon Heavy", RocketStatus.IN_SPACE);

        List<MissionSummary> summary = missionRepo.getSummary();
        MissionSummary ms = summary.stream()
                .filter(s -> s.name().equals("Transit"))
                .findFirst()
                .orElseThrow();

        assertEquals(3, ms.rocketCount());
        assertEquals(3, ms.rockets().size());
        assertTrue(ms.rockets().stream().anyMatch(r -> r.name().equals("Red Dragon") && r.status() == RocketStatus.ON_GROUND));
        assertTrue(ms.rockets().stream().anyMatch(r -> r.name().equals("Dragon XL") && r.status() == RocketStatus.IN_SPACE));
        assertTrue(ms.rockets().stream().anyMatch(r -> r.name().equals("Falcon Heavy") && r.status() == RocketStatus.IN_SPACE));
    }

    @Test
    void addDuplicateMission_throwsException() {
        missionRepo.addMission("Apollo");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            missionRepo.addMission("Apollo");
        });
        assertEquals("Mission already exists", ex.getMessage());
    }

    @Test
    void addMissionWithNullName_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            missionRepo.addMission(null);
        });
        assertEquals("Mission name cannot be null or empty", ex.getMessage());
    }

    @Test
    void addMissionWithEmptyName_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            missionRepo.addMission("");
        });
        assertEquals("Mission name cannot be null or empty", ex.getMessage());
    }

    @Test
    void changeStatusOfNonExistentMission_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            missionRepo.changeStatus("GhostMission", MissionStatus.IN_PROGRESS);
        });
        assertEquals("Mission not found", ex.getMessage());
    }

    @Test
    void addMissionWithVeryLongName_succeeds() {
        String longName = "M".repeat(1000);
        Mission mission = missionRepo.addMission(longName);
        assertEquals(longName, mission.getName());
    }

    @Test
    void getSummary_whenNoMissions_returnsEmptyList() {
        List<MissionSummary> summary = missionRepo.getSummary();
        assertNotNull(summary);
        assertTrue(summary.isEmpty());
    }

    @Test
    void getSummary_missionWithNoRockets_showsZeroCount() {
        missionRepo.addMission("NoRocketMission");
        List<MissionSummary> summary = missionRepo.getSummary();
        MissionSummary ms = summary.stream()
                .filter(m -> m.name().equals("NoRocketMission"))
                .findFirst()
                .orElseThrow();
        assertEquals(0, ms.rocketCount());
    }
}

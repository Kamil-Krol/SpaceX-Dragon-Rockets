package spacex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spacex.model.Mission;
import spacex.model.MissionStatus;
import spacex.model.Rocket;
import spacex.model.RocketStatus;
import spacex.repository.MissionRepository;
import spacex.repository.MissionRepositoryImpl;
import spacex.repository.RocketRepository;
import spacex.repository.RocketRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;


class RocketRepositoryTest {
    private RocketRepository rocketRepo;
    private MissionRepository missionRepo;

    @BeforeEach
    void setup() {
        rocketRepo = new RocketRepositoryImpl();
        missionRepo = new MissionRepositoryImpl();
    }

    @Test
    void addRocket_initialStatusIsOnGround() {
        Rocket r = rocketRepo.addRocket("Falcon");
        assertEquals(RocketStatus.ON_GROUND, r.getStatus());
    }

    @Test
    void assignRocketToMission_assignsSuccessfully() {
        Rocket r = rocketRepo.addRocket("Dragon");
        Mission m = missionRepo.addMission("Luna");
        rocketRepo.assignToMission("Dragon", m);
        assertEquals(m, r.getAssignedMission());
        assertTrue(m.getRockets().contains(r));
    }

    @Test
    void changeRocketStatus_updatesStatusAndMission() {
        rocketRepo.addRocket("R1");
        Mission m = missionRepo.addMission("Luna");
        rocketRepo.assignToMission("R1", m);

        rocketRepo.changeRocketStatus("R1", RocketStatus.IN_REPAIR);
        assertEquals(RocketStatus.IN_REPAIR, rocketRepo.getRocket("R1").getStatus());
        assertEquals(MissionStatus.PENDING, m.getStatus());

        rocketRepo.changeRocketStatus("R1", RocketStatus.IN_SPACE);
        assertEquals(RocketStatus.IN_SPACE, rocketRepo.getRocket("R1").getStatus());
        assertEquals(MissionStatus.IN_PROGRESS, m.getStatus());
    }
    @Test
    void addDuplicateRocket_throws() {
        rocketRepo.addRocket("Falcon");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            rocketRepo.addRocket("Falcon");
        });
        assertEquals("Rocket already exists", ex.getMessage());
    }

    @Test
    void assignRocketAlreadyAssigned_throws() {
        rocketRepo.addRocket("Dragon");
        Mission m1 = missionRepo.addMission("Luna");
        Mission m2 = missionRepo.addMission("Mars");
        rocketRepo.assignToMission("Dragon", m1);
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            rocketRepo.assignToMission("Dragon", m2);
        });
        assertEquals("Rocket already assigned", ex.getMessage());
    }

    @Test
    void assignRocketToEndedMission_throws() {
        rocketRepo.addRocket("Dragon");
        Mission m = missionRepo.addMission("Luna");
        missionRepo.changeStatus("Luna", MissionStatus.ENDED);
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            rocketRepo.assignToMission("Dragon", m);
        });
        assertEquals("Mission ended", ex.getMessage());
    }

    @Test
    void changeStatusOfNonExistingRocket_throws() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            rocketRepo.changeRocketStatus("NonExistent", RocketStatus.IN_SPACE);
        });
        assertEquals("Rocket not found", ex.getMessage());
    }

    @Test
    void assignNonExistingRocket_throws() {
        Mission m = missionRepo.addMission("Luna");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            rocketRepo.assignToMission("NonExistent", m);
        });
        assertEquals("Rocket not found", ex.getMessage());
    }

    @Test
    void addRocket_emptyOrNullName_throws() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {
            rocketRepo.addRocket("");
        });
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> {
            rocketRepo.addRocket(null);
        });
        assertEquals("Rocket name cannot be empty", ex1.getMessage());
        assertEquals("Rocket name cannot be empty", ex2.getMessage());
    }

    @Test
    void changeRocketStatusToNull_throwsNullPointerException() {
        rocketRepo.addRocket("Falcon");

        NullPointerException ex = assertThrows(NullPointerException.class, () -> {
            rocketRepo.changeRocketStatus("Falcon", null);
        });

        assertEquals("Rocket status cannot be null", ex.getMessage());
    }
}

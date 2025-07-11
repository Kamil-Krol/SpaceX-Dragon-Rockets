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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}

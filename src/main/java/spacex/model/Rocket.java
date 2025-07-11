package spacex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "assignedMission")
public class Rocket {
    private final String name;
    private RocketStatus status = RocketStatus.ON_GROUND;
    private Mission assignedMission;

    public void assignMission(Mission mission) {
        this.assignedMission = mission;
    }
}

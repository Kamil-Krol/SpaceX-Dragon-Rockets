package spacex.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "rockets")
public class Mission {
    private final String name;
    private MissionStatus status = MissionStatus.SCHEDULED;
    private final Set<Rocket> rockets = new HashSet<>();

    public void addRocket(Rocket rocket) {
        rockets.add(rocket);
        updateStatus();
    }

    public void updateStatus() {
        if (status == MissionStatus.ENDED) return;

        if (rockets.isEmpty()) {
            status = MissionStatus.SCHEDULED;
        } else if (rockets.stream().anyMatch(r -> r.getStatus() == RocketStatus.IN_REPAIR)) {
            status = MissionStatus.PENDING;
        } else {
            status = MissionStatus.IN_PROGRESS;
        }
    }

}
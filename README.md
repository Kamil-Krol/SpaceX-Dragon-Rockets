# SpaceX-Dragon-Rockets

## Overview

This application models missions and rockets similar to SpaceX operations. It provides functionality to create and manage missions and rockets, assign rockets to missions, change their statuses, and generate mission summaries including rocket details.

---

## Key Features

- **Mission Management**
    - Add new missions with initial status `SCHEDULED`.
    - Change mission status (`SCHEDULED`, `PENDING`, `IN_PROGRESS`, `ENDED`).
    - Missions track their assigned rockets and update status dynamically based on rocket statuses.

- **Rocket Management**
    - Add rockets with initial status `ON_GROUND`.
    - Assign rockets to missions (only if the mission is not ended and the rocket is not already assigned).
    - Change rocket statuses (`ON_GROUND`, `IN_SPACE`, `IN_REPAIR`, `IN_BUILD`).
    - Changing rocket status updates the status of the assigned mission accordingly.

- **Mission Summaries**
    - Generate mission summaries with mission name, status, rocket count, and list of rockets with their individual statuses.
    - Summaries are sorted by descending rocket count and mission name descending.

---

## Assumptions and Specifications

- Mission status updates follow these rules:
    - If no rockets assigned → status = `SCHEDULED`.
    - If any rocket is in `IN_REPAIR` → status = `PENDING`.
    - Otherwise, if rockets assigned → status = `IN_PROGRESS`.
    - Status `ENDED` is final and cannot be overridden by rocket status changes.

- Rocket assignment restrictions:
    - A rocket can be assigned to only one mission at a time.
    - Cannot assign rockets to missions with status `ENDED`.

- Input validation:
    - Mission and rocket names must be non-null and non-empty.
    - Duplicate mission or rocket names are not allowed.
    - Changing status to null throws `NullPointerException`.

- The project uses Java records for immutable summary data (`MissionSummary`).

---

## Testing

- Comprehensive unit tests for both mission and rocket repositories.
- Tests cover:
    - Adding missions/rockets with valid and invalid data.
    - Status changes and propagation logic.
    - Rocket assignment rules and exceptions.
    - Summary generation correctness and sorting order.

---


DROP TABLE IF EXISTS rockets;
DROP TABLE IF EXISTS missions;

CREATE TABLE missions (
                          name VARCHAR PRIMARY KEY,
                          status VARCHAR
);

CREATE TABLE rockets (
                         name VARCHAR PRIMARY KEY,
                         status VARCHAR,
                         mission_name VARCHAR,
                         FOREIGN KEY (mission_name) REFERENCES missions(name)
);

-- Missions
INSERT INTO missions (name, status) VALUES
                                        ('Mars', 'SCHEDULED'),
                                        ('Luna1', 'PENDING'),
                                        ('Double Landing', 'ENDED'),
                                        ('Transit', 'IN_PROGRESS'),
                                        ('Luna2', 'SCHEDULED'),
                                        ('Vertical Landing', 'ENDED');

-- Rockets
INSERT INTO rockets (name, status, mission_name) VALUES
                                                     ('Dragon 1', 'IN_SPACE', 'Luna1'),
                                                     ('Dragon 2', 'IN_REPAIR', 'Luna1'),
                                                     ('Red Dragon', 'ON_GROUND', 'Transit'),
                                                     ('Dragon XL', 'IN_SPACE', 'Transit'),
                                                     ('Falcon Heavy', 'IN_SPACE', 'Transit'),
                                                     ('Dragon 3', 'ON_GROUND', NULL);
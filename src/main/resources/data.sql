CREATE TABLE IF NOT EXISTS team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255),
    balance DECIMAL (19,2),
    commission INT DEFAULT 0
    );


CREATE TABLE IF NOT EXISTS player
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (255) NOT NULL,
    age INT,
    position VARCHAR (255),
    experience INT,
    team_id BIGINT,
    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE
);

INSERT INTO team (name, city, balance, commission)
VALUES ('FC Barcelona', 'Barcelona', 50000000.00, 5.0),
       ('Real Madrid', 'Madrid', 60000000.00, 5.0),
       ('Manchester City', 'Manchester', 700.00, 4.0),
       ('Liverpool', 'Liverpool', 55000000.00, 10.0);

INSERT INTO player (name, age, position, experience, team_id)
VALUES ('Lionel Messi', 36, 'Forward', 20, 1),
       ('Luka Modric', 38, 'Midfielder', 19, 2),
       ('Erling Haaland', 24, 'Forward', 6, 3),
       ('Virgil van Dijk', 32, 'Defender', 12, 4),
       ('Pedri', 21, 'Midfielder', 4, 1),
       ('Karim Benzema', 35, 'Forward', 17, 2);

CREATE TABLE `matchResult`
(
    `id`            integer NOT NULL,
    `homeTeam`      varchar(255) not null,
    `awayTeam`      varchar(255) not null,
    `homeTeamGoals` integer not null,
    `awayTeamGoals` integer not null,
    PRIMARY KEY (`id`)
);
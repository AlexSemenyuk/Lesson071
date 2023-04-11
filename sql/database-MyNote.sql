DROP DATABASE mynotes;
create database myNotes default char set utf8;
use myNotes;

CREATE TABLE `tasks`
(
    `id`           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(255)    NOT NULL,
    `description`  TEXT            NOT NULL,
    `category_id`  INT             NOT NULL,
    `deadline`     DATE            NOT NULL,
    `priority_id`  INT             NOT NULL,
    `condition_id` INT             NOT NULL
);

CREATE TABLE `categories`
(
    `id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `num`      INT             NOT NULL,
    `category` VARCHAR(255)    NOT NULL
);

alter table tasks
    add constraint tasks_categories_fk
        foreign key (category_id) references categories (id);


CREATE TABLE `priorities`
(
    `id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `num`      INT             NOT NULL,
    `priority` VARCHAR(255)    NOT NULL
);

alter table tasks
    add constraint tasks_priorities_fk
        foreign key (priority_id) references priorities (id);

CREATE TABLE `conditions`
(
    `id`        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `num`       INT             NOT NULL,
    `condition` VARCHAR(255)    NOT NULL
);

alter table tasks
    add constraint tasks_conditions_fk
        foreign key (condition_id) references conditions (id);
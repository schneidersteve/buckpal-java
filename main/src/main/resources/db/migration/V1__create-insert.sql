create table account_entity(
    id bigint AUTO_INCREMENT primary key not null
);

create table activity_entity(
    id bigint AUTO_INCREMENT primary key not null,
    timestamp timestamp,
    owner_account_id bigint,
    source_account_id bigint,
    target_account_id bigint,
    amount bigint
);

insert into account_entity (id) values (1);
insert into account_entity (id) values (2);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (1, '2018-08-08 08:00:00.0', 1, 1, 2, 500);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (2, '2018-08-08 08:00:00.0', 2, 1, 2, 500);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (3, '2018-08-09 10:00:00.0', 1, 2, 1, 1000);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (4, '2018-08-09 10:00:00.0', 2, 2, 1, 1000);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (5, '2019-08-09 09:00:00.0', 1, 1, 2, 1000);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (6, '2019-08-09 09:00:00.0', 2, 1, 2, 1000);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (7, '2019-08-09 10:00:00.0', 1, 2, 1, 1000);

insert into activity_entity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (8, '2019-08-09 10:00:00.0', 2, 2, 1, 1000);
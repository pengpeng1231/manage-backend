create table departments
(
    id          bigint auto_increment primary key,
    code        varchar(30) not null,
    name        varchar(30) not null,
    phone       varchar(20) null,
    status      tinyint     not null default 1,
    parent_id   bigint      null,
    create_time bigint      null,
    update_time bigint      null,
    constraint UQ_departments_code UNIQUE (code),
    constraint FK_departments_parent_id
        foreign key (parent_id) references departments (id)
);

create table users
(
    id          bigint auto_increment primary key,
    username    varchar(20)  not null,
    password    varchar(100) not null,
    email       varchar(50)  null,
    phone       varchar(20)  null,
    status      tinyint      not null default 1,
    dept_id     bigint       null,
    create_time bigint       null,
    update_time bigint       null,
    avatar      text         null,
    constraint UQ_users_username
        unique (username),
    constraint FK_users_departments_id
        foreign key (dept_id) references departments (id)
);

create index dept_id
    on users (dept_id);

create table menus
(
    id          bigint auto_increment primary key,
    name        varchar(30) not null,
    path        varchar(50) not null,
    icon        varchar(30) null,
    parent_id   bigint      null,
    status      tinyint     not null default 1,
    sort_num    int         not null default 0,
    create_time bigint      null,
    update_time bigint      null,
    constraint FK_menus_parent_id
        foreign key (parent_id) references menus (id)
);

create table positions
(
    id        bigint auto_increment primary key,
    name      varchar(30) not null,
    code      varchar(30) not null,
    type      varchar(30) not null,
    level     varchar(20) not null,
    status    tinyint     not null default 1,
    dep_id    bigint      null,
    parent_id bigint      null,
    constraint UQ_positions_name unique (name),
    constraint FK_positions_parent_id
        foreign key (parent_id) references departments (id)
)
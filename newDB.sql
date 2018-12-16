 CREATE DATABASE eslbase OWNER postgres;

 --таблица ценников
create table esls
(
  eslcode          varchar(255) not null
    constraint esls_pkey
      primary key,
  batterylevel     varchar(255),
  connectivity     varchar(255),
  eslpattern       varchar(255),
  esltype          varchar(255) not null,
  firmware         varchar(255) not null,
  lastupdate       timestamp,
  registrationdate timestamp,
  startdate        timestamp,
  status           varchar(255),
  item_itemcode    varchar(255)
      references items
);

alter table esls
  owner to postgres;
-- таблица итемов
create table items
(
  itemcode       varchar(255)     not null
    constraint items_pkey
      primary key,
  itemname       varchar(256)     not null,
  lastupdated    timestamp,
  price          double precision not null,
  promotionprice double precision not null,
  storageunit    varchar(255)     not null,
  esl_eslcode    varchar(255)
      references esls
);

alter table items
  owner to postgres;


-- таблица шедулера
create table tasks
(
  taskname     varchar(255) not null
    constraint tasks_pkey
      primary key,
  frequency    varchar(255),
  lastupdated  date,
  nextsheduled date,
  currentimage text,
  tasktype     varchar(255) not null,
  status     varchar(255) not null
);

alter table tasks
  owner to postgres;

-- таблица инфо о выполнении таски
create table tasksInfo
(
  taskid     varchar(255) not null
    constraint tasksInfo_pkey
      primary key,
  tasks_taskname    varchar(255)
      references tasks,
  imageforupdate text,
  currentimage text,
  esl_eslcode   varchar(255) 
    references esls
);

alter table tasksInfo
  owner to postgres;

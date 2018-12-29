﻿CREATE DATABASE eslbase OWNER postgres;

/*DROP TABLE item_params;
DROP TABLE available_params_for_template;
DROP TABLE templates;
DROP TABLE task_results;
DROP TABLE task_updated_item_params;
DROP TABLE tasks;
DROP TABLE directory_params;
DROP TABLE item_params_group;
DROP TABLE items;
DROP TABLE esls;*/

--таблица ценников
CREATE TABLE esls
(
  id                INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  code              VARCHAR(255) NOT NULL,
  battery_level     VARCHAR(255),
  current_image     BYTEA,
  connectivity      VARCHAR(255),
  esltype           VARCHAR(255) NOT NULL,
  firmware          VARCHAR(255) NOT NULL,
  last_update       TIMESTAMP,
  registration_date TIMESTAMP,
  start_date        TIMESTAMP,
  status            VARCHAR(255)
);

-- таблица итемов
CREATE TABLE items
(
  id            INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  code          VARCHAR(255) NOT NULL,
  name          VARCHAR(256) NOT NULL,
  last_updated  TIMESTAMP,
  storageunit   VARCHAR(255) NOT NULL,
  esl_id        INT NULL REFERENCES esls(id) 
);

--индекс для обозначения связи 1к1
CREATE UNIQUE INDEX index_items_esl_id
ON public.items (esl_id ASC);

-- таблица справочник параметров
CREATE TABLE directory_params
(
  id            INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name          VARCHAR(256) NOT NULL,
  type_param    INT NOT NULL
);

-- таблица шаблонов
CREATE TABLE templates
(
  id        INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name      VARCHAR(256) NOT NULL,
  template  TEXT NULL
);

-- таблица групп параметров
CREATE TABLE item_params_group
(
  id            INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  item_id       INT NOT NULL REFERENCES items(id),
  template_id   INT REFERENCES templates(id),
  date_added    TIMESTAMP NOT NULL 
);

-- таблица параметров итемов
CREATE TABLE item_params
(
  id                    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  item_params_group_id  INT REFERENCES item_params_group(id),
  param_id              INT NOT NULL REFERENCES directory_params(id),
  param_value           TEXT NULL
);

-- таблица доступных парамтров для конкретного шаблона
CREATE TABLE available_params_for_template
(
  id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  template_id INT REFERENCES templates(id),
  param_id    INT NOT NULL REFERENCES directory_params(id)
);

-- таблица тасков
CREATE TABLE tasks
(
  id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  taskname        VARCHAR(255),
  cron_expression VARCHAR(255),
  status          INT NOT NULL
);

-- таблица результатов таска
CREATE TABLE task_results
(
  id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  task_id     INT REFERENCES tasks(id),
  start_date  TIMESTAMP,
  end_date    TIMESTAMP,
  status      INT NOT NULL,
  result      VARCHAR(255)
);

-- таблица обновляемых итемов для конкретного таска
CREATE TABLE task_updated_item_params
(
  id                    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  task_id               INT REFERENCES tasks(id),
  item_params_group_id  INT REFERENCES item_params_group(id),
  status                INT NOT NULL
)

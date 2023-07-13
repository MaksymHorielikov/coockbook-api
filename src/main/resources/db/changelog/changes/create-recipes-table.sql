--liquibase formatted sql
--changeset sql:create-recipes-table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS recipes
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(500) NOT NULL,
    date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    parent_id BIGINT NULL,
    deleted TINYINT(1) NOT NULL DEFAULT 0
);

--rollback DROP TABLE recipes;

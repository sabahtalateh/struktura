-- +goose Up
-- +goose StatementBegin
CREATE TABLE projects
(
    id       VARCHAR NOT NULL,
    name     VARCHAR NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_project PRIMARY KEY (id)
);
-- +goose StatementEnd

-- +goose Down
-- +goose StatementBegin
DROP TABLE projects;
-- +goose StatementEnd

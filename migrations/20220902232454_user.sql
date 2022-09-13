-- +goose Up
-- +goose StatementBegin
CREATE TABLE users
(
    id            SERIAL                      NOT NULL,
    username      VARCHAR UNIQUE              NOT NULL,
    password      VARCHAR                     NOT NULL,
    registered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
-- +goose StatementEnd

-- +goose Down
-- +goose StatementBegin
DROP TABLE users;
-- +goose StatementEnd

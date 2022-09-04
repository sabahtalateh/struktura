-- +goose Up
-- +goose StatementBegin
CREATE TABLE account
(
    id            SERIAL                      NOT NULL,
    email         VARCHAR UNIQUE              NOT NULL,
    password      VARCHAR                     NOT NULL,
    registered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);
-- +goose StatementEnd

-- +goose Down
-- +goose StatementBegin
DROP TABLE account;
-- +goose StatementEnd

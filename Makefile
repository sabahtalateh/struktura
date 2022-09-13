DEV_CONF_EXAMPLE=build/devenv/microprofile-config.properties.example
SERVER_CONF=src/main/resources/META-INF/microprofile-config.properties
DEV_APP_EXAMPLE=build/devenv/application.yaml.example
SERVER_APP=src/main/resources/application.yaml

DEV_DB_PORT=54321
DEV_DB_URI=host=127.0.0.1 port=${DEV_DB_PORT} user=struktura password=struktura dbname=struktura sslmode=disable

# ====== FRONTENDERS SECTION ======

just-tools:
	go install github.com/pressly/goose/v3/cmd/goose@latest

just-run:
	DB_PORT=${DEV_DB_PORT} docker-compose -f build/devenv/infra.yml up -d
	goose -dir migrations postgres "${DEV_DB_URI}" up
	cp ${DEV_CONF_EXAMPLE} ${SERVER_CONF}
	cp ${DEV_APP_EXAMPLE} ${SERVER_APP}
	./mvnw clean package
	java -jar target/struktura.jar

just-stop: dev-infra-down

# ====== FRONTENDERS SECTION ======

dev-infra-up:
	DB_PORT=${DEV_DB_PORT} docker-compose -f build/devenv/infra.yml up -d

dev-infra-down:
	docker-compose -f build/devenv/infra.yml down

dev-infra-ls:
	docker-compose -f build/devenv/infra.yml ls

dev-server-build:
	./mvnw clean package

dev-server: dev-server-build
	java -jar target/struktura.jar

goose-install:
	go install github.com/pressly/goose/v3/cmd/goose@latest

dev-goose-up:
	goose -dir migrations postgres "${DEV_DB_URI}" up

dev-goose-down:
	goose -dir migrations postgres "${DEV_DB_URI}" down
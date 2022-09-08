DEV_CONF=build/devenv/microprofile-config.properties
SERVER_CONF=src/main/resources/META-INF/microprofile-config.properties
DEV_APP=build/devenv/application.yaml
SERVER_APP=src/main/resources/application.yaml

DEV_DB_URI=host=127.0.0.1 port=54321 user=struktura password=struktura dbname=struktura sslmode=disable binary_parameters=yes

dev-infra-up:
	docker-compose -f build/devenv/infra.yml up -d

dev-infra-down:
	docker-compose -f build/devenv/infra.yml down

dev-infra-ls:
	docker-compose -f build/devenv/infra.yml ls

dev-env:
	cp ${DEV_CONF} ${SERVER_CONF}
	cp ${DEV_APP} ${SERVER_APP}

dev-server-build: dev-env
	./mvnw clean package

dev-server: dev-env dev-server-build
	java -jar target/struktura.jar

dev-native:
	./mvnw clean package -Pnative-image
	./target/struktura

goose-install:
	go install github.com/pressly/goose/v3/cmd/goose@latest

dev-goose-up:
	goose -dir migrations postgres "${DEV_DB_URI}" up

dev-goose-down:
	goose -dir migrations postgres "${DEV_DB_URI}" down
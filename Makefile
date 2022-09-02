DEV_CONF=build/devenv/microprofile-config.properties
SERVER_CONF=src/main/resources/META-INF/microprofile-config.properties

dev-infra-up:
	docker-compose -f build/devenv/infra.yml up -d

dev-infra-down:
	docker-compose -f build/devenv/infra.yml down

dev-server:
	cp ${DEV_CONF} ${SERVER_CONF}
	./mvnw clean package
	java -jar target/struktura.jar

dev-native:
	cp ${DEV_CONF} ${SERVER_CONF}
	./mvnw clean package -Pnative-image
	./target/struktura

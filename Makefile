.DEFAULT_GOAL:= test
.PHONY: build


run:
	./gradlew run --args='${FILE}'

build:
	./gradlew build --no-build-cache

test:
	./gradlew test --no-build-cache

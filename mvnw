#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Wrapper startup script for UNIX
# ----------------------------------------------------------------------------

set -e

MVNW_DIR="$(cd "$(dirname "$0")"; pwd)"
MVNW_JAR="$MVNW_DIR/.mvn/wrapper/maven-wrapper.jar"
MVNW_PROPERTIES="$MVNW_DIR/.mvn/wrapper/maven-wrapper.properties"

if [ ! -f "$MVNW_JAR" ]; then
  echo "Missing Maven Wrapper JAR. Please download it manually."
  exit 1
fi

JAVA_CMD="java"
exec "$JAVA_CMD" -jar "$MVNW_JAR" "$@"

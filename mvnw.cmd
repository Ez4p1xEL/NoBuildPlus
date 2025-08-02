@echo off
setlocal

set MVNW_DIR=%~dp0
set MVNW_JAR=%MVNW_DIR%.mvn\wrapper\maven-wrapper.jar

if not exist "%MVNW_JAR%" (
  echo Missing Maven Wrapper JAR. Please download it manually.
  exit /b 1
)

set JAVA_CMD=java
"%JAVA_CMD%" -jar "%MVNW_JAR%" %*

#!/bin/bash

echo -e "\nDinosoul Deploy Start!!!"

# Set Profile
PROFILE="dev"
echo "  > current profile : ${PROFILE}"


# Set Variables
JAR_PATH="./server-0.0.1-SNAPSHOT.jar"
PROCESS_NAME="server"
LOG_FILE="application.log"


# Process Detect And Terminate Task
echo -e "\nReady to terminate current process..."

PID=$(pgrep -f "${PROCESS_NAME}")

if [ -z "${PID}" ]; then
  echo "  > process not detected!!"
else
  echo "  > process detected!!"
  pgrep -f "${PROCESS_NAME}" |
  while read -r line; do
    echo "   > terminate process $line"
    kill -15 "$line"
  done
fi

while (lsof -Pi :8080 -sTCP:LISTEN | grep 8080); do
  sleep 1
done
echo "  > Process Terminate Success!!"

cd ~
cp -r secret Server/src/main/resources/

cd ~
cd "Server"
./gradlew bootJar
echo "\nBuild project!!"

# Jar Execution Task
echo -e "\nReady to execute jar..."

cd build/libs
nohup java -jar "${JAR_PATH}" --spring.profiles.active="${PROFILE}" -Duser.timezone="Asia/Seoul" >> "${LOG_FILE}" 2>/dev/null &
echo "  > Jar Execute Success!!"

#!/usr/bin/env sh
APP_HOME=$(cd "${0%/*}" || exit; pwd)
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Locate Java
if [ -n "$JAVA_HOME" ]; then JAVA_EXE="$JAVA_HOME/bin/java"; else JAVA_EXE="java"; fi
if [ ! -x "$JAVA_EXE" ]; then
  echo "ERROR: Java not found. Set JAVA_HOME or install JDK 21." >&2
  exit 1
fi

# Ensure wrapper jar exists (fetch from Gradle repo if missing)
if [ ! -f "$CLASSPATH" ]; then
  echo "Fetching gradle-wrapper.jar..."
  mkdir -p "$APP_HOME/gradle/wrapper"
  WRAPPER_URL="https://repo.gradle.org/gradle/libs-releases-local/org/gradle/gradle-wrapper/8.7/gradle-wrapper-8.7.jar"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$WRAPPER_URL" -o "$CLASSPATH"
  else
    echo "curl required to fetch wrapper jar" >&2
    exit 1
  fi
fi

exec "$JAVA_EXE" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"

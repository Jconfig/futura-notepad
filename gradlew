#!/usr/bin/env sh

# SPDX-License-Identifier: Apache-2.0
# Minimal Gradle wrapper script

APP_HOME=$(cd "${0%/*}" || exit; pwd)
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Locate Java
if [ -n "$JAVA_HOME" ] ; then
  JAVA_EXE="$JAVA_HOME/bin/java"
else
  JAVA_EXE="java"
fi
if [ ! -x "$JAVA_EXE" ] ; then
  echo "ERROR: Java not found. Set JAVA_HOME or install JDK 21." >&2
  exit 1
fi

# Download wrapper JAR if missing
if [ ! -f "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" ]; then
  echo "Downloading gradle-wrapper.jar..."
  WRAPPER_URL="https://services.gradle.org/distributions/gradle-8.7-wrapper.jar"
  mkdir -p "$APP_HOME/gradle/wrapper"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$WRAPPER_URL" -o "$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
  else
    echo "curl required to fetch wrapper jar" >&2
    exit 1
  fi
fi

# Pass through args
exec "$JAVA_EXE" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"

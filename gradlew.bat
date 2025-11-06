@ECHO OFF
SETLOCAL
set DIR=%~dp0
set APP_HOME=%DIR%
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

IF DEFINED JAVA_HOME (
  set JAVA_EXE=%JAVA_HOME%\bin\java.exe
) ELSE (
  set JAVA_EXE=java.exe
)

IF NOT EXIST "%JAVA_EXE%" (
  ECHO ERROR: Java not found. Set JAVA_HOME or install JDK 21.
  EXIT /B 1
)

IF NOT EXIST "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" (
  ECHO Downloading gradle-wrapper.jar...
  powershell -Command "Invoke-WebRequest https://services.gradle.org/distributions/gradle-8.7-wrapper.jar -OutFile '%APP_HOME%\gradle\wrapper\gradle-wrapper.jar'"
)

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

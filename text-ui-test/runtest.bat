@echo off
chcp 65001 > nul

echo ===============================================
echo               Rucia Test Runner
echo ===============================================
echo.

:: Step 1: Check Java version
echo [1/4] Checking Java version...
java -version

:: Check if using Java 21
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVAVER=%%g
)

set JAVAVER=%JAVAVER:"=%
for /f "delims=. tokens=1" %%v in ("%JAVAVER%") do (
    set MAJORVER=%%v
)

if NOT "%MAJORVER%"=="21" (
    echo.
    echo =============== ERROR ===============
    echo Please use Java 21 ^(current: %MAJORVER% ^)
    echo ===================================
    exit /b 1
)

echo [√] Java version check passed
echo.

:: Step 2: Setting up test environment
echo [2/4] Setting up test environment...
if not exist ..\bin (
    mkdir ..\bin
    echo Created bin directory
)

if exist ..\text-ui-test\ACTUAL.txt (
    del ..\text-ui-test\ACTUAL.txt
    echo Cleaned up previous test outputs
)

echo.

:: Step 3: Compiling source files
echo [3/4] Compiling source files...
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java ..\src\main\java\tasks\*.java
IF ERRORLEVEL 1 (
    echo.
    echo =============== ERROR ===============
    echo         BUILD FAILURE
    echo ===================================
    exit /b 1
)

echo [√] Compilation successful
echo.

:: Step 4: Running tests
echo [4/4] Running tests...

:: Run the specific input and compare with expected output
echo Running test: input.txt
java -classpath ..\bin Rucia < ..\text-ui-test\input.txt > ..\text-ui-test\ACTUAL.txt

FC ..\text-ui-test\ACTUAL.txt ..\text-ui-test\EXPECTED.txt > nul
if ERRORLEVEL 1 (
    echo.
    echo =============== ERROR ===============
    echo           Test FAILED: input.txt
    echo ===================================
    exit /b 1
)

echo.
echo ============= SUCCESS ==============
echo         All tests passed
    echo ===================================
exit /b 0

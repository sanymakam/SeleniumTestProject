# SeleniumTestProject

# System Requirements

1. JAVA - Java [JDK or JRE - 8 or above](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

# Tools Used
1. [Gradle](https://gradle.org/) (4.10)- This is the build tool used to configure and install all the required dependent libraries and run tests from command line on local and remote machines.
2. [TestNG](https://testng.org/doc/index.html) - Test framework used to organize and run tests.
3. [Java](https://www.java.com/en/)(8) - As programming language.
4. [Selenium](https://selenium.dev/) - This is used to interact with the browser.
5. [Bonigarcia](https://github.com/bonigarcia/webdrivermanager) - This is used to automatically manage the driver versions.

# Steps to Setup
1. Install Appium through npm - `npm install -g appium@1.14.1`
2. Take a clone of this repository to local machine.
3. In IntelliJ (or any IDE), goto file -> open -> browse to the folder where the clone is taken -> select the folder -> click on open.
4. Now project is ready to execute, provided the above mentioned setup of tools is done successfully and all the dependencies are downloaded in the IDE.

# Steps to run
1. To run as gradle - `./gradlew clean runTests -Dtag=search`

# Reports 
1. TestNG report can be seen at - `/build/<tag name>/html/index.html` (open in browser)
2. Extent report can be seen at - `TestReport/Test-Automaton-Report.html` (open in browser)

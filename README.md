## Ftsc - Fyber time series challenge

### Assumptions
Time window - As the time window length is defined 60s and the epoch starts on 1970-01-01 00:00:00 I considered that a time window is exactly one minute based on beginning of the epoch and not based on the first record of the data or so. I rather use the concept above to make it simpler than a calculation over time window length since it is an evaluation project and will not be enhanced or changed over the time.

**Project** - Maven project, Java 8 is required once I used java.time to define time window, Scala version is 2.11.8 and JUnit for testing.

### Cloning
`git clone https://github.com/asbelcastro/Ftsc.git`

### Building
`cd <GitRepository>/Ftsc`
`mvn clean package`

**All tests must pass as below:**

`-------------------------------------------------------`

` T E S T S`

`-------------------------------------------------------`

`Running com.belcastro.aleques.ChallengeTest`

`Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.523 sec`

`Results :`

`Tests run: 10, Failures: 0, Errors: 0, Skipped: 0`

**and building must succeed as well:**

`[INFO] ------------------------------------------------------------------------`

`[INFO] BUILD SUCCESS`

`[INFO] ------------------------------------------------------------------------`

### Running
`java -jar target/challenge-01.00.00.jar /Users/Belcastro/Documents/Profissional/Fyber/data_scala.txt`

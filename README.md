## Ftsc - Fyber time series challenge

### Assumptions
**Time window** - As the time window length is 60 and the epoch starts on 1970-01-01 00:00:00 I considered that, a time window is exactly one minute based on beginning of the epoch and the very second on the time window is inclusive. Morover every single row on the report shows the first second in the time window making the report easier to be read and standardized.

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
`java -jar target/challenge-01.00.00.jar <TimeSerieFilePath>`

Where `<TimeSerieFilePath> - Absolute path for time series file`

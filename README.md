## Ftsc - Fyber time series challenge

### Assumptions
**Data** - Expected data already sorted by epoch to consolidate the time window sequentially. This approach reads line-by-line the data file avoiding full scan to compute and out of memory in case of data file larger than memory of computing machine. 

**Time window** - Every single line of the report shows the very first epoch of the time window. I've assumed that to standardized the report.

**Project** - Maven project, Scala version 2.11.8 and JUnit for testing.

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

`Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.523 sec`

`Results :`

`Tests run: 9, Failures: 0, Errors: 0, Skipped: 0`

**and building must succeed as well:**

`[INFO] ------------------------------------------------------------------------`

`[INFO] BUILD SUCCESS`

`[INFO] ------------------------------------------------------------------------`

### Running
`java -jar target/challenge-01.01.00.jar <TimeSerieFilePath>`

Where `<TimeSerieFilePath> - Absolute path for time series file`

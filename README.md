# Adobe-AEM
<h2>Engineering Test</h2>

This is a web service that takes in a number and outputs a Roman numeral.
<br>
Reference : https://en.wikipedia.org/wiki/Roman_numerals

<h3>HTTP Endpoint</h3>
http://localhost:8080/romannumeral?query={integer}
<br><br>
<b>Constraints</b><br>
The input should be an integer in the range 1 - 3999. Anything else will display an error message.

<h3>Prerequisites</h3>
<hr>

* Eclipse IDE
* Java 8
* JUnit5
* Gradle

<h3>Eclipse Instructions</h3>
<hr>

Import the project as a gradle project using the Githib repo link.<br> Right click on the project and click Java Build Path <br> Select the Libraries tab and click on Add Library button present on the left <br> Select JUnit and on the next box select JUnit5 and click finish.

The project can be run by follwoing two ways
* Right click on the AEMApplication.java present in com.adobe.aem package and selct Run As -> Java application. The console tab should display all the logs.
* Run this command in the terminal./gradlew bootRun

To run the JUnit test cases, right click on the RomanNumeralControllerTest.java and select Run As -> JUnit test. The result would be displayed in the JUnit tab.

<h3>Packaging Layout</h3>
<hr>
<pre>
├── HELP.md
├── README.md
├── bin
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── resources
│   ├── application.properties
│   ├── logback-spring.xml
│   ├── static
│   └── templates
├── settings.gradle
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── adobe
│   │   │           └── aem
│   │   │               ├── AemApplication.java
│   │   │               ├── controller
│   │   │               │   └── RomanNumeralController.java
│   │   │               ├── exceptions
│   │   │               │   └── OutOfRangeException.java
│   │   │               └── service
│   │   │                   └── RomanNumeralService.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── logback-spring.xml
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── adobe
│                   └── aem
│                       ├── AemApplicationTests.java
│                       └── RomanNumeralControllerTest.java
</pre>

<h3>Monitoring</h3>
<hr>
The actuator/prometheus enpoint is enabled for monitoring the metrics. Prometheus and Grafna can be hooked up to this application for monitoring purpose.

<h4>Promethes set up </h4>
<hr>
1. docker pull prom/prometheus<br>
2. Create a prometheus.yml with the following configurations - The HOST_IP should be replaced with the system's IP Address
<pre>
global:
    scrape_interval:     15s

scrape_configs:
- job_name: 'prometheus'
  scrape_interval: 5s

  static_configs:
    - targets: ['localhost:9090']

- job_name: 'spring-actuator'
  metrics_path: '/actuator/prometheus'
  scrape_interval: 5s
  static_configs:
    - targets: ['HOST_IP:8080']
 </pre>
 3. Run the Prometheus docker container in the background<br>
 $ docker run -d --name prometheus -p 9090:9090 -v <PATH_TO_prometheus.yml_FILE>:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml<br>
 4. Visit http://localhost:9090 explore the Prometheus dashboard.

<h4>Grafana set up </h4>
<hr>
 1. docker run -d --name grafana -p 3000:3000 grafana/grafana<br>
 2. You can now navigate to http://localhost:3000 and log in to Grafana with the default username admin and password admin<br>
 3. Click Dashboards - > manage - > Import<br>
 4. Use the RomanNumeral-1630564521149.json to import the dashboard.<br>
 5. The panels give the information on the request frequency, ltenct, exception frequency.<br>

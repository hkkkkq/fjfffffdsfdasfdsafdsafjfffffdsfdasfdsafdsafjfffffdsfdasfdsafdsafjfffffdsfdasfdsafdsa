package com.example.demo.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LampLogger {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static final Logger TX_LOGGER = LoggerFactory.getLogger("TX_LOGGER");

	public void txLog(LampLogVo logVo) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in String
		String jsonInString = mapper.writeValueAsString(logVo);
//		System.out.println(jsonInString);
		TX_LOGGER.info( jsonInString );
		/* sample
		 *{"timestamp":"2019-03-14 17:24:37.027","service":"PG076101","operation":"listEmployee","bizTransactionId":null,"transactionId":"fa74ac87-90eb-45cd-980b-531cad79f90b","logType":"IN_REQ","payload":null,"caller":null,"host":{"name":"DESKTOP-MSRERBM","ip":"0:0:0:0:0:0:0:1"},"response":null,"user":{"id":"testuser","ip":"0:0:0:0:0:0:0:1","type":null},"device":null,"security":null,"destination":null}
		 */
	}

}

/*
logback.xml
---
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

	<property name="DEV_HOME" value="c:/logs" />

	<appender name="FILE-AUDIT"
		class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${DEV_HOME}/debug.log</file>
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<Pattern>
				%d{yyyy-MM-dd HH:mm:ss} - %msg%n
			</Pattern>
		</encoder>

		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!-- rollover daily -->
			<fileNamePattern>${DEV_HOME}/archived/debug.%d{yyyy-MM-dd}.%i.log
                        </fileNamePattern>
			<timeBasedFileNamingAndTriggeringPolicy
				class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
				<maxFileSize>10MB</maxFileSize>
			</timeBasedFileNamingAndTriggeringPolicy>
		</rollingPolicy>

	</appender>

	<logger name="TX_LOGGER" level="debug"
		additivity="false">
		<appender-ref ref="FILE-AUDIT" />
	</logger>

	<root level="error">
		<appender-ref ref="FILE-AUDIT" />
	</root>

</configuration>



*/

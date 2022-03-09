### 1. 해당 프로젝트의 목적

- Filter 와 Logback 을 이용하여 모든 Http 요청에 대한 로그를 남기고, 이를 파일에 저장한다.
- 모든 로그에는 다음과 같은 정보가 들어간다.
    - 요청을 한 User 또는 비로그인자는 No User로 구분
    - 어떤 URL로 호출하였는지 확인
    - 요청한 시간


### 2. 플로우
- Rest API 기반으로 정말 Simple하게 짰다.
- 로그인이 되지 않은 유저는 No User 로 MDC 매핑 해주었다.
- 로그인을 하면 세션에 로그인한 Member 이름을 저장한다.
- 로그인한 유저가 다시 호출하면 Filter에서 세션에 있는 이름을 꺼내와 MDC에 매핑한다.
- 그 이후 로그에는 멤버 이름이 남게 된다.

### 3. Logback Configuration XMl File 
````xml
<configuration>

    <!-- Log File 로 보관 -->
    <property name="LOG_FILE" value="LogFile" />
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.gz</fileNamePattern>

            <!-- keep 30 days' worth of history capped at 3GB total size -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>3GB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %X{first} %X{last} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 출력 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- 아래 first와 last는 mdc로 매핑해준 것 ( User에 따라 어떤 로그가 남았는지 확인 가능 ) -->
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %X{first} %X{last} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Console -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <layout>
            <Pattern>%X{first} %X{last} - %m%n</Pattern>
        </layout>
    </appender>

    <root level="info">
<!--        <appender-ref ref="CONSOLE" />-->
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
````

### 4. 실제 로그 확인 
~~~
17:17:42.553 [http-nio-8080-exec-2] INFO  o.a.c.c.C.[Tomcat].[localhost].[/] -  -  - Initializing Spring DispatcherServlet 'dispatcherServlet'
17:17:42.554 [http-nio-8080-exec-2] INFO  o.s.web.servlet.DispatcherServlet -  -  - Initializing Servlet 'dispatcherServlet'
17:17:42.555 [http-nio-8080-exec-2] INFO  o.s.web.servlet.DispatcherServlet -  -  - Completed initialization in 1 ms
17:17:42.561 [http-nio-8080-exec-2] INFO  project.logback.filter.LogFilter -  - /member/add - Filter Starting ...
17:17:42.561 [http-nio-8080-exec-2] INFO  project.logback.filter.LogFilter -  - /member/add - not login User
17:17:42.633 [http-nio-8080-exec-2] INFO  p.l.controller.MemberController - No User - /member/add - sign in user...
17:17:42.656 [http-nio-8080-exec-2] INFO  project.logback.filter.LogFilter - No User - /member/add - Filter End...
17:17:56.358 [http-nio-8080-exec-3] INFO  project.logback.filter.LogFilter -  - /login/1 - Filter Starting ...
17:17:56.359 [http-nio-8080-exec-3] INFO  project.logback.filter.LogFilter -  - /login/1 - not login User
17:17:56.363 [http-nio-8080-exec-3] INFO  project.logback.filter.LogFilter - No User - /login/1 - Filter End...
17:18:05.425 [http-nio-8080-exec-4] INFO  project.logback.filter.LogFilter -  - /member/login/1 - Filter Starting ...
17:18:05.425 [http-nio-8080-exec-4] INFO  project.logback.filter.LogFilter -  - /member/login/1 - not login User
17:18:05.428 [http-nio-8080-exec-4] INFO  p.l.controller.MemberController - No User - /member/login/1 - login ....
17:18:05.447 [http-nio-8080-exec-4] INFO  project.logback.filter.LogFilter - No User - /member/login/1 - Filter End...
17:18:10.414 [http-nio-8080-exec-5] INFO  project.logback.filter.LogFilter -  - /member/1 - Filter Starting ...
17:18:10.414 [http-nio-8080-exec-5] INFO  project.logback.filter.LogFilter -  - /member/1 - login User
17:18:10.416 [http-nio-8080-exec-5] WARN  o.s.w.s.m.s.DefaultHandlerExceptionResolver - yeonkyu - /member/1 - Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' not supported]
17:18:10.416 [http-nio-8080-exec-5] INFO  project.logback.filter.LogFilter - yeonkyu - /member/1 - Filter End...
17:18:14.212 [http-nio-8080-exec-6] INFO  project.logback.filter.LogFilter -  - /member/all - Filter Starting ...
17:18:14.212 [http-nio-8080-exec-6] INFO  project.logback.filter.LogFilter -  - /member/all - login User
17:18:14.212 [http-nio-8080-exec-6] WARN  o.s.w.s.m.s.DefaultHandlerExceptionResolver - yeonkyu - /member/all - Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' not supported]
17:18:14.212 [http-nio-8080-exec-6] INFO  project.logback.filter.LogFilter - yeonkyu - /member/all - Filter End...
~~~
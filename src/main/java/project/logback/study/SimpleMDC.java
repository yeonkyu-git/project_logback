package project.logback.study;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Slf4j
public class SimpleMDC {

    public static void main(String[] args) {
        log.info("Hello world");
        MDC.put("first", "Dorothy");
        MDC.put("last", "Parker");

        log.info("Hello world 22222");


        MDC.put("first", "Richard");
        MDC.put("last", "Nixon");
        log.info("I am not a crook.");
        log.info("Attributed to the former US president. 17 Nov 1973.");

    }
}

package io.arrogantprogrammer.coffeeshop.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusUtil {

    static final Logger LOGGER = LoggerFactory.getLogger(EventBusUtil.class);

    public static final String WEB_UPDATES = "web-updates";

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModules(new JavaTimeModule(),new Jdk8Module());

    public static String toJson(final Object object) {
        LOGGER.debug("marshalling {} to JSON", object.toString());

        try {
            String json = objectMapper.writeValueAsString(object);
            LOGGER.debug("marshalled {} to JSON: {}", object.toString(), json);
            return json;
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            return "{ \"error\" : \"" + e.getMessage() + "\" }";
        }
    }
}

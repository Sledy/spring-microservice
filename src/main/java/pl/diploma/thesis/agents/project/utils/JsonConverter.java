package pl.diploma.thesis.agents.project.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JsonConverter<T> {

    private final ObjectMapper objectMapper;
    private final ExceptionFormatter exceptionFormatter;

    public String convertToJsonString(T input) {
        try {
            return objectMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert object to JSON: " + input.toString());
            log.error(exceptionFormatter.formatStackTrace(e));
            return "";
        }
    }

    public Optional<T> convertToObject(String input, Class<T> targetClass) {
        try {
            return Optional.of(objectMapper.readValue(input, targetClass));
        } catch (JsonProcessingException e) {
            log.error("Cannot deserialize JSON: " + input);
            log.error(exceptionFormatter.formatStackTrace(e));
            return Optional.empty();
        }
    }


}

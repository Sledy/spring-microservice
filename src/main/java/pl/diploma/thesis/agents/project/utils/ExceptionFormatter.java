package pl.diploma.thesis.agents.project.utils;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class ExceptionFormatter {

    String formatStackTrace(Throwable throwable) {
        return Stream.of(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
    }

}

package com.darkarth.demo.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.darkarth.demo.exception.UncheckedException;
import com.darkarth.demo.model.dto.SimpleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LineUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LineUtil.class);

    private final String ID_KEY = "id";
    private final String NAME_KEY = "name";
    private final String DATE_KEY = "date";
    private final String TYPE_KEY = "type";
    private final String EQUALS_SEPARATOR = "=";
    private final String BASE_MSG = "The line format is incorrect.";

    public SimpleDTO transform(String line) {
        try {
            return createObject(getMap(line.split("|")));
        } catch(Exception e) {
            return null;
        }
    }

    private void validateLength(int size) {
        if (size != 4) {
            throw new UncheckedException("EX01", BASE_MSG, "The line fields should be exactly 4.", LOGGER);
        }
    }

    private SimpleDTO createObject(Map<String, String> map) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, SimpleDTO.class);
    }

    private Map<String, String> getMap(String[] content) {

        validateLength(content.length);

        Map<String, String> map = Arrays.stream(content)
        .map(
            s -> s.split(EQUALS_SEPARATOR)
        ).collect(
            Collectors.toMap(
                a -> validateKey(a[0]), 
                a -> a[1]
            )
        );

        validateLength(map.keySet().size());

        return map;
    }

    private String validateKey(String key) {
        if (
            !key.equals(ID_KEY) && 
            !key.equals(NAME_KEY) && 
            !key.equals(DATE_KEY) && 
            !key.equals(EQUALS_SEPARATOR) &&
            !key.equals(TYPE_KEY)
        ) {
            throw new UncheckedException("EX02", BASE_MSG, String.format("The key %s is not a valid key.", key), LOGGER);
        }

        return key;
    }

}